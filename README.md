# 🔐 UC18 – Google Authentication & User Management

📅 **Date:** 23 March 2026  
🌿 **Branch:** `feature/UC18-GoogleAuthUserManagement`

---

## Objective

This use case focuses on securing the **Quantity Measurement Application** by implementing a multi-layered authentication system using:

- Google OAuth2
- JWT (JSON Web Tokens)

---

## Key Goals

- Integrate Google Sign-In for seamless user authentication  
- Implement JWT-based stateless authorization for API security  
- Secure all measurement and history endpoints from unauthorized access  
- Manage user identities and roles using a persistent database layer  

---

## Implementation Details

### 1. 🔒 Security Configuration (Spring Security)

Created `SecurityConfig` to manage the security filter chain:

- Enabled OAuth2 Login with Google as the provider  
- Configured Stateless Session Management (no `JSESSIONID`)  
- Set up public vs. protected route access (`/auth/**` is public)  
- Disabled CSRF for REST API compatibility  

---

### 2. Identity & Token Management (JWT)

Developed a custom `JwtService` for:

- **Token Generation** → Creating signed tokens upon successful login  
- **Token Validation** → Checking signature, expiration, and user claims  
- **Extraction** → Retrieving user details from the `Authorization: Bearer` header  

Implemented `JwtAuthenticationFilter`:

- Intercepts every request  
- Validates JWT before it reaches the controller  

---

### 3. User Service & Persistence

Enhanced `CustomUserDetailsService`:

- Integrates with `UserRepository` to fetch user details  
- Maps Google-authenticated users to local database records  

#### 🔐 Password Protection

- Used `BCryptPasswordEncoder` for secure credential storage  

---

### 4. Authentication Flow

1. **User Login**  
   User authenticates via Google OAuth2 or custom Login API  

2. **Validation**  
   Backend validates credentials / OAuth callback  

3. **JWT Issuance**  
   A unique token is generated and sent to the client  

4. **Authorized Request**  
   Client sends JWT in header:
   ```
   Authorization: Bearer <JWT>
   ```

5. **Access Granted**  
   Filters verify token and allow access to:
   - `/compare`
   - `/add`
   - `/history`

---

### 5. Testing & Quality Assurance

Updated test suite using **JUnit 5** and **MockMvc**:

#### Security Tests
- `401 Unauthorized` → Missing/invalid token  
- `200 OK` → Valid token  

#### Service Tests
- Verified JWT generation and parsing logic  

#### 🔧 Additional Practices
- Used `@WithMockUser` for controller testing  
- Applied Mockito for mocking  
- Used Lombok for clean DTOs  

---

## Tech Stack

- Spring Boot  
- Spring Security  
- Google OAuth2  
- JWT (JSON Web Token)  
- H2 Database  
- Project Lombok  
- JUnit 5 / Mockito  

---

## 🔗 Source Code

📁 [feature/UC18-GoogleAuthUserManagement](https://github.com/abhishekkushwaha-2003/QuantityMeasurementApp/tree/feature/UC18-GoogleAuthUserManagement)

---
