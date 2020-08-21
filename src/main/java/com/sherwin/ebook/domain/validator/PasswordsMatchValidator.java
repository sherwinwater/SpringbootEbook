package com.sherwin.ebook.domain.validator;

import com.sherwin.ebook.domain.User;
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
