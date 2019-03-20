/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 2138459
 */
@Service
public class FilteredByAvalibility implements MovieFilter{

    @Override
    public List<Movie> filter(Cinema cinema, String date, String filter) {
        int emptySeats=Integer.parseInt(filter);
        List<Movie> films= new ArrayList<>();
        List<CinemaFunction> cf= cinema.getFunctions();
        for(CinemaFunction c:cf){
            if(c.getDate().equals(date) && emptySeats<=c.getSeats().size()) films.add(c.getMovie());
        }
       return films;
    }


    
}
