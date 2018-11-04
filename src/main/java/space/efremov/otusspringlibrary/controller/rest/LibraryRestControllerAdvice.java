package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class LibraryRestControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(),
                ex.getId().isPresent() ?
                        String.format("The entity '%s' with id = %d doesn't find", ex.getClazz().getSimpleName(), ex.getId().get()) :
                        String.format("One or more entities '%s' don't find", ex.getClazz().getSimpleName()));
    }

}
