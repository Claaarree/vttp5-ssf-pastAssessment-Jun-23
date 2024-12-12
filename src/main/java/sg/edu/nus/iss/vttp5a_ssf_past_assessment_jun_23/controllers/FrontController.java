package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model.Captcha;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model.User;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.services.AuthenticationService;

@Controller
@RequestMapping("/")
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 6
	@Autowired
	AuthenticationService authenticationService;

	@GetMapping
	public ModelAndView displayLogin() {
		ModelAndView mav = new ModelAndView("view0");
		User u = new User();
		mav.addObject("user", u);
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView handleLogin(@Valid @ModelAttribute User u, BindingResult results, 
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (results.hasErrors()) {
			mav.setViewName("view0");
			
		} else {
			Integer loginAttempts = 0;
			if(session.getAttribute("loginAttempts") == null) {
				loginAttempts = 1;
				session.setAttribute("loginAttempts", loginAttempts);
			}

			try {
				authenticationService.authenticate(u.getUsername(), u.getPassword());
				mav.setViewName("redirect:/protected");

			} catch (Exception e) {
				ObjectError badLogin = new ObjectError("badLogin", 
				"Bad login attempt! Please try again!");
				results.addError(badLogin);

				mav.setViewName("view0");
				Captcha captcha = new Captcha();
				mav.addObject("captcha", captcha);
				
				loginAttempts = (Integer)session.getAttribute("loginAttempts");
				System.out.println("login attempts: " + loginAttempts);
				if (captcha.getAnswer() != u.getAnswer() && loginAttempts > 1) {
					ObjectError captchaError = new ObjectError("captchaError", 
					"The captcha answer was wrong! Please try again!");
					results.addError(captchaError);
				}

				if (loginAttempts == 3){
					mav.setViewName("view2");
					session.removeAttribute("loginAttempts");
				} else {
					session.setAttribute("loginAttempts", loginAttempts + 1);
				}

				
			} 
		}

		return mav;
	}

	@GetMapping("/protected")
	public ModelAndView loginSuccess() {
		ModelAndView mav = new ModelAndView("view1");
		return mav;
	}
}
