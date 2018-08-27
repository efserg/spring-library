package space.efremov.otusspringlibrary.controller.rest;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Size;

public class ReviewInput {

    @Size(min = 1, max = 2048, message = "Review must'n be empty")
    private String text;

    @JsonCreator
    public ReviewInput(@Size(min = 1, max = 2048, message = "Review must'n be empty") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
