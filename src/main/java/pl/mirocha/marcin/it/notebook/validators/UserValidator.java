package pl.mirocha.marcin.it.notebook.validators;

import pl.mirocha.marcin.it.notebook.exceptions.UserValidationException;
import pl.mirocha.marcin.it.notebook.model.dto.RegisterUserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static void validateLogin(String login) {
        if (!login.matches("^[a-z0-9]{5,}$")) {
            throw new UserValidationException("Login too short");
        }
    }

    public static void validateName(String name) {
        String regex = "^[A-Z]{1}[a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        //matcher.end();   mozliwosci po kropce
        if (!matcher.matches()) {
            throw new UserValidationException("Name incorrect!");
        }
    }

    public static void validateSurName(String surname) {
        String regex = "^[A-Z]{1}[a-z]+$";
        if (!surname.matches(regex)) {
            throw new UserValidationException("Surname incorrect!");
        }
    }

    public static void validatePassword(String password) {
        String regex = "^([\\w]{4,}\\d[\\w]*)?([\\w]{3,}\\d[\\w]+)?([\\w]{2,}\\d[\\w]{2,})?([\\w]+\\d[\\w]{3,})?([\\w]*\\d[\\w]{4,})?$";
        if (!password.matches(regex)) {
            throw new UserValidationException("Password too short");
        }
    }

    public static void validatePasswordEquality(String password, String password2) {
        if (!password.equals(password2)) {
            throw new UserValidationException("Password is not equals");
        }
    }

    public static void validateUserDTO(RegisterUserDTO userDTO) {
        UserValidator.validateName(userDTO.getName());
        UserValidator.validateSurName(userDTO.getSurname());
        UserValidator.validateLogin(userDTO.getLogin());
        UserValidator.validatePassword(userDTO.getPassword());
        UserValidator.validatePasswordEquality(userDTO.getPassword(), userDTO.getPassword2());
    }
}
