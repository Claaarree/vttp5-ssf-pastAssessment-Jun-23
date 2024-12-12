package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.component.UserComponent;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model.Captcha;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model.User;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.services.AuthenticationService;

@Controller
@RequestMapping("/test")
public class FrontController1 {

	// TODO: Task 2, Task 3, Task 4, Task 6
	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserComponent userComponent;

	@GetMapping
	public ModelAndView displayLogin(HttpSession session) {
		
		ModelAndView mav = new ModelAndView("view00");
		User u = new User();
		mav.addObject("user", u);
		return mav;
	}

	@PostMapping("/test")
	public ModelAndView handleLogin(@Valid @ModelAttribute User u, BindingResult results, 
			HttpSession session) {
		if(session.getAttribute("loginAttempts") == null) {
			session.setAttribute("loginAttempts", 0);
		}
		Integer login = (Integer)session.getAttribute("loginAttempts");
		login += 1;
		System.out.println(login);
		session.setAttribute("loginAttempts", login);
		ModelAndView mav = new ModelAndView();
		Captcha c = new Captcha();

		// checking for validation errors
		if (results.hasErrors()) {			
			mav.addObject("captcha", c);
			session.setAttribute("captcha", c);
			mav.setViewName("view00");
			
		} else {
			mav.addObject("captcha", c);
			session.setAttribute("captcha", c);
			
			if(login >= 3){
				authenticationService.disableUser(u.getUsername());
				session.setAttribute("loginAttempts", 0);
			}
			// check if user is locked out
			if (userComponent.isUserLocked(u)){
				mav.setViewName("view22");
			} else {
				try {
					authenticationService.authenticate(u.getUsername(), u.getPassword());
					u.setIsAuthenticated(true);
					mav.setViewName("redirect:/protected");
					session.setAttribute("authenticated", u.getIsAuthenticated());
				} catch (Exception e) {
					ObjectError error = new ObjectError("error", "error error");
					results.addError(error);
					mav.setViewName("view00");
				}
			}
		}
				
		return mav;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		// System.out.println(session.getAttribute("authenticated") + "in logout before invalidate");
		ModelAndView mav = new ModelAndView("redirect:/");
		session.removeAttribute("authenticated");
		session.invalidate();
		// System.out.println(session.getAttribute("authenticated") + "in logout after invalidate");
		return mav;
	}

}
