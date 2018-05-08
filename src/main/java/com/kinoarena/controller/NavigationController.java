package com.kinoarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

	//test main
	@RequestMapping(value="/getHome", method = RequestMethod.GET)
	public String returnToTestMain() {
		return "home";
	}
}
