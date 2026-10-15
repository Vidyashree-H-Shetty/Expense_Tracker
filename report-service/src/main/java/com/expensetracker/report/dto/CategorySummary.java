package com.expensetracker.report.dto;

public class CategorySummary {

    private String category;
    private Double total;

    public CategorySummary(String category, Double total) {
        this.category = category;
        this.total = total;
    }

    public String getCategory() { return category; }
    public Double getTotal() { return total; }
}
