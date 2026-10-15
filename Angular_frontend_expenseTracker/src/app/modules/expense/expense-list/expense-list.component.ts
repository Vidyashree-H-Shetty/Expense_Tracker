import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../../../core/services/expense.service';
import { CategoryService } from '../../../core/services/category.service';
import { BudgetService } from '../../../core/services/budget.service';

@Component({
  selector: 'app-expense-list',
  templateUrl: './expense-list.component.html',
  styleUrls: ['./expense-list.component.css']
})
export class ExpenseListComponent implements OnInit {
addExpense() {
throw new Error('Method not implemented.');
}

  expenses: any[] = [];

  categories: any[] = [];
  selectedCategoryId: number = 0;

  newCategory = '';
  budgetAmount: number = 0;
amount: any;
description: any;
date: any;


  constructor(
    private service: ExpenseService,
    private categoryService: CategoryService,
    private budgetService: BudgetService
  ) {}

  ngOnInit() {
    this.loadExpenses();
    this.loadCategories(); 
  }

  loadExpenses() { 
    this.service.getExpenses(1).subscribe(res => { 
      this.expenses = res; 
    }); 
  }
  deleteExpense(id: number) { 
    if (confirm('Are you sure you want to delete this expense?')) { 
      this.service.deleteExpense(id).subscribe({ next: () => { alert('Deleted successfully'); this.loadExpenses(); 
    }, 
    error: err => { 
      console.error(err); 
      alert('Delete failed'); 
    } }); 
  }}

  

  loadCategories() {
    const userId = 1;

    
    this.categoryService.getCategories(userId)
      .subscribe({
        next: (res: any) => {
          this.categories = res;
        },
        error: (err: any) => {
          console.error(err);
          // alert('Failed to load categories');
        }
      });
  }

  addCategory() {
    const payload = {
      name: this.newCategory,
      userId: 1
    };

    this.categoryService.addCategory(payload)
      .subscribe({
        next: () => {
          alert('Category added');
          this.newCategory = '';
          this.loadCategories();
        },
        error: (err: any) => {
          console.error(err);
          alert('Error adding category');
        }
      });
  }

  

  setBudget() {
    const payload = {
      userId: 1,
      categoryId: this.selectedCategoryId,
      limitAmount: this.budgetAmount
    };

    this.budgetService.setBudget(payload)
      .subscribe({
        next: () => {
          alert('Budget set successfully');
          this.budgetAmount = 0;
        },
        error: (err: any) => {
          console.error(err);
          alert('Error setting budget');
        }
      });
  }
  downloadPdf() {
    const userId = 1;

    this.service.exportPdf(userId).subscribe(blob => {
      const file = new Blob([blob], { type: 'application/pdf' });

      const url = window.URL.createObjectURL(file);
      const a = document.createElement('a');

      a.href = url;
      a.download = 'expenses.pdf';
      a.click();

      window.URL.revokeObjectURL(url);
    });
  }

  downloadExcel() {
    const userId = 1;

    this.service.exportExcel(userId).subscribe(blob => {
      const file = new Blob([blob], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });

      const url = window.URL.createObjectURL(file);
      const a = document.createElement('a');

      a.href = url;
      a.download = 'expenses.xlsx';
      a.click();

      window.URL.revokeObjectURL(url);
    });
  }
}
