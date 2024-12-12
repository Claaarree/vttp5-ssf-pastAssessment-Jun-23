package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.services;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class AuthenticationService {

	private final String authenticationUrl = "https://ssf-auth-server-production.up.railway.app/api/authenticate";

	RestTemplate template = new RestTemplate();

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {
		JsonObject payload = Json.createObjectBuilder()
				.add("username", username)
				.add("password", password)
				.build();

		RequestEntity<String> req = RequestEntity.post(authenticationUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Accept", "application/json")
				.body(payload.toString());

		ResponseEntity<String> res = template.exchange(req, String.class);
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
