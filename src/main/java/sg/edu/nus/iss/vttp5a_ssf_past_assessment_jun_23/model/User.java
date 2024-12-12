package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {
    @NotBlank(message = "Please input a username!")
    @Size(min = 2, message = "Your username must be at least 2 characters in length!")
    private String username;

    @NotBlank(message = "Please enter a password!")
    @Size(min = 2, message = "Your password is at least 2 characters long!")
    private String password;

    private Boolean isAuthenticated;
    private Double answer;
    
    public User() {
    }

    public User(String username, String password, Boolean isAuthenticated) {
        this.username = username;
        this.password = password;
        this.isAuthenticated = isAuthenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }
    
}
