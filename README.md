# 💰 Expense Tracker with Reports (Microservices Architecture)

A **full-stack, production-ready Expense Tracker application** built using **Spring Boot Microservices, PostgreSQL, and Angular**.

This project demonstrates **scalable backend architecture**, **secure JWT authentication**, **data analytics**, and a **modern UI dashboard with charts**.

---

## 🚀 Features

### 🔐 Authentication & Security

* JWT-based authentication
* Secure API Gateway routing
* Password encryption using BCrypt

### 💸 Expense Management

* Add, edit, delete expenses
* Categorize expenses (Food, Travel, Bills, etc.)
* Filter by date & category
* Pagination support

### 📊 Reports & Analytics

* Monthly expense reports
* Category-wise spending analysis
* Interactive charts (Chart.js)
* Dashboard insights

### 🔔 Notifications

* Budget threshold alerts
* Email notifications (SMTP)

### 📁 Export Functionality

* Export reports as:

  * Excel (Apache POI)
  * PDF (OpenPDF/iText)

### 🌙 UI/UX

* Dark mode UI
* Responsive design
* Clean Angular architecture

---

## 🏗️ System Architecture

```
Client (Angular)
       |
       v
API Gateway (Spring Cloud Gateway)
       |
-------------------------------------------------
|        |           |           |               |
User   Expense     Report    Notification     Eureka
Service Service    Service      Service       Server
```

### 🔧 Microservices

| Service                  | Responsibility                  |
| ------------------------ | ------------------------------- |
| **User Service**         | Authentication, user management |
| **Expense Service**      | CRUD operations for expenses    |
| **Report Service**       | Analytics & report generation   |
| **Notification Service** | Email alerts                    |
| **API Gateway**          | Routing & JWT validation        |
| **Eureka Server**        | Service discovery               |

---

## 🛠️ Tech Stack

### Backend

* Java 25
* Spring Boot
* Spring Cloud (Gateway, Eureka)
* Spring Security (JWT)
* OpenFeign

### Frontend

* Angular
* Chart.js
* Bootstrap / CSS

### Database

* PostgreSQL

### Tools

* Maven
* Git & GitHub
* Postman

---

## 📂 Project Structure

```
expense-tracker/
│
├── eureka-server/
├── api-gateway/
├── user-service/
├── expense-service/
├── report-service/
├── notification-service/
└── frontend-angular/
```

---

## ⚙️ Setup Instructions

### 🔹 1. Clone Repository

```bash
git clone https://github.com/your-username/expense-tracker.git
cd expense-tracker
```

---

### 🔹 2. PostgreSQL Setup

Create database:

```sql
CREATE DATABASE expense_tracker;
```

Update `application.yml` in services:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/expense_tracker
    username: postgres
    password: your_password
```

---

### 🔹 3. Run Backend Services (Order Important ⚠️)

1. Start Eureka Server
2. Start API Gateway
3. Start User Service
4. Start Expense Service
5. Start Report Service
6. Start Notification Service

Eureka Dashboard:

```
http://localhost:8761
```

---

### 🔹 4. Run Frontend

```bash
cd frontend-angular
npm install
ng serve
```

App runs at:

```
http://localhost:4200
```

---

## 🔐 API Usage

### Login

```http
POST /auth/login
```

```json
{
  "email": "test@gmail.com",
  "password": "1234"
}
```

Response:

```
JWT TOKEN
```

---

### Add Expense

```http
POST /expenses
Authorization: Bearer <TOKEN>
```

---

### Get Reports

```http
GET /reports/monthly
Authorization: Bearer <TOKEN>
```

---

## 📊 Dashboard Preview

* Monthly expense trends
* Category-wise pie charts
* Budget alerts visualization

---

## 🧪 Testing

Use **Postman** or frontend UI:

* Test authentication
* Validate JWT-protected APIs
* Check report generation
* Verify export functionality

---

## 🔥 Highlights (Why this project stands out)

* Microservices architecture (industry-level)
* JWT security with API Gateway
* Real-time analytics dashboard
* Excel & PDF export
* Clean code & layered architecture
* Scalable and production-ready design

---

## 📌 Future Enhancements

* Docker & Kubernetes deployment
* CI/CD pipeline (GitHub Actions)
* Role-based access control (RBAC)
* Mobile app (Flutter/React Native)
* AI-based expense prediction

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and share it!
