package space.efremov.otusspringlibrary.controller.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserInput {

    @Email(message = "Use valid email")
    @NotBlank
    private String email;

    @NotBlank(message = "Username must'n be empty")
    @Size(min = 3, max = 255, message = "Username length must be between 3 and 255 characters")
    private String username;

    @JsonCreator
    public UserInput(@Email(message = "Use valid email") @NotBlank String email, @NotBlank(message = "Username must'n be empty") @Size(min = 3, max = 255, message = "Username length must be between 3 and 255 characters") String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
