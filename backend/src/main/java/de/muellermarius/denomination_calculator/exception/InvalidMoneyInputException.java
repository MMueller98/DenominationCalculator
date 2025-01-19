package de.muellermarius.denomination_calculator.exception;

import lombok.Getter;

@Getter
public class InvalidMoneyInputException extends RuntimeException{

    private final double value;

    public InvalidMoneyInputException(final String message, final double value) {
        super(message);
        this.value = value;
    }
}
