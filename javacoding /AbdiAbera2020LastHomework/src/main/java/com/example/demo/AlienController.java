package com.example.demo;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienController {
	@Autowired
	AlienRepository repo;
	
	@GetMapping("/")
	
	public List<Alien>getAlien()
	
	{
		List <Alien> aliens = repo.findAll();
		return aliens;
	
	}
@PostMapping (path = "/alien")
public Alien add( @RequestBody Alien alien)
{
	repo.save(alien);
	return alien;
	
}
@GetMapping ("/alien/{aid}")
public Optional <Alien> getAlienOne(@PathVariable("aid") int aid)
{
	return repo.findById (aid);
	
}
@DeleteMapping ("/alien/{aid}")
public String delete(@PathVariable("aid") int aid) {
	Alien a = repo.getOne(aid);
	repo.delete(a);
	
	return "delete";
}
@PutMapping (path =  "/alien", consumes = { "application/json"})
	public Alien SaveorUpdate (@RequestBody Alien alien) {
	repo.save(alien);
	return alien;
}
		
}
