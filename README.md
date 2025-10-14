# PrepX Backend

A complete Full-Stack Website for Aptitude practice and practice sets. A progress report will be saved for feedback.

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Main Features](#main-features)
- [Setup & Installation](#setup--installation)
- [Project Structure](#project-structure)
- [API & Core Components](#api--core-components)
- [Security](#security)
- [Docker Support](#docker-support)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

PrepX_Backend powers the server-side logic for the PrepX platform. It provides RESTful APIs for managing aptitude questions, user authentication (including OTP-based flows), CSV-based bulk imports, and progress tracking. The backend is built with Spring Boot and designed for security, scalability, and ease of deployment.

---

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.4+
- **Database:** PostgreSQL (via Spring Data JPA)
- **Authentication:** JWT, OTP via Email
- **Build Tool:** Maven
- **Testing:** Spring Boot Test, Spring Security Test
- **CSV Handling:** Apache Commons CSV
- **Mail:** Spring Boot Starter Mail
- **Containerization:** Docker

---

## Architecture

- **Spring Boot Application:** Entry point in `Application.java`.
- **Layered Structure:** Controllers, Services, Repositories, Helpers.
- **Security:** Custom JWT provider, password hashing, CORS configuration.
- **CSV Import:** Bulk question upload and parsing.
- **OTP Flow:** Email-based OTP for registration/login.

---

## Main Features

- **User Authentication:**
  - Email/password login.
  - OTP-based login and registration.
  - JWT token generation and validation.
- **Question Management:**
  - Bulk upload via CSV.
  - Category and difficulty filtering.
  - CRUD operations for questions.
- **Email Notifications:** For OTP and other communications.
- **Progress Tracking:** User progress and feedback.
- **CORS Config:** Supports multiple frontend origins.

---

## Setup & Installation

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL database
- Docker (optional, for containerization)

### Local Setup

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/raja2576/PrepX_Backend.git
   cd PrepX_Backend
   ```

2. **Configure Database:**
   - Set your PostgreSQL URL, username, password in `src/main/resources/application.properties`.

3. **Build the Project:**
   ```sh
   mvn clean package
   ```

4. **Run the Application:**
   ```sh
   mvn spring-boot:run
   ```
   The API server will start on port **8080** by default.

### Docker Setup

A multi-stage `Dockerfile` is provided for efficient builds:
- **Stage 1:** Uses Maven to package the app.
- **Stage 2:** Runs the JAR in a minimal Java runtime.

To build and run the Docker image:

```sh
docker build -t prepx-backend .
docker run -p 8080:8080 prepx-backend
```

---

## Project Structure

```
PrepX_Backend/
├── Dockerfile
├── pom.xml
├── src/
│   └── main/
│       └── java/com/prepXBackend/
│           ├── Application.java        # Spring Boot entry point
│           ├── config/                 # Security, JWT, app config
│           │   ├── AppConfig.java
│           │   ├── JwtConstant.java
│           │   └── JwtProvider.java
│           ├── controller/
│           │   └── otpBasedControllerAuth.java
│           ├── helper/
│           │   └── CSVHelper.java
│           ├── model/                  # Domain models (e.g., Question, User)
│           ├── repository/             # JPA repositories
│           ├── request/
│           │   ├── LoginRequest.java
│           │   └── OtpRequest.java
│           ├── response/               # Response DTOs
│           └── service/
│               ├── EmailService.java
│               └── QuestionService.java
└── README.md
```

---

## API & Core Components

### Authentication Controller (`otpBasedControllerAuth.java`)
- `/auth/register` — Registers a new user.
- `/auth/send-otp` — Sends OTP to user's email.
- `/auth/verify-otp` — Verifies OTP for login.
- `/auth/send-otp-register` — Sends OTP for registration.
- `/auth/verify-otp-register` — Verifies OTP for registration.
- Uses JWT for secure, stateless sessions.

### JWT Provider (`JwtProvider.java`)
- Generates and validates JWT tokens.
- Encodes email in token claims.
- Uses a base64 secret key for signing.

### CSV Helper (`CSVHelper.java`)
- Validates CSV format.
- Parses CSV files into Question objects.
- Enables bulk import of questions via REST API.

### Question Service (`QuestionService.java`)
- `saveQuestionsFromCSV(file)` — Bulk saves questions from CSV.
- `getQuestionsByCategoryAndDifficulty(category, difficulty)` — Fetches questions by category and difficulty.
- `getAllQuestions()` — Fetches all questions.

### Email Service (`EmailService.java`)
- Sends OTPs and other emails using Spring Boot mail.

### App Config (`AppConfig.java`)
- Configures security (BCrypt password encoder).
- Sets up CORS for multiple origins.
- Exposes headers such as Authorization.

---

## Security

- **Password Hashing:** BCrypt.
- **JWT:** Custom provider for token generation and validation.
- **CORS:** Configured for common local and deployed frontend origins.
- **OTP:** Email-based OTP stored in memory for short-term use.

---

## Docker Support

The provided `Dockerfile` supports multi-stage builds for efficient deployment. The final container exposes port 8080 and runs the compiled Spring Boot JAR.

---

## Contributing

Contributions are welcome! Please fork the repo, create a feature branch, and submit a pull request.

---

## License

MIT License (see LICENSE file).

---

## Author

Maintained by [raja2576](https://github.com/raja2576)

---

**PrepX Backend: Secure, scalable aptitude practice for everyone!**
