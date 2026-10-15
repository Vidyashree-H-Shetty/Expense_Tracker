import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ExpenseService } from '../../../core/services/expense.service';
import { CategoryService } from '../../../core/services/category.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-expense-form',
  standalone: true,
  imports: [FormsModule, CommonModule], 
  templateUrl: './expense-form.component.html',
  styleUrls: ['./expense-form.component.css']
})
export class ExpenseFormComponent implements OnInit {

  categories: any[] = [];
  userId: number = 1; 

  expense = {
    userId: this.userId, 
    categoryId: 0, 
    amount: 0,
    description: '',
    date: ''
  };

  constructor(
    private expenseService: ExpenseService,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories() {
    
    this.categoryService.getCategories(this.userId).subscribe({
      next: (data: any) => { 
        this.categories = data;
      },
      error: (err: any) => {
        console.error('Error fetching categories', err);
      }
    });
  }

  onSubmit() {
    if (this.expense.categoryId === 0) {
      alert("Please select a category");
      return;
    }

    this.expenseService.addExpense(this.expense).subscribe({
      next: (res: any) => {
        alert("Expense Added Successfully!");
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        
        const msg = err.error?.message || "Error adding expense";
        alert(msg);
      }
    });
  }
}