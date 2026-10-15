package com.expensetracker.report.service.impl;

import com.expensetracker.report.dto.ExpenseDTO;
import com.expensetracker.report.service.ExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    // ✅ EXCEL EXPORT
    @Override
    public byte[] exportToExcel(List<ExpenseDTO> expenses) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Expenses");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Category");
            header.createCell(2).setCellValue("Amount");
            header.createCell(3).setCellValue("Description");
            header.createCell(4).setCellValue("Date");

            int rowIdx = 1;

            for (ExpenseDTO e : expenses) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(e.getId());
                row.createCell(1).setCellValue(e.getCategoryName());
                row.createCell(2).setCellValue(e.getAmount());
                row.createCell(3).setCellValue(e.getDescription());
                row.createCell(4).setCellValue(e.getDate().toString());
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel export failed", e);
        }
    }

    // ✅ PDF EXPORT
    @Override
    public byte[] exportToPdf(List<ExpenseDTO> expenses) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            document.add(new Paragraph("Expense Report\n\n"));

            for (ExpenseDTO e : expenses) {
                document.add(new Paragraph(
                        "ID: " + e.getId() +
                                " | Category: " + e.getCategoryName() +
                                " | Amount: " + e.getAmount() +
                                " | Date: " + e.getDate()
                ));
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF export failed", e);
        }
    }
}
