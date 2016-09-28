package com.ssa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebsiteController {

	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest request, ModelAndView mv)
	{
		if(request.getParameter("name") != null)
		{
			mv.addObject("name", request.getParameter("name"));
		}
		mv.setViewName("home");
		return mv;
	}
	
	@RequestMapping("/about")
	public ModelAndView about(HttpServletRequest request, ModelAndView mv)
	{
		//if(request.getParameter("name") != null)
		//{
		//	mv.addObject("name", request.getParameter("name"));
		//}
		mv.setViewName("about");
		return mv;
	}
	
	@RequestMapping("/help")
	public ModelAndView help(HttpServletRequest request, ModelAndView mv)
	{
		String[] msg = {"Message Zero", "Message One", "Message Two", "Message Three", "Message Four"};
		
		if(request.getParameter("help") != null)
		{
			System.out.println(request.getParameter("help"));
			mv.addObject("help", msg[(Integer.parseInt(request.getParameter("help")))]);
		}
		mv.setViewName("help");
		return mv;
	}
}
