package com.service.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidParamException.class)
    protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("Invalid currency code entered"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidUrlGifException.class)
    protected ResponseEntity<AwesomeException> handleThereInvalidUrlException() {
        return new ResponseEntity<>(new AwesomeException("Query url not found"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidApiKeyChangeRatesException.class)
    protected ResponseEntity<AwesomeException> handleInvalidApiKeyOpenexChangeRatesException() {
        return new ResponseEntity<>(new AwesomeException("Please enter your key in the settings properties for openexchangerates"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidApiKeyGiphyException.class)
    protected ResponseEntity<AwesomeException> handleInvalidApiKeyGiphyException() {
        return new ResponseEntity<>(new AwesomeException("Please enter your your key in the settings properties for giphy"), HttpStatus.NOT_FOUND);
    }

    private static class AwesomeException {
        private String message;

        public AwesomeException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
