package de.muellermarius.denomination_calculator.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @GetMapping("/token")
    public UUID createUserToken() {
        return UUID.randomUUID();
    }
}
