import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'http://localhost:8080/categories';

  constructor(private http: HttpClient) {}

  getCategories(userId: number) {
    
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }

  addCategory(data: any) {
    return this.http.post(this.baseUrl, data);
  }
}