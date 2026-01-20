package com.ls.grocery_app.controller;


import com.ls.grocery_app.entity.User; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.ls.grocery_app.repository.UserRepository;

@Controller
public class LoginController {
	
	 @Autowired
	 private UserRepository userRepo;
	 
	 @GetMapping("/login")
	 public String showLoginForm() {
	     return "login";
	 }
	 
	 @PostMapping("/login")
	 public String login(@RequestParam String username, @RequestParam String password, Model model) {
		 User user = userRepo.findByUsername(username);
		 
		 if (user != null && user.getPassword().equals(password)) {
	            return "redirect:/";
	     } else {
	            model.addAttribute("error", "Invalid Username or Password");
	            return "login";
	     }
	 }
}
