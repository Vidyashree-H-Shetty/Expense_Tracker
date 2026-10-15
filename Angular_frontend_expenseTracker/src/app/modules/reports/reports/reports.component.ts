import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../../core/services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  summary: any;

  constructor(private service: ReportService) {}

  ngOnInit() {
    this.service.getSummary(1).subscribe(res => {
      this.summary = res;
    });
  }

  downloadPdf() {
    this.service.exportPdf(1).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }

  downloadExcel() {
    this.service.exportExcel(1).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }
}
