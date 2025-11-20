package com.uj.expenseManager.controller;

import com.uj.expenseManager.dto.UserDTO;
import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.mapper.UserMapper;
import com.uj.expenseManager.models.User;
import com.uj.expenseManager.service.UserService;
import com.uj.expenseManager.service.UserTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserTokenService userTokenService;

    /**
     * Get userDTO by username
     *
     * @param username, username of the User to fetch
     * @param authentication, authentication object to get current user details
     * @return UserDTO object if found, else 404 Not Found
     */
    @GetMapping
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username, Authentication authentication) {
        User user = userService.getUserByUsername(username);
        validateLoggedInUser(username, authentication);
        if(!userTokenService.checkIfTokenIsPresent(authentication.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or expired token");
        }
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    /**
     * Get userDTO by name (partial or full match, case-insensitive)
     *
     * @param namePart part of the name to search for
     * @return List of users matching the name part
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUserByName(@RequestParam String namePart, Authentication authentication) {
        userTokenService.checkIfTokenIsPresent(authentication.getName());
        List<User> users = userService.getUserByName(namePart);
        return ResponseEntity.ok(userMapper.toDTOList(users));
    }

    /**
     * Update existing userDTO
     *
     * @param userDTO, UserDTO object with updated details
     * @return Updated UserDTO object if found, else 404 Not Found
     */
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, Authentication authentication) {
        userTokenService.checkIfTokenIsPresent(authentication.getName());
        validateLoggedInUser(userDTO.getUsername(), authentication);
        User updatedUser = userService.updateUser(userMapper.toEntity(userDTO));
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    /**
     * Delete userDTO by username
     *
     * @param username username of the User to delete
     * @return Success message if deleted, else error message
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully!");
    }

}
