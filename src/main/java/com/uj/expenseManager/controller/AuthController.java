package com.uj.expenseManager.controller;

import com.uj.expenseManager.dto.UserDTO;
import com.uj.expenseManager.dto.UserLoginDTO;
import com.uj.expenseManager.mapper.UserMapper;
import com.uj.expenseManager.models.User;
import com.uj.expenseManager.security.JwtUtil;
import com.uj.expenseManager.service.UserService;
import com.uj.expenseManager.service.UserTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserTokenService userTokenService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.register(userMapper.toEntity(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO dto) {
        User user = userService.authenticate(dto);
        String token = jwtUtil.generateToken(user.getUsername());
        userTokenService.saveOrUpdateToken(dto.getUsername(), token);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        String username = authentication.getName();
        userTokenService.deleteTokenByUsername(username);
        return ResponseEntity.ok("Logged out successfully. Please delete token on client side.");
    }
}
