import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../../../core/services/expense.service';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  expenses: any[] = [];

  totalExpense = 0;
  monthlyExpense = 0;

  
  averageDailyExpense = 0;
  highestExpense: any = null;

  topCategory = '';
  topCategoryAmount = 0;
  spendingTrend = '';
  alerts: string[] = [];

  
  public pieChartType: ChartType = 'pie';
  public pieChartData: ChartData<'pie'> = {
    labels: [],
    datasets: [{ data: [] }]
  };

  public pieChartOptions = {
    responsive: true,
    maintainAspectRatio: false
  };

  
  public lineChartType: ChartType = 'line';
  public lineChartData: ChartData<'line'> = {
    labels: [],
    datasets: [{ data: [], label: 'Monthly Spend' }]
  };

  
  public barChartType: ChartType = 'bar';
  public barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{ data: [], label: 'Category Spend' }]
  };

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    const userId = 1;

    this.expenseService.getExpenses(userId).subscribe({
      next: (data: any[]) => {
        this.expenses = data;
        this.calculateStats();
        this.prepareCharts();
        this.generateInsights();
      },
      error: (err) => console.error(err)
    });
  }

  calculateStats() {
    const currentMonth = new Date().getMonth();
    const today = new Date().getDate();

    this.totalExpense = this.expenses.reduce((sum, e) => sum + e.amount, 0);

    this.monthlyExpense = this.expenses
      .filter(e => new Date(e.date).getMonth() === currentMonth)
      .reduce((sum, e) => sum + e.amount, 0);

    this.averageDailyExpense = this.monthlyExpense / (today || 1);

    this.highestExpense = this.expenses.reduce((max, e) => {
      return (!max || e.amount > max.amount) ? e : max;
    }, null);
      }

  prepareCharts() {
    const categoryMap: Record<string, number> = {};
    const monthlyMap: Record<string, number> = {};

    this.expenses.forEach(e => {
      const category = e.category?.name || 'Other';
      const month = new Date(e.date).toLocaleString('default', { month: 'short' });

      categoryMap[category] = (categoryMap[category] || 0) + e.amount;
      monthlyMap[month] = (monthlyMap[month] || 0) + e.amount;
    });

    
    const categoryValues = Object.values(categoryMap) as number[];
    const categoryLabels = Object.keys(categoryMap);

    const monthlyValues = Object.values(monthlyMap) as number[];
    const monthlyLabels = Object.keys(monthlyMap);

    
    this.pieChartData = {
      labels: categoryLabels,
      datasets: [{ data: categoryValues }]
    };

    
    this.barChartData = {
      labels: categoryLabels,
      datasets: [{ data: categoryValues, label: 'Category Spend' }]
    };

    
    this.lineChartData = {
      labels: monthlyLabels,
      datasets: [{ data: monthlyValues, label: 'Monthly Spend' }]
    };
  }

  generateInsights() {
    if (this.expenses.length === 0) return;

    const categoryMap: Record<string, number> = {};

    this.expenses.forEach(e => {
      const category = e.category?.name || 'Other';
      categoryMap[category] = (categoryMap[category] || 0) + e.amount;
    });

    const sorted = Object.entries(categoryMap)
      .sort((a, b) => b[1] - a[1]);

    this.topCategory = sorted[0][0];
    this.topCategoryAmount = sorted[0][1];

    
    const half = Math.floor(this.expenses.length / 2);

    const firstHalf = this.expenses
      .slice(0, half)
      .reduce((sum, e) => sum + e.amount, 0);

    const secondHalf = this.expenses
      .slice(half)
      .reduce((sum, e) => sum + e.amount, 0);

    this.spendingTrend =
      secondHalf > firstHalf ? 'Increasing 📈' : 'Decreasing 📉';

    
    this.alerts = [];

    if (this.topCategoryAmount > this.totalExpense * 0.4) {
      this.alerts.push(`⚠ High spending on ${this.topCategory}`);
    }

    if (this.averageDailyExpense > 1000) {
      this.alerts.push('🚨 Daily spending is high');
    }

    if (this.monthlyExpense > this.totalExpense * 0.8) {
      this.alerts.push('⚠ Nearing monthly limit');
    }

    if (this.alerts.length === 0) {
      this.alerts.push('✅ Spending looks healthy');
    }
  }
}
