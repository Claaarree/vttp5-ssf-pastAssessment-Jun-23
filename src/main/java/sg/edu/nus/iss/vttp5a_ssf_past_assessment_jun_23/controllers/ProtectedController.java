package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/protected")
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
	@GetMapping
	public ModelAndView showContent(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if(session.getAttribute("authenticated") == null || 
		!(Boolean)session.getAttribute("authenticated")
		) {
			mav.setViewName("redirect:/");
		} else mav.setViewName("view1");
		return mav;
	}
}
