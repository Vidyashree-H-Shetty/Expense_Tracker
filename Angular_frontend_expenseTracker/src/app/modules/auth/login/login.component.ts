















 










import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  isLoginMode = true;

  
  form = { 
    email: '', 
    password: '', 
    name: '' 
  };

  constructor(private auth: AuthService, private router: Router) {}

  

  showPassword = false;

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
  }


  
  handleSubmit() {
    if (this.isLoginMode) {
      this.login();
    } else {
      this.register();
    }
  }

  login() {
    
    const loginData = { email: this.form.email, password: this.form.password };
    
    this.auth.login(loginData).subscribe({
      next: (res: any) => {
        this.auth.saveToken(res.token);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Login failed', err);
        alert('Invalid email or password');
      }
    });
  }

  register() {
    
    this.auth.register(this.form).subscribe({
      next: (res: any) => {
        alert('Registration successful! Please login.');
        this.isLoginMode = true; 
      },
      error: (err) => {
        console.error('Registration failed', err);
        alert('Could not create account. Please try again.');
      }
    });
  }
}