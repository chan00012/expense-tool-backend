package com.ibm.expensetool.service;

import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.core.dto.request.UserAcesss;
import com.ibm.expensetool.core.dto.request.UserRequest;
import com.ibm.expensetool.core.service.UserService;
import com.ibm.expensetool.utils.JWTokenUtility;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

@Log4j2
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTokenUtility jwTokenUtility;

    @InjectMocks
    private UserService userService;

    @Test
    public void addUser_thenAssertIfUserIsPersist() {
        UserRequest userRequest = new UserRequest("aaaaa", "robles", "cstrobles@gmail.com", "thisisnotapassword");
        User user = userService.addUser(userRequest);

        Assert.notNull(user);
    }

    @Test
    public void login_thenAssertIfItGenerateToken() {

        User user = new User("christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");
        Mockito.when(userRepository.findUserByEmail(any(String.class))).thenReturn(user);
        Mockito.when(jwTokenUtility.generateToken(any(UserAcesss.class))).thenReturn("thisisasampletoken");

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("cstrobles@gmail.com");
        userRequest.setPassword("thisisnotapassword");

        String token = userService.login(userRequest);

        Assert.notNull(token);
    }
}
