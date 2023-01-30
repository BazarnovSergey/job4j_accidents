package ru.job4j.accidents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import ru.job4j.accidents.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;
import ru.job4j.accidents.util.UserValidator;

import javax.validation.Valid;

@Controller
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;
    private final UserValidator userValidator;

    @Autowired
    public RegControl(PasswordEncoder encoder, UserService users,
                      AuthorityService authorities, UserValidator userValidator) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
        this.userValidator = userValidator;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "reg";
        }
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage(@ModelAttribute("user") User user) {
        return "reg";
    }

}
