# 📚 BookApp – Share & Discover Books

A personal full-stack project built with Spring Boot (Java) and Vue.js. BookApp allows users to register, log in, add books with images, browse by category, and switch between light/dark mode.

## 🚀 Tech Stack

**Backend:**
- Java 17
- Spring Boot
- Spring Security + JWT Authentication
- MySQL
- RESTful API

**Frontend:**
- Vue.js 3
- Pinia & Axios
- Tailwind CSS
- Dark mode toggle

## 🔑 Features

- 🔐 User authentication (JWT)
- 📖 Add, update & delete books
- 🖼️ Book covers via image URL
- 📂 Filter books by category
- 🌙 Dark/Light mode toggle
- 🖥️ Responsive design (mobile-friendly)

## 🎯 Motivation

This app was created as part of my personal learning journey after completing the Java Developer program at WIFI Steiermark. It demonstrates my ability to build modern full-stack web applications from scratch, without any external templates or scaffolding.

## 📁 Project Structure

```
bookapp/
├── backend/    Spring Boot REST API (port 8081)
│   └── src/main/java/com/bookapp/
│       ├── config/       security + CORS
│       ├── controller/   REST endpoints (/api/auth, /api/books)
│       ├── dto/          request/response objects
│       ├── exception/    error handling (clean JSON error messages)
│       ├── model/        JPA entities (User, Book)
│       ├── repository/   database access
│       ├── security/     JWT creation + validation
│       └── service/      business logic
└── frontend/   Vue 3 + Vite + Tailwind (port 5175)
```

## 🛠️ Setup Instructions

### 1. Database
Create an empty MySQL database:
```sql
CREATE DATABASE bookapp;
```

### 2. Backend (Spring Boot)
The database password is **not** stored in the code. Set it as an environment variable before starting:

```bash
cd backend
# Linux / macOS
export DB_PASSWORD=your_mysql_password
# Windows PowerShell
$env:DB_PASSWORD="your_mysql_password"

./mvnw spring-boot:run
```

Optional environment variables:

| Variable | Default | Meaning |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/bookapp` | database URL |
| `DB_USERNAME` | `root` | database user |
| `DB_PASSWORD` | *(empty)* | database password |
| `JWT_SECRET` | demo key | Base64 key for signing logins – **set your own in production** |
| `CORS_ORIGINS` | `http://localhost:5173,http://localhost:5175` | allowed frontend URLs |

In IntelliJ you can set them under *Run → Edit Configurations → Environment variables*.

### 3. Frontend (Vue)
```bash
cd frontend
npm install
npm run dev
```
Open http://localhost:5175

## 🔌 API

| Method | URL | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/register` | – | create account, returns JWT |
| POST | `/api/auth/login` | – | login, returns JWT |
| GET | `/api/books` | ✅ | your books |
| GET | `/api/books/{id}` | ✅ | one of your books |
| POST | `/api/books` | ✅ | add a book |
| PUT | `/api/books/{id}` | ✅ | update your book |
| DELETE | `/api/books/{id}` | ✅ | delete your book |
