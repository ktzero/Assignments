package com.ssa.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, ModelAndView mv) {
        if(request.getParameter("name") != null){
            mv.addObject("name", request.getParameter("name"));
        }
        mv.setViewName("home");
        return mv;
    }
	@RequestMapping("/about")
    public ModelAndView about(HttpServletRequest request, ModelAndView mv) {
//        if(request.getParameter("name") != null){
//            mv.addObject("name", request.getParameter("name"));
//        }
        mv.setViewName("about");
        return mv;
    }
	@RequestMapping("/help")
    public ModelAndView help(HttpServletRequest request, ModelAndView mv) {
		ArrayList<String> messages = new ArrayList<String>();
		for (int i = 0; i < 101; i++) {
			messages.add("This is help message " + i);
		}
		
        if(request.getParameter("id") != null){
            mv.addObject("id", request.getParameter("id"));
            int helpId = Integer.parseInt(request.getParameter("id").substring(0,1));
            mv.addObject("desc", messages.get(helpId));
        }
        if(request.getParameter("desc") != null) {
        	mv.addObject("id", "x");
        	mv.addObject("desc", request.getParameter("desc"));
        }
        mv.setViewName("help");
        return mv;
    }
}
