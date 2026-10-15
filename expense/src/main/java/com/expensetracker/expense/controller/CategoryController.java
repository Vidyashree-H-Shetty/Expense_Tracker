package com.expensetracker.expense.controller;

import com.expensetracker.expense.entity.Category;
import com.expensetracker.expense.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repo;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        System.out.println("HIT CREATE CATEGORY API");
        return repo.save(category);
    }


    @GetMapping
    public List<Category> getAll() {
        return repo.findAll();
    }
    @GetMapping("/user/{userId}")
    public List<Category> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }
}
