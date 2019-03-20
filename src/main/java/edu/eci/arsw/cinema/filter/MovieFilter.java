/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import java.util.List;
import java.util.Date;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.Movie;

/**
 *
 * @author 2138459
 */
public interface MovieFilter {
    
    /**
     *
     * @param cinema
     * @param date
     * @param filter
     * @return
     */
  public List<Movie> filter(Cinema cinema, String date, String filter);
    
}
