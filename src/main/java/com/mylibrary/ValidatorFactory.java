package com.mylibrary;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;

import java.util.Calendar;

public class ValidatorFactory {

    public static Validator<String> createStringValidator(String type) {
        if (type.equals("isbn")) {
            return (Validator<String>) (value, context) -> {
                if (value.length() < 9) return ValidationResult.error("The length should be more than 9 numbers!");
                try {
                    int number = Integer.parseInt(value.substring(0, 7));
                    int number2 = Integer.parseInt(value.substring(7));
                    //if it returns an exception then it's not a valid input(ISBN is made up from numbers only)
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid ISBN containing only numbers!");
                }
                return ValidationResult.ok();
            };
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (type.equals("year")) {
            return (Validator<String>) (value, context) -> {
                if (value.length() < 4) return ValidationResult.error("Must be a valid year!");
                try {
                    int number = Integer.parseInt(value);
                    if (number < 1000 || number > year) return ValidationResult.error("Must be a valid year!");
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid year!");
                }

                return ValidationResult.ok();
            };
        }

        if (type.equals("quantity")) {
            return (Validator<String>) (value, context) -> {
                try {
                    int quantity = Integer.parseInt(value); // checking if it's a valid number
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid quantity!");
                }
                return ValidationResult.ok();
            };
        }

        if(type.equals("price")){
            return (Validator<String>) (value, context) ->{
                try {
                    double price = Double.parseDouble(value); // checking if it's a valid double number
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid price!");
                }
                return ValidationResult.ok();
            };

        }

        return null;
    }
}
