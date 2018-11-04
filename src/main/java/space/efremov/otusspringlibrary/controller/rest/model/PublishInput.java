package space.efremov.otusspringlibrary.controller.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class PublishInput {

    @NotBlank(message = "Publisher's name must'n be empty")
    private final String name;

    @JsonCreator
    public PublishInput(@NotBlank(message = "Publisher's name must'n be empty") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
