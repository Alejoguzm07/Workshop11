/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2138459
 */

//@Service
public class FilteredByGender implements MovieFilter {

	@Override
	public List<Movie> filter(Cinema cinema, String date, String filter) {
        List<Movie> films= new ArrayList<>();
        List<CinemaFunction> cf= cinema.getFunctions();
        for(CinemaFunction c:cf){
            if(c.getDate().equals(date) && c.getMovie().getGenre().equals(filter)) films.add(c.getMovie());
        }
       return films;
	}


    
}
