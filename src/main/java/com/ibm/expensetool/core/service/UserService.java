package com.ibm.expensetool.core.service;

import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.core.dto.request.UserAcesss;
import com.ibm.expensetool.core.dto.request.UserRequest;
import com.ibm.expensetool.core.exception.ServerException.ExistingUserException;
import com.ibm.expensetool.core.exception.ServerException.InvalidCredentialsException;
import com.ibm.expensetool.core.exception.ServerException.NotExistingUserException;
import com.ibm.expensetool.utils.JWTokenUtility;
import com.ibm.expensetool.utils.Security;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTokenUtility jwTokenUtility;

    public User addUser(UserRequest userRequest) {
        User user = userRepository.findUserByEmail(userRequest.getEmail());

        if (user != null) throw new ExistingUserException();


        User newUser = User.builder()
                                        .firstName(userRequest.getFirstName())
                                        .lastName(userRequest.getLastName())
                                        .email(userRequest.getEmail())
                                        .password(Security.bcrypt(userRequest.getPassword()))
                                        .build();

        userRepository.save(newUser);
        log.info("|NEW USER ADDED| - {}", newUser);

        return newUser;
    }

    public String login(UserRequest userRequest) {
        User user = userRepository.findUserByEmail(userRequest.getEmail());

        if (user == null) throw new NotExistingUserException();

        boolean isPasswordCorrect = Security.verifyBcrypt(userRequest.getPassword(), user.getPassword());

        String token = null;

        if (isPasswordCorrect) {
            token = jwTokenUtility.generateToken(new UserAcesss(user.getUserId()));
            log.debug("|GENERATED TOKEN| - {}", token);
        } else {
            throw new InvalidCredentialsException();
        }

        return token;
    }
}
