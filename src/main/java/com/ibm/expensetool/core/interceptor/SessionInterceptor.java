package com.ibm.expensetool.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.expensetool.core.dto.request.UserAcesss;
import com.ibm.expensetool.utils.JWTokenUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static final String USER_ACCESS = "userAccess";
    private static final String X_SESSION = "x-session";

    @Autowired
    private UserAcesss userAcesss;

    @Autowired
    private JWTokenUtility tokenUtility;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = request.getHeader(X_SESSION);

        if (jwt == null || jwt.isEmpty()) {
            throw new JwtException("Session token might be null or empty");
        }

        Jws<Claims> claims = tokenUtility.verifyToken(jwt);
        UserAcesss userAcesssClaims = objectMapper.readValue(claims.getBody().get(USER_ACCESS).toString(), UserAcesss.class);
        this.userAcesss.setUserId(userAcesssClaims.getUserId());
        log.info("|JWT EXTRACT| USER ACCESS SESH: {}", this.userAcesss);

        return true;
    }

}