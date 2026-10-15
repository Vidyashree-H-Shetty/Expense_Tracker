package com.expensetracker.expense.service.impl;

import com.expensetracker.expense.dto.ExpenseDTO;
import com.expensetracker.expense.entity.Category;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.entity.Budget;
import com.expensetracker.expense.exception.ResourceNotFoundException;
import com.expensetracker.expense.repository.CategoryRepository;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.expense.repository.BudgetRepository;
import com.expensetracker.expense.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Expense addExpense(ExpenseDTO dto) {

        // ✅ 1. Fetch Category
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ 2. Budget Validation
        Optional<Budget> budgetOpt = budgetRepository
                .findByUserIdAndCategoryId(dto.getUserId(), dto.getCategoryId());

        if (budgetOpt.isPresent()) {

            Budget budget = budgetOpt.get();

            Double totalSpent = expenseRepository
                    .sumByUserIdAndCategoryId(dto.getUserId(), category.getId()); // ✅ FIXED

            if (totalSpent == null) totalSpent = 0.0;

            if (totalSpent + dto.getAmount() > budget.getLimitAmount()) {
                throw new RuntimeException(
                        "Budget exceeded for category: " + category.getName()
                );
            }
        }

        // ✅ 3. Save Expense
        Expense expense = new Expense();
        expense.setUserId(dto.getUserId());
        expense.setCategory(category);
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getUserExpenses(int userId) {
        return expenseRepository.findByUserId((long)userId);
    }

    @Override
    public Expense updateExpense(Long id, ExpenseDTO dto) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        expense.setUserId(dto.getUserId());
        expense.setCategory(category);
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
