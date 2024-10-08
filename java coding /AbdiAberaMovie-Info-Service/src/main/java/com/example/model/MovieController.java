package com.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController 
@RequestMapping ("/movies")
public class MovieController {
	@Autowired 
	private RestTemplate restTemplate;
	
	@RequestMapping( path = "/{movieId}")
public Movie getMovieInfo(@PathVariable ("movieId")String movieId) {
		
		return new Movie(movieId,"test name", "this is action movie");
	}
}
