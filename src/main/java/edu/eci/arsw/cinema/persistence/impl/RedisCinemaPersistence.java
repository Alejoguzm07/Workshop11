package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.filter.MovieFilter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.arsw.cinema.util.RedisMethods;
import edu.eci.arsw.cinema.util.*;
import redis.clients.jedis.Jedis;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */

@Service
public class RedisCinemaPersistence implements CinemaPersitence {

    @Autowired
    private MovieFilter filter = null;

    private final ConcurrentHashMap<String, Cinema> cinemas = new ConcurrentHashMap<>();
    private final RedisMethods redis = new RedisMethods();

    public RedisCinemaPersistence() {
        String functionDate = "2018-12-18 15:30";
        String functionDate1 = "2018";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("Split","suspense"),functionDate1);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Glass","Suspense"),functionDate1);
        functions.add(funct3);
        functions.add(funct4);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("cinemaX",functions);
        Cinema c1=new Cinema("cinemaY",functions);
        Cinema c2= new Cinema("cineMark",functions);
        cinemas.put("cinemaX", c);
        cinemas.put("CineColombia", c1);
        cinemas.put("Cinemark", c2); 
        try {
            funct1.setSeats(convertToMatrix(redis.getSeatsRedis(c,funct1)));
            funct2.setSeats(convertToMatrix(redis.getSeatsRedis(c,funct2)));
            funct3.setSeats(convertToMatrix(redis.getSeatsRedis(c1,funct3)));
            funct4.setSeats(convertToMatrix(redis.getSeatsRedis(c1,funct4)));
         } catch (Exception ex) {
            Logger.getLogger(RedisCinemaPersistence.class.getName()).log(Level.SEVERE, null, ex);
         }               
    }

    private List<List<Boolean>> convertToMatrix(boolean[][] temp){
        List<List<Boolean>>list = new ArrayList<List<Boolean>>();
        for (int i = 0; i < temp.length; i++) {
            List<Boolean>temp3 = new ArrayList<Boolean>();
            for (int j = 0; j < temp[i].length; j++) {
                temp3.add(temp[i][j]);
            }
            list.add(temp3);
        }
    return list;
}

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {

    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        Cinema c = cinemas.get(cinema);
        List<CinemaFunction> functions = c.getFunctions();
        List<CinemaFunction> rta = new ArrayList<>();
        for(int i = 0; i < functions.size(); i++){
            CinemaFunction fun = functions.get(i);
            if(fun.getDate().equals(date)){
                rta.add(fun);

            }
        }
        return rta;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        return cinemas.get(name);
    }

    @Override
    public Set<Cinema> getAllCinemas() {
        Set<Cinema> res = new HashSet<>();
        for(String c:cinemas.keySet())
            res.add(cinemas.get(c));
        return res;
    }

    @Override
    public Cinema getCinemaByName(String name) throws CinemaPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCinema(String name, CinemaFunction cinemaFunction) throws CinemaPersistenceException {
        Cinema c = cinemas.get(name);
        boolean flag=false;
        List<List<Boolean>> seats= new ArrayList<>();
        List<CinemaFunction> cfs=c.getFunctions();
        for(CinemaFunction cf:cfs){

            if(cf.getMovie().getName().equals(cinemaFunction.getMovie().getName())){
                cf.setDate(cinemaFunction.getDate());
                cf.setMovie(cinemaFunction.getMovie());
                cf.setSeats(cinemaFunction.getSeats());
                flag=true;

            }
        }

        if(!flag)  c.addCinemafunction(cinemaFunction);
    }

    @Override
    public CinemaFunction getcinemaFunctionByMovieName(List<CinemaFunction> cfs, String movieName) throws CinemaPersistenceException {
        CinemaFunction rst=null;
        for(CinemaFunction cf:cfs){
            if(cf.getMovie().getName().equals(movieName))rst=cf;
        }
        return rst;
    }

}