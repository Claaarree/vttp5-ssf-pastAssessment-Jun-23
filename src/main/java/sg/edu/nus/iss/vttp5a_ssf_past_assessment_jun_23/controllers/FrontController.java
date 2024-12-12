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
@RequestMapping("")
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 6
	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserComponent userComponent;

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
		Captcha c = new Captcha();
		mav.addObject("captcha", c);
		session.setAttribute("captcha", c);
		// checking for validation errors
		if (results.hasErrors()) {
			System.out.println("In results has errors");

			mav.setViewName("view0");
			
		} else {
			// check if user is locked out
			if (userComponent.isUserLocked(u)){
				mav.setViewName("view2");
			}

			// System.out.println("In results has errors else");

			// checking if account is locked
			if (authenticationService.isLocked(u.getUsername())){
				System.out.println("account locked");
				mav.setViewName("view2");
			} 
			else {
				System.out.println("account not locked");

				// setting login attempts count
				Integer loginAttempts = 0;
				if(session.getAttribute("loginAttempts") == null) {
					System.out.println("in new login session");

					loginAttempts = 1;
					session.setAttribute("loginAttempts", loginAttempts);
				} 
				loginAttempts = (Integer)session.getAttribute("loginAttempts");
				System.out.println("login attempts: " + loginAttempts);
				
				// checking login attempts
				if (loginAttempts == 3){
					mav.setViewName("view2");
					authenticationService.disableUser(u.getUsername());
					session.removeAttribute("loginAttempts");
				} else {
					session.setAttribute("loginAttempts", loginAttempts + 1);
					mav.setViewName("view0");
				}				
				
				// checking if captcha is correct if there is one
				if (session.getAttribute("captcha") != null){
					System.out.println("in has captcha");

					// Captcha captcha =(Captcha)session.getAttribute("captcha");
					System.out.println("captcha answer: " + c.getAnswer());
					System.out.println("captcha question: " + c.getQuestion());

					if (c.getAnswer() != u.getAnswer() && loginAttempts > 1) {
						System.out.println("in captcha wrong answer");
						// session.setAttribute("captcha", new Captcha());
						ObjectError captchaError = new ObjectError("captchaError", 
						"The captcha answer was wrong! Please try again!");
						results.addError(captchaError);
					} else {
						System.out.println("in captcha right answer");
						// finally authenticating the username and password
						try {
							authenticationService.authenticate(u.getUsername(), u.getPassword());
							u.setIsAuthenticated(true);
							mav.setViewName("redirect:/protected");
							session.setAttribute("authenticated", u.getIsAuthenticated());
		
							// System.out.println(session.getAttribute("authenticated") + "in try");
			
						} catch (Exception e) {
							ObjectError badLogin = new ObjectError("badLogin", 
							"Bad login attempt! Please try again!");
							results.addError(badLogin);
							mav.setViewName("view0");	
						} 
					}
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
