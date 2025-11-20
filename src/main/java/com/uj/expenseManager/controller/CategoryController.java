package com.uj.expenseManager.controller;

import com.uj.expenseManager.models.User;
import com.uj.expenseManager.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<User> addCategory(@RequestParam String username, @RequestParam String category) {
        User user = categoryService.addCategoryToUser(username, category);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/category")
    public ResponseEntity<User> deleteCategory(@RequestParam String username, @RequestParam String category) {
        User user = categoryService.deleteCategoryFromUser(username, category);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
