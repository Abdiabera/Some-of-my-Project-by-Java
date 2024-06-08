package com.example.demo;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Entity 
	@Table (name = "class1")
//@RestController
public class Alien {
	
@Id
@GeneratedValue (strategy = GenerationType.AUTO)

@Column(name = "id")
private int id;
@Column(name = "name")

private String name ;


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}


public String getPoints() {
	return points;
}


public void setPoints(String points) {
	this.points = points;
}

@Column(name = "points")
private String points;

@Override
public String toString (){
	return " Alien [ id = " + id + ", name =" + name + ", points="  + points + "]";
}
}
