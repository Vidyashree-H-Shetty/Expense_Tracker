package com.expensetracker.expense.repository;

import com.expensetracker.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.category.id = :categoryId")
    Double sumByUserIdAndCategoryId(Long userId, Long categoryId);

}
