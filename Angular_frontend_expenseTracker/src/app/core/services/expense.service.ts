import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense } from 'src/app/models/expense.model';

@Injectable({ providedIn: 'root' })
export class ExpenseService {
  getCategories() {
    throw new Error('Method not implemented.');
  }

  private baseUrl = 'http://localhost:8080/expenses';

  constructor(private http: HttpClient) {}

  addExpense(expense: Expense): Observable<any> {
    return this.http.post(this.baseUrl, expense);
  }

  getExpenses(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}`);
  }

  deleteExpense(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  exportPdf(userId: number) {
    return this.http.get(
      `http://localhost:8080/reports/export/pdf/${userId}`,
      { responseType: 'blob' }
    );
  }
  
  exportExcel(userId: number) {
    return this.http.get(
      `http://localhost:8080/reports/export/excel/${userId}`,
      { responseType: 'blob' }
    );
  }
  
  
}
