package com.Abdi.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Abdi.demo.AppUsersRepo;
import com.Abdi.demo.AppUsers;
@Controller
public class MainController {

	@Autowired
	AppUsersRepo Repo;
	@RequestMapping("/")
	public ModelAndView doHome(){
		
	 ModelAndView mv = new  ModelAndView("index.jsp");
		 mv.addObject("lists", Repo.findAll());
		 return mv;
		
	}


	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView doSave(@RequestParam("id") String idString, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName)
	{
		ModelAndView mv = new ModelAndView("redirect:/save");
		AppUsers u;
		if (!idString.isEmpty())
			u = (AppUsers)Repo.findById(Integer.parseInt(idString)).get();
		else
			u = new AppUsers();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		Repo.save(u);
		return mv;
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView doView(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/view.jsp");
		
		mv.addObject("lists", Repo.findById(id).get());

		return mv;
		
	//return "redirect:/";
}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView dodelete(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("redirect:/");
		
		Repo.deleteById(id);
		
		return mv;
		
}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView doedit(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/edit.jsp");
		
		mv.addObject("lists", Repo.findById(id).get());
		
		return mv;
		
		
}

	}