package com.sherwin.springit.domain.validator;

import com.sherwin.springit.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements
        ConstraintValidator<PasswordsMatch, User> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    public boolean isValid(User user, ConstraintValidatorContext context){
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
