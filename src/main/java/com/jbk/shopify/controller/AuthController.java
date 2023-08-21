package com.jbk.shopify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jbk.shopify.entity.User;
import com.jbk.shopify.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService service;

	@PostMapping("/login")
	public String loginProcess(@ModelAttribute User user, Model model) {

		Object object = service.loginProcess(user);

		if (object instanceof User) {
			return "home";
		} else {
			model.addAttribute("msg", object);
			return "login";
		}

	}

}
