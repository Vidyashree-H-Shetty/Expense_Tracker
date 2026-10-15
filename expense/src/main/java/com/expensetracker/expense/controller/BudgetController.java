package com.expensetracker.expense.controller;

import com.expensetracker.expense.entity.Budget;
import com.expensetracker.expense.repository.BudgetRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetRepository budgetRepository;

    public BudgetController(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // ✅ CREATE BUDGET
    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetRepository.save(budget);
    }
}
