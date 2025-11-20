package com.uj.expenseManager.service;

import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.models.User;
import com.uj.expenseManager.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final UserService userService;

    public CategoryService(UserService userService) {

        this.userService = userService;
    }

    /**
     * Adds a category to the specified user's category list.
     *
     * @param username the username of the user
     * @param category the category to be added
     * @return the updated User object
     * @throws ResponseStatusException if the username or category is not provided, or if the user does not exist
     */
    public User addCategoryToUser(String username, String category) {
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User username and category must be provided.");
        }
        String titledCategory = StringUtils.titleize(category);
        User userInDB = userService.getUserByUsername(username);
        userInDB.getCategories().add(titledCategory);
        return userService.updateUser(userInDB);
    }

    /**
     * Deletes a category from the specified user's category list.
     *
     * @param username the username of the user
     * @param category the category to be deleted
     * @return the updated User object
     * @throws ResponseStatusException if the username or category is not provided, if the user does not exist,
     *                                 if the category is not found for the user, or if the user would have no categories left
     */
    public User deleteCategoryFromUser(String username, String category) {
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User username and category must be provided.");
        }
        User user = userService.getUserByUsername(username);
        boolean isCategoryDeleted = user.getCategories().remove(category);
        if (user.getCategories().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User must have at least one category.");
        }
        if (!isCategoryDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category " + category + " not found for user " + username);
        }
        return userService.updateUser(user);
    }
}
