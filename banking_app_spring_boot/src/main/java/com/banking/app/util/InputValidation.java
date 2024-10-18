package com.banking.app.util;

import com.banking.app.exception.BadInputException;
import org.springframework.http.HttpStatus;

public class InputValidation {

    public static int parseInteger(String userInput) {
        try {
             return Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new BadInputException(HttpStatus.BAD_REQUEST, "Bad Input, try again");
        }
    }

    public static void checkPositiveAmount(double amount) {
        if (amount < 0)
            throw new BadInputException(HttpStatus.BAD_REQUEST, "Cannot be negative, invalid");
    }

}
