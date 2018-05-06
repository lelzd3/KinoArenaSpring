package com.kinoarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

	//admin main
	@RequestMapping(value="/adminMain", method = RequestMethod.GET)
	public String returnToAdminMan() {
		return "adminMain";
	}
	
	//user main
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String returnToUserMain() {
		return "main";
	}


	//test main
	@RequestMapping(value="/getTestMain", method = RequestMethod.GET)
	public String returnToTestMain() {
		return "testMain";
	}
}
