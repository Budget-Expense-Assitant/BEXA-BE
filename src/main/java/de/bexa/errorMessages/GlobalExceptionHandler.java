package de.bexa.errorMessages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(ResponseStatusException ex) {
        log.warn(ex.getMessage());
        throw ex;
    }

    @ExceptionHandler(Exception.class)
    public ResponseStatusException handleAllOtherExceptions(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, GeneralErrorMessages.GENERAL_ERROR);
    }
}
