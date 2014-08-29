package com.it.gallery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GalleryImageTestController {

	@RequestMapping("/gallery/imagetest.it")
	public ModelAndView intro(){
		
		ModelAndView mav = new ModelAndView();
		
		//-----------------------------------// fix
		mav.setViewName("imageTest");
		
		//-----------------------------------//
		  
		return mav;
	} 
	
}
