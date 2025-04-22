package com.zoo.presentation.exception;

import com.zoo.application.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.zoo.application.exception.ResourceNotFoundException;


import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AnimalNotFoundException.class, EnclosureNotFoundException.class, FeedingScheduleNotFoundException.class})
    ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("urn:zoo:errors:resource-not-found"));
        return problemDetail;
    }

    @ExceptionHandler({EnclosureFullException.class, IncompatibleEnclosureTypeException.class, IllegalStateException.class})
    ProblemDetail handleConflictState(RuntimeException ex) {
         log.warn("Conflict state detected: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Operation Conflict");
         problemDetail.setType(URI.create("urn:zoo:errors:conflict"));
         if (ex instanceof IllegalStateException) {
              problemDetail.setType(URI.create("urn:zoo:errors:illegal-state"));
         } else if (ex instanceof EnclosureFullException) {
              problemDetail.setType(URI.create("urn:zoo:errors:enclosure-full"));
         } else if (ex instanceof IncompatibleEnclosureTypeException) {
             problemDetail.setType(URI.create("urn:zoo:errors:incompatible-enclosure"));
         }
         return problemDetail;
    }

    @ExceptionHandler({InvalidTransferException.class, IllegalArgumentException.class})
    ProblemDetail handleBadRequest(RuntimeException ex) {
         log.warn("Bad request: {}", ex.getMessage());
         ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
         problemDetail.setTitle("Bad Request");
         if (ex instanceof InvalidTransferException) {
              problemDetail.setType(URI.create("urn:zoo:errors:invalid-transfer"));
         } else {
              problemDetail.setType(URI.create("urn:zoo:errors:invalid-argument"));
         }
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred. Please contact support.");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("urn:zoo:errors:internal-server-error"));
        return problemDetail;
    }

}
