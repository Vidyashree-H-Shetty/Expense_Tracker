import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ReportService {

  private baseUrl = 'http://localhost:8080/reports';

  constructor(private http: HttpClient) {}

  getSummary(userId: number) {
    return this.http.get<any>(`${this.baseUrl}/total/${userId}`);
  }

  exportPdf(userId: number) {
    return this.http.get(`${this.baseUrl}/export/pdf/${userId}`, { responseType: 'blob' });
  }

  exportExcel(userId: number) {
    return this.http.get(`${this.baseUrl}/export/excel/${userId}`, { responseType: 'blob' });
  }
}
