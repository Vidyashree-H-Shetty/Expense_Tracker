package com.expensetracker.report.dto;

import java.time.LocalDate;

public class ExpenseDTO {

    private Long id;
    private Long userId;
    private String categoryName;
    private Double amount;
    private String description;
    private LocalDate date;

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getCategoryName() { return categoryName; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }
}
