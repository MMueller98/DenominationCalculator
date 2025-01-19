package de.muellermarius.denomination_calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidMoneyInputException.class)
    public ResponseEntity<String> handleInvalidMoneyInputException(final InvalidMoneyInputException e)  {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid Money Input: " + e.getValue() + "\nInput a value between 0 and " + Double.MAX_VALUE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(final Exception e)  {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected Internal Server Error: " + e.getMessage());
    }


}
