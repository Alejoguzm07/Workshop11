/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.services.CinemaServices;
import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cristian
 */

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {
    @Autowired
    CinemaServices cinemaServices;
    @RequestMapping(method = RequestMethod.GET)
public ResponseEntity<Set<Cinema>> manejadorGetRecursoXX() throws CinemaPersistenceException{
        Set<Cinema> data= cinemaServices.getAllCinemas();
        return new ResponseEntity<>(data,HttpStatus.ACCEPTED); 
}
@GetMapping("/{name}")
public ResponseEntity<?> getCinemaByName(@PathVariable("name") String name) throws CinemaPersistenceException{

    try {
        Cinema data=cinemaServices.getCinemaByName(name);
        return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
    } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

} 
@GetMapping("/{name}/{date}")
public ResponseEntity<?> getCinemaByNameAndDadte(@PathVariable("name") String name,@PathVariable("date") String date) throws CinemaPersistenceException{

    try {
        List<CinemaFunction> data=cinemaServices.getCinemaByNameAndDate(name, date);
        return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
    } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

}
@GetMapping("/{name}/{date}/{moviename}")
public ResponseEntity<?> getMovieName(@PathVariable("name") String name, @PathVariable("date") String date, @PathVariable("moviename") String moviename)throws CinemaPersistenceException{

    try {
        List<CinemaFunction> cfs=cinemaServices.getCinemaByNameAndDate(name,date);
        CinemaFunction cf=cinemaServices.getcinemaFunctionByMovieName(cfs,moviename);
        return new ResponseEntity<>(cf,HttpStatus.ACCEPTED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }


}

@PostMapping("/{name}")
public ResponseEntity<?> postRegistrarFuncion(@PathVariable("name") String name, @RequestBody  CinemaFunction cinemafunction){
    try {
        cinemaServices.getCinemaByName(name).addCinemafunction(cinemafunction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

}

@PutMapping("/{name}")
    public ResponseEntity<?> putUpdatefunction(@PathVariable("name")String name,@RequestBody CinemaFunction cinemaFunction){
    try {


        cinemaServices.updateCinema(name,cinemaFunction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (CinemaPersistenceException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }


}
}
