package space.efremov.otusspringlibrary.controller.rest;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class TagInput {

    @NotBlank(message = "Tag must'n be empty")
    private final String name;

    @JsonCreator
    public TagInput(@NotBlank(message = "Tag must'n be empty") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
