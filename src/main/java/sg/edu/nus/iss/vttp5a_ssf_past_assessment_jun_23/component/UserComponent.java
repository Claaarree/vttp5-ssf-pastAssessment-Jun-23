package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model.User;
import sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.services.AuthenticationService;

@Component
public class UserComponent {
    @Autowired
    AuthenticationService authenticationService;

    public Boolean isUserLocked(User u) {
        return authenticationService.isLocked(u.getUsername());
    }

    public Boolean isUserAuthenticated(ResponseEntity<String> response) {
        return response.getStatusCode().equals(HttpStatus.valueOf(201));
    }


}
