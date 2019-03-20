/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cristian
 */
@Service
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> unsafeCinemas=new HashMap<>();
    private Map<String, Cinema> cinemas = Collections.synchronizedMap(unsafeCinemas);

    public InMemoryCinemaPersistence() {
        //load stub data
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
        Cinema c1=new Cinema("CineColombia",functions);
        Cinema c2= new Cinema("cineMark",functions);
        cinemas.put("cinemaX", c);
        cinemas.put("CineColombia", c1);
        cinemas.put("Cinemark", c2);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        Cinema c = cinemas.get(cinema);
        List<CinemaFunction> functions = c.getFunctions();
        CinemaFunction cf = null;
        for(int i = 0; i < functions.size(); i++){
            CinemaFunction fun = functions.get(i);

            if(fun.getMovie().equals(movieName) && fun.getDate().equals(date)){
                cf = fun;
                cf.buyTicket(row, col);
                break;
            }
        }

        
        //throw new UnsupportedOperationException("Not supported yet.");
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
