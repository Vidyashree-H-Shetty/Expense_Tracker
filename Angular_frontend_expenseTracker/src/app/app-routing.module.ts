import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { ExpenseFormComponent } from './modules/expense/expense-form/expense-form.component';
import { DashboardComponent } from './modules/dashboard/dashboard/dashboard.component';


const routes: Routes = [

  
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  
  
  
  
  
  
  
  
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  {
    path: 'expenses',
    loadChildren: () =>
      import('./modules/expense/expense.module')
        .then(m => m.ExpenseModule),
    canActivate: [AuthGuard]
  },

  
  {
    path: 'login',
    loadChildren: () =>
      import('./modules/auth/auth.module')
        .then(m => m.AuthModule)
  },

  { path: 'add-expense', component: ExpenseFormComponent, canActivate: [AuthGuard] },

  {
    path: 'reports',
    loadChildren: () =>
      import('./modules/reports/reports.module')
        .then(m => m.ReportsModule),
    canActivate: [AuthGuard]
  },

  { path: '**', redirectTo: 'login' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
