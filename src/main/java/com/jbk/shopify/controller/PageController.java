package com.jbk.shopify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/adduserpage")
	public String addUserPage() {
		return "adduser";

	}
	
	@RequestMapping("/home")
	public String home() {
		return "home";

	}
	
	
}