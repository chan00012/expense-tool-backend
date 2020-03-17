package com.ibm.expensetool.core.controller;

import com.ibm.expensetool.core.dto.request.UserRequest;
import com.ibm.expensetool.core.dto.response.ServerResponse;
import com.ibm.expensetool.core.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public void createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("|CREATE USER REQUEST BODY| - {}", userRequest);
        userService.addUser(userRequest);
    }

    @PostMapping("/login")
    public ServerResponse login(@RequestBody UserRequest userRequest) {
        log.info("|LOGIN REQUEST BODY| - {}", userRequest);
        String token = userService.login(userRequest);
        ServerResponse serverResponse = ServerResponse.builder().token(token).build();
        return serverResponse;
    }
}
