import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  private baseUrl = 'http://localhost:8080/budget';

  constructor(private http: HttpClient) {}

  setBudget(data: any) {
    return this.http.post(this.baseUrl, data);
  }

  getBudget(userId: number, categoryId: number) {
    return this.http.get(`${this.baseUrl}/${userId}/${categoryId}`);
  }
}
