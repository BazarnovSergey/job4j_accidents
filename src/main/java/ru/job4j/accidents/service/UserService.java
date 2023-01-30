package ru.job4j.accidents.service;

import ru.job4j.accidents.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> getUser(String username);
}
