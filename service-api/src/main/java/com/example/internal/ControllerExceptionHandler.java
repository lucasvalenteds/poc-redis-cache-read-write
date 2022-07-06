package com.example.internal;

import com.example.person.PersonNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ProblemDetail> personNotFoundException(PersonNotFoundException exception) {
        final var responseBody = ProblemDetail.forStatus(404)
                .withTitle("Person not found by ID")
                .withDetail("No person with ID " + exception.getPersonId() + " exists");

        return ResponseEntity.status(responseBody.getStatus())
                .body(responseBody);
    }
}
