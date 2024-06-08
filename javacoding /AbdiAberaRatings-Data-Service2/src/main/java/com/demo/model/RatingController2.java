package com.demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  @RequestMapping ("/Rating")

	public class RatingController2 {
		
		List<Rating> ratings = Arrays.asList(
				
				new Rating ("1234", 7),
				
				new Rating("8769",8));
  
		@RequestMapping("/{movieId}")
				public UserRating getRating (@PathVariable("movieId")String movieId) {
			
			UserRating UserRatings = new UserRating ();
			
			UserRatings.setRatings(ratings);
			
			return UserRatings;
		}
	}


