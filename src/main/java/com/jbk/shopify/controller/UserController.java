package com.jbk.shopify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jbk.shopify.entity.User;
import com.jbk.shopify.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping("/add-user")
	public String addUser(@ModelAttribute User user,Model model) {
		boolean isAdded = service.addUser(user);
		if(isAdded) {
			model.addAttribute("msg", "User Added");
			return "adduser";
		}
		else {
			model.addAttribute("msg", "Username or may be email,phone Already Exists");
			return "adduser";
		}
		
		
		
	}
	
	@GetMapping("alluser")
	public String getAllUser(Model model) {
		
		List<User> allUser = service.getAllUser();
		model.addAttribute("users", allUser);
		return "userlist";
		
	}
	
	
}
