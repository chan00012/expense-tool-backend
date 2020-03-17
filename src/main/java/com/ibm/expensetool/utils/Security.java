package com.ibm.expensetool.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

public class Security {

    public static String bcrypt(String password) {
        String bcryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(4));
        return bcryptedPassword;
    }

    public static boolean verifyBcrypt(String password, String hashPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashPassword);
    }

    public static String base64Encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String base64Decode(String passwordBase64) {
        return new String(Base64.getDecoder().decode(passwordBase64));
    }


}
