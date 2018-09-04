package space.efremov.otusspringlibrary.controller.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.*;
import java.util.List;

public class BookInput {

    @Size(min = 1, max = 1024, message = "Book's title must'n be empty")
    private String title;

    @Pattern(regexp = "^(?=[-0-9xX ]{13}$)(?:[0-9]+[- ]){3}[0-9]*[xX0-9]$", message = "ISBN must be in correct ISBN 10 or ISBN 13 format")
    private String isbn;

    @Max(value = 3000, message = "Year must'n be more then 3000")
    @Min(value = 1, message = "Year must'n be less then 1")
    private Integer year;

    @NotNull(message = "Publisher ID must'n be null")
    @Min(value = 1, message = "Publisher ID must be more then zero")
    private Long publisherId;

    private List<Long> authorIds;

    private List<Long> tagIds;

    @JsonCreator
    public BookInput(@Size(min = 1, max = 1024, message = "Book's title must'n be empty") String title, @Pattern(regexp = "^(?=[-0-9xX ]{13}$)(?:[0-9]+[- ]){3}[0-9]*[xX0-9]$", message = "ISBN must be in correct ISBN 10 or ISBN 13 format") String isbn, @Max(value = 3000, message = "Year must'n be more then 3000") @Min(value = 1, message = "Year must'n be less then 1") Integer year, @NotNull(message = "Publisher ID must'n be null") @Min(value = 1, message = "Publisher ID must be more then zero") Long publisherId, List<Long> authorIds, List<Long> tagIds) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publisherId = publisherId;
        this.authorIds = authorIds;
        this.tagIds = tagIds;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getYear() {
        return year;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }
}
