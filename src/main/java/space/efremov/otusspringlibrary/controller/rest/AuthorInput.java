package space.efremov.otusspringlibrary.controller.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AuthorInput {

    @NotBlank
    private final String name;

    @JsonCreator
    public AuthorInput(@NotBlank @JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
