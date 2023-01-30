package ru.job4j.accidents.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService users;

    @Autowired
    public UserValidator(UserService users) {
        this.users = users;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (users.getUser(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "This name is already taken");
        }
    }
}
