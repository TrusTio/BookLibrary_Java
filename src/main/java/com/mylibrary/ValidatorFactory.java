package com.mylibrary;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;

import java.util.Calendar;

class ValidatorFactory {
    private static boolean isValidIsbn, isValidName, isValidAuthor, isValidYear, isValidPrice, isValidQuantity;

    static Validator<String> createStringValidator(String type) {

        if (type.equals("isbn")) {
            return (Validator<String>) (value, context) -> {
                isValidIsbn=false;
                if (value.length() < 9) return ValidationResult.error("The length should be more than 9 numbers!");
                try {
                    int number = Integer.parseInt(value.substring(0, 7));
                    int number2 = Integer.parseInt(value.substring(7));
                    //if it returns an exception then it's not a valid input(ISBN is made up from numbers only)
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid ISBN containing only numbers!");
                }
                isValidIsbn=true;
                return ValidationResult.ok();
            };
        }

        if(type.equals("name")){
            return (Validator<String>) (value, context) -> {
                isValidName=false;
                if(value.length()<3 || value.length()>40){
                    return ValidationResult.error("Book name must be between 3 and 40 characters");
                }
                isValidName=true;
                return ValidationResult.ok();
            };
        }

        if(type.equals("author")){
            return (Validator<String>) (value, context) -> {
                isValidAuthor=false;
                if(value.length()<3 || value.length()>40){
                    return ValidationResult.error("Author name must be between 3 and 40 characters");
                }
                isValidAuthor=true;
                return ValidationResult.ok();
            };
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (type.equals("year")) {
            return (Validator<String>) (value, context) -> {
                isValidYear=false;
                if (value.length() < 4) return ValidationResult.error("Must be a valid year!");
                try {
                    int number = Integer.parseInt(value);
                    if (number < 1000 || number > year) return ValidationResult.error("Must be a valid year!");
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid year!");
                }
                isValidYear =true;
                return ValidationResult.ok();
            };
        }

        if (type.equals("quantity")) {
            return (Validator<String>) (value, context) -> {
                isValidQuantity=false;
                if(value.length()<1) return ValidationResult.error("Must be a valid quantity!");
                try {
                    int quantity = Integer.parseInt(value); // checking if it's a valid number
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid quantity!");
                }
                isValidQuantity=true;
                return ValidationResult.ok();
            };
        }

        if (type.equals("price")) {
            return (Validator<String>) (value, context) -> {
                isValidPrice=false;
                if(value.length()<1) return ValidationResult.error("Must be a valid price!");
                try {
                    double price = Double.parseDouble(value); // checking if it's a valid double number
                } catch (NumberFormatException e) {
                    return ValidationResult.error("Must be a valid price!");
                }
                isValidPrice=true;
                return ValidationResult.ok();
            };

        }

        return null;
    }

    static boolean validFields(){
        return isValidIsbn && isValidName && isValidAuthor && isValidYear && isValidPrice && isValidQuantity;
    }

}
