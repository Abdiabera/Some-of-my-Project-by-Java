package com.Abdi.demo;

import java.sql.Connection;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
public interface AppUsersRepo extends CrudRepository <AppUsers, Integer> {
	
	
}


	


