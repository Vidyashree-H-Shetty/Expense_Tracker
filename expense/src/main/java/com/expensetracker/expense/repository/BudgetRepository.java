package com.expensetracker.expense.repository;

import com.expensetracker.expense.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
