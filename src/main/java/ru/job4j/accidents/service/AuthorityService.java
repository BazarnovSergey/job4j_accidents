package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;

@Service
public interface AuthorityService {

    Authority findByAuthority(String authority);

}
