package com.web.keycloak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping (method = RequestMethod.GET, value= "/realm/{realmName}")
    public ModelAndView site (@PathVariable final String realmName, final Model model) {
		
        final ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        
        return mav;
    }


}