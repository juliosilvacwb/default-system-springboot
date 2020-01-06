package com.springboot.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * PasswordUtils
 */
public class PasswordUtils {

    /**
     * Encode password using BCrypt
     *
     * @param password
     * @return String
     */
    public static String encode(String password) {

        if (StringUtils.isEmpty(password)) {
            return password;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
