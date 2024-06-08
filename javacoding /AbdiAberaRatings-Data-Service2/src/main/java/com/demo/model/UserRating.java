package com.demo.model;

import java.util.List;

public class UserRating {
	
	private List<Rating> UserRatings;
	
	public List<Rating> getRatings(){
		
		return UserRatings;
	}
	
public void setRatings(List <Rating> ratings) {
	
	this.UserRatings = ratings;
}

}
