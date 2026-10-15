package com.expensetracker.report.service;

import com.expensetracker.report.dto.ExpenseDTO;

import java.util.List;

public interface ExportService {

    byte[] exportToExcel(List<ExpenseDTO> expenses);

    byte[] exportToPdf(List<ExpenseDTO> expenses);
}
