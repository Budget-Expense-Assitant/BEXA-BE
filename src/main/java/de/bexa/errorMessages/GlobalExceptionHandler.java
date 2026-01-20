package de.bexa.errorMessages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        LOGGER.warn(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseStatusException handleAllOtherExceptions(Exception ex) {
        LOGGER.error("Unexpected exception: {}", ex.getMessage(), ex);
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, GeneralErrorMessages.GENERAL_ERROR);
    }
}
