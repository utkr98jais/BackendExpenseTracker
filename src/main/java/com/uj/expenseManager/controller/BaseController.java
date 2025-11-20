package com.uj.expenseManager.controller;

import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

public class BaseController {
    public void validateLoggedInUser(String username, Authentication auth) {
        if (!StringUtils.hasLength(username) || auth == null || !StringUtils.hasLength(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied!");
        }
        if (!username.equals(auth.getName())) {
            System.out.println("Access denied! User " + auth.getName() + " tried to access data of user " + username);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied!");
        }
    }
}
