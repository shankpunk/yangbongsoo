package com.it.gallery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GalleryImageTestController2 {

	@RequestMapping("/gallery/imagetest2.it")
	public ModelAndView intro(){
		
		ModelAndView mav = new ModelAndView();
		
		//-----------------------------------// fix
		mav.setViewName("imageTest2");
		
		//-----------------------------------//
		  
		return mav;
	} 
	
}
