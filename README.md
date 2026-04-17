
<h1 align="center"> Quantity Measurement App</h1>
<h3 align="center">Microservice Architecture | Spring Boot | Spring Cloud | JWT</h3>

<br/>

---

## 📖 Project Overview

```
╔══════════════════════════════════════════════════════════════════════╗
║  Originally a Monolithic Spring Boot App → Converted to Microservices ║
╚══════════════════════════════════════════════════════════════════════╝
```

**Quantity Measurement App** is a production-ready **Spring Boot Microservice** project that supports:

| Operation | Description |
|-----------|-------------|
| ➕ **Add** | Add two quantities (auto unit conversion) |
| ➖ **Subtract** | Subtract two quantities |
| ✖️ **Multiply** | Multiply a quantity by a factor |
| ➗ **Divide** | Divide a quantity by a factor |
| ⚖️ **Compare** | Compare if two quantities are equal |
| 🔄 **Convert** | Convert a value to another unit |

### 🧮 Supported Measurement Types

```
📏 LengthUnit      →   FEET  |  INCHES  |  YARDS  |  CENTIMETERS
⚖️  WeightUnit     →   MILLIGRAM  |  GRAM  |  KILOGRAM  |  POUND  |  TONNE
🧪 VolumeUnit      →   LITRE  |  MILLILITRE  |  GALLON
🌡️  TemperatureUnit →   CELSIUS  |  FAHRENHEIT  |  KELVIN
```

### 🔑 Core Capabilities

- 🔐 **JWT-based Authentication** — secure, stateless sessions
- 🐙 **GitHub OAuth2 Login** — social login support
- 🗃️ **Operation History** — every operation is persisted to MySQL
- ⚡ **Reactive API Gateway** — non-blocking, WebFlux-based routing
- 🔍 **Eureka Service Discovery** — dynamic service registration
- 📚 **Swagger Documentation** — interactive API explorer on each service

---

## 🏗️ Microservice Architecture


```
                    ┌──────────────────────────────┐
                    │      Frontend (React)         │
                    │       localhost:3000          │
                    └──────────────┬───────────────┘
                                   │  HTTP Requests
                                   ▼
                    ╔══════════════════════════════╗
                    ║         API GATEWAY          ║  ◄── Single Entry Point
                    ║        localhost:8080        ║
                    ║   • JWT Validation           ║
                    ║   • Request Routing          ║
                    ║   • CORS Handling            ║
                    ║   • Load Balancing (lb://)   ║
                    ╚══════════════┬═══════════════╝
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
              ▼                    ▼                    ▼
   ╔══════════════════╗  ╔══════════════════╗  ╔══════════════════╗
   ║  SECURITY        ║  ║  QUANTITY        ║  ║  EUREKA SERVER   ║
   ║  SERVICE         ║  ║  MEASUREMENT     ║  ║  localhost:8761  ║
   ║  localhost:8081  ║  ║  APP             ║  ║                  ║
   ║                  ║  ║  localhost:8082  ║  ║  • Service       ║
   ║  • Register      ║  ║                  ║  ║    Registry      ║
   ║  • Login         ║  ║  • Add/Subtract  ║  ║  • Heartbeats    ║
   ║  • JWT Issue     ║  ║  • Multiply/Div  ║  ║  • Health Check  ║
   ║  • OAuth2        ║  ║  • Compare       ║  ║  • Dashboard     ║
   ║  • BCrypt        ║  ║  • Convert       ║  ║                  ║
   ║  • User CRUD     ║  ║  • History       ║  ╚══════════════════╝
   ╚════════╤═════════╝  ╚════════╤═════════╝
            │                    │
            ▼                    ▼
   ┌─────────────────┐  ┌─────────────────┐
   │   security_db   │  │   quantity_db   │
   │    (MySQL)      │  │    (MySQL)      │
   |  [User Details] |   |   [Operations]  |
   └─────────────────┘  └─────────────────┘
```


> 💡 **Key Design:** The API Gateway is the **only public-facing component**. All other services are internal and communicate via Eureka discovery.

---

## 📁 Project Structure

```
📦 microservice-architecture/
│
├── 📂 eureka-server/                         🔵 Service Registry (Port: 8761)
│   ├── 📂 src/main/java/com/eureka/
│   │   └── 📄 EurekaServerApplication.java
│   ├── 📂 src/main/resources/
│   │   └── 📄 application.yml
│   └── 📄 pom.xml
│
├── 📂 security-service/                      🟢 Auth Service (Port: 8081)
│   ├── 📂 src/main/java/com/security/
│   │   ├── 📄 SecurityServiceApplication.java
│   │   ├── 📂 controller/
│   │   │   └── 📄 AuthController.java
│   │   ├── 📂 service/
│   │   │   ├── 📄 JwtService.java
│   │   │   └── 📄 CustomUserDetailsService.java
│   │   ├── 📂 repository/
│   │   │   └── 📄 UserRepository.java
│   │   ├── 📂 model/
│   │   │   └── 📄 User.java
│   │   ├── 📂 dto/
│   │   │   ├── 📄 LoginRequest.java
│   │   │   ├── 📄 RegisterRequest.java
│   │   │   └── 📄 AuthResponse.java
│   │   ├── 📂 security/
│   │   │   ├── 📄 JwtAuthFilter.java
│   │   │   └── 📄 OAuth2SuccessHandler.java
│   │   ├── 📂 config/
│   │   │   ├── 📄 SecurityConfig.java
│   │   │   └── 📄 SwaggerConfig.java
│   │   └── 📂 exception/
│   │       ├── 📄 GlobalExceptionHandler.java
│   │       ├── 📄 DuplicateEmailException.java
│   │       ├── 📄 UserNotFoundException.java
│   │       └── 📄 ErrorResponse.java
│   ├── 📂 src/main/resources/
│   │   └── 📄 application.yml
│   └── 📄 pom.xml
│
├── 📂 quantity-measurement-app/              🟡 Core Business Service (Port: 8082)
│   ├── 📂 src/main/java/com/quantity/
│   │   ├── 📄 QuantityMeasurementApplication.java
│   │   ├── 📂 controller/
│   │   │   └── 📄 QuantityMeasurementController.java
│   │   ├── 📂 service/
│   │   │   ├── 📄 IQuantityMeasurementService.java
│   │   │   └── 📄 QuantityMeasurementServiceImpl.java
│   │   ├── 📂 repository/
│   │   │   └── 📄 QuantityMeasurementRepository.java
│   │   ├── 📂 model/
│   │   │   └── 📄 QuantityMeasurementEntity.java
│   │   ├── 📂 dto/
│   │   │   ├── 📄 QuantityDTO.java
│   │   │   ├── 📄 QuantityInputDTO.java
│   │   │   ├── 📄 QuantityMeasurementDTO.java
│   │   │   └── 📄 OperationType.java
│   │   ├── 📂 core/
│   │   │   ├── 📄 IMeasurable.java
│   │   │   ├── 📄 SupportsArithmetic.java
│   │   │   ├── 📄 Quantity.java           ◄── Generic Quantity<U> Engine
│   │   │   ├── 📄 LengthUnit.java
│   │   │   ├── 📄 WeightUnit.java
│   │   │   ├── 📄 VolumeUnit.java
│   │   │   └── 📄 TemperatureUnit.java
│   │   ├── 📂 config/
│   │   │   ├── 📄 SecurityConfig.java
│   │   │   └── 📄 SwaggerConfig.java
│   │   └── 📂 exception/
│   │       ├── 📄 GlobalExceptionHandler.java
│   │       ├── 📄 QuantityMeasurementException.java
│   │       ├── 📄 DatabaseException.java
│   │       └── 📄 ErrorResponse.java
│   ├── 📂 src/main/resources/
│   │   └── 📄 application.yml
│   └── 📄 pom.xml
│
└── 📂 api-gateway/                           🔴 API Gateway (Port: 8080)
    ├── 📂 src/main/java/com/gateway/
    │   ├── 📄 ApiGatewayApplication.java
    │   ├── 📂 filter/
    │   │   └── 📄 JwtAuthenticationFilter.java
    │   └── 📂 config/
    │       └── 📄 GatewayConfig.java
    ├── 📂 src/main/resources/
    │   └── 📄 application.yml
    └── 📄 pom.xml
```

---

## 📋 Services Description

### 🔵 1. Eureka Server — `Port: 8761`

> The **Service Registry** — acts as a phonebook for all microservices.

Every service registers itself here on startup. The API Gateway queries Eureka to discover the location of other services **dynamically** — no hardcoded URLs.

| Feature | Details |
|---------|---------|
| 🔍 Service Discovery | Registers all services on startup |
| 💓 Heartbeat Monitoring | Health checks via periodic heartbeats |
| 📊 Dashboard | Visual UI to view all registered services |
| 🛡️ Self-Preservation | Disabled in dev mode for clean restarts |

---

### 🟢 2. Security Service — `Port: 8081`

> Handles all **authentication and user management** operations.

Responsible for user registration, login, JWT token generation, and GitHub OAuth2 login.

| Feature | Details |
|---------|---------|
| 📝 Registration | User registration with field validation |
| 🔑 JWT Generation | Stateless tokens valid for 24 hours |
| 🐙 GitHub OAuth2 | Social login via GitHub |
| 🔐 BCrypt Encryption | Password hashing with BCrypt |
| 🚫 Session Management | Stateless — no server-side sessions |

---

### 🟡 3. Quantity Measurement App — `Port: 8082`

> The **core business logic** service — handles all quantity operations.

Powered by a **Generic `Quantity<U>` engine** that performs automatic base unit conversions before any arithmetic operation.

| Feature | Details |
|---------|---------|
| ➕➖✖️➗ Operations | Add, Subtract, Multiply, Divide |
| ⚖️ Compare | Equality comparison with auto-conversion |
| 🔄 Convert | Convert between units in same category |
| 📏 Length Units | FEET, INCHES, YARDS, CENTIMETERS |
| ⚖️ Weight Units | MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE |
| 🧪 Volume Units | LITRE, MILLILITRE, GALLON |
| 🌡️ Temperature Units | CELSIUS, FAHRENHEIT, KELVIN |
| 🗃️ History Tracking | All operations saved to MySQL |
| ⚠️ Error Tracking | Failed operations also recorded |

---

### 🔴 4. API Gateway — `Port: 8080`

> The **single entry point** for all client requests.

Routes requests to appropriate services, validates JWT tokens, and handles CORS — all built on **Spring WebFlux (reactive/non-blocking)**.

| Feature | Details |
|---------|---------|
| 🔐 JWT Validation | Validates tokens BEFORE forwarding requests |
| 🔀 Dynamic Routing | Routes via Eureka `lb://` service names |
| 🌐 CORS | Global CORS configuration |
| ⚡ Reactive | Built on WebFlux — non-blocking I/O |
| 🔁 Load Balancing | Automatic via `lb://` prefix |

---

## ✅ Prerequisites

> Make sure the following tools are installed before running the project.

| Tool | Version | Purpose | Download |
|------|---------|---------|----------|
| ☕ **Java JDK** | 17+ | Runtime Environment | [adoptium.net](https://adoptium.net/) |
| 🔨 **Maven** | 3.8+ | Build Tool | [maven.apache.org](https://maven.apache.org/) |
| 🗄️ **MySQL** | 8.0+ | Database Server | [dev.mysql.com](https://dev.mysql.com/downloads/) |
| 💡 **IntelliJ IDEA** | Latest | IDE | [jetbrains.com](https://www.jetbrains.com/idea/) |
| 📮 **Postman** | Latest | API Testing | [postman.com](https://www.postman.com/) |

---

## 🗄️ Database Setup

> Run these SQL commands in **MySQL Workbench** or your terminal before starting services.

```sql
-- ============================================================
-- Step 1: Create both databases
-- ============================================================
CREATE DATABASE security_db;
CREATE DATABASE quantity_db;

-- ============================================================
-- Step 2: Verify creation
-- ============================================================
SHOW DATABASES;

-- Expected output:
-- +--------------------+
-- | Database           |
-- +--------------------+
-- | quantity_db        |
-- | security_db        |
-- +--------------------+
```

> 💡 **Note:** Tables are **auto-created by Hibernate** on first startup due to `ddl-auto: update`. You only need to create the databases manually.

---

## 🚀 How to Run

### Step 1 — Clone / Open Projects

Open all **4 projects** in IntelliJ IDEA or Eclipse as **separate windows**:

```
File → Open → select each project folder → Open in New Window
```

| Project Folder | Opens On |
|---------------|----------|
| `eureka-server/` | Port 8761 |
| `security-service/` | Port 8081 |
| `quantity-measurement-app/` | Port 8082 |
| `api-gateway/` | Port 8080 |

---

### Step 2 — Maven Reload

For **each project**, reload Maven dependencies:

```
Right click pom.xml → Maven → Reload Project
```

---

### Step 3 — Update MySQL Credentials

In `security-service/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/security_db
    username: root
    password: YOUR_MYSQL_PASSWORD   # ← 🔴 Update this
```

In `quantity-measurement-app/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/quantity_db
    username: root
    password: YOUR_MYSQL_PASSWORD   # ← 🔴 Update this
```

---

### Step 4 — Start Services in Order

> ⚠️ **CRITICAL:** Services MUST be started in this exact order!

```
┌─────────────────────────────────────────────────────────┐
│                   STARTUP ORDER                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  1️⃣  eureka-server            → wait for port 8761     │
│          ↓  (wait ~10 seconds)                          │
│  2️⃣  security-service         → wait for port 8081     │
│          ↓  (wait ~10 seconds)                          │
│  3️⃣  quantity-measurement-app → wait for port 8082     │
│          ↓  (wait ~10 seconds)                          │
│  4️⃣  api-gateway              → wait for port 8080     │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

### Step 5 — Verify Everything is Running

Open your browser and navigate to:

```
http://localhost:8761
```

You should see **3 services registered** in the Eureka Dashboard:

```
┌──────────────────────────────────────┬──────────────────┐
│ Application                          │ Status           │
├──────────────────────────────────────┼──────────────────┤
│ SECURITY-SERVICE                     │ ✅ UP (1)        │
│ QUANTITY-MEASUREMENT-APP             │ ✅ UP (1)        │
│ API-GATEWAY                          │ ✅ UP (1)        │
└──────────────────────────────────────┴──────────────────┘
```

---

## 📡 API Endpoints

> All requests go through the **API Gateway on port 8080**

### 🔓 Auth Endpoints — Public (No Token Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Login and receive JWT token |
| `GET` | `/auth/oauth-success` | GitHub OAuth2 callback |

---

### 🔐 Quantity Endpoints — Secured (JWT Token Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/quantities/add` | ➕ Add two quantities |
| `POST` | `/api/v1/quantities/subtract` | ➖ Subtract two quantities |
| `POST` | `/api/v1/quantities/multiply` | ✖️ Multiply two quantities |
| `POST` | `/api/v1/quantities/divide` | ➗ Divide two quantities |
| `POST` | `/api/v1/quantities/compare` | ⚖️ Compare two quantities |
| `POST` | `/api/v1/quantities/convert` | 🔄 Convert to another unit |
| `GET` | `/api/v1/quantities/history/operation/{op}` | 📜 History by operation type |
| `GET` | `/api/v1/quantities/history/type/{type}` | 📜 History by measurement type |
| `GET` | `/api/v1/quantities/count/{operation}` | 🔢 Count of successful operations |
| `GET` | `/api/v1/quantities/history/errored` | ⚠️ All errored operations |

---

## 🔒 Security Flow

```
 CLIENT                  GATEWAY              SECURITY-SERVICE       QUANTITY-SERVICE
    │                       │                        │                      │
    │  POST /auth/login      │                        │                      │
    │──────────────────────►│                        │                      │
    │                       │   Forward to :8081     │                      │
    │                       │───────────────────────►│                      │
    │                       │                        │  Validate credentials │
    │                       │                        │  Generate JWT token   │
    │                       │◄───────────────────────│                      │
    │◄──────────────────────│  { token: "eyJ..." }   │                      │
    │                       │                        │                      │
    │  POST /api/v1/... + Bearer Token               │                      │
    │──────────────────────►│                        │                      │
    │                       │  🔐 Validate JWT       │                      │
    │                       │  ✅ Valid →             │                      │
    │                       │  Add X-Auth-User header│                      │
    │                       │──────────────────────────────────────────────►│
    │                       │                        │  Process operation    │
    │                       │◄──────────────────────────────────────────────│
    │◄──────────────────────│  { result... }         │                      │
    │                       │                        │                      │
    │  POST /api/v1/... + Invalid Token              │                      │
    │──────────────────────►│                        │                      │
    │                       │  ❌ Invalid JWT         │                      │
    │◄──────────────────────│  401 Unauthorized      │                      │
```

> 🔑 **Key Rule:** The API Gateway validates every JWT. Downstream services **trust the gateway** and don't re-validate tokens.

---

## 🔗 Service Communication

### Load-Balanced Routing via Eureka

```yaml
# API Gateway routes using lb:// (load balanced)
spring:
  cloud:
    gateway:
      routes:
        - id: security-service
          uri: lb://security-service        # Eureka resolves → localhost:8081
          predicates:
            - Path=/auth/**
            
        - id: quantity-service
          uri: lb://quantity-measurement-app # Eureka resolves → localhost:8082
          predicates:
            - Path=/api/v1/quantities/**
```

### OpenFeign Declarative REST Client

```java
// Declarative REST client — no boilerplate HTTP code needed!
@FeignClient(name = "quantity-measurement-app")
public interface QuantityClient {

    @PostMapping("/api/v1/quantities/add")
    QuantityMeasurementDTO add(@RequestBody QuantityInputDTO input);
    
    @PostMapping("/api/v1/quantities/convert")
    QuantityMeasurementDTO convert(@RequestBody QuantityInputDTO input);
}
```

> 💡 `lb://` means **load balanced** — Eureka dynamically resolves the service address. Works even if the service's IP or port changes!

---

## 📊 Eureka Dashboard

After starting all services, visit:

```
🌐  http://localhost:8761
```

You will see the Eureka dashboard with all registered services:

```
┌─────────────────────────────────────────────────────────────────────────┐
│  🔵  Eureka — Instances currently registered with Eureka                │
├──────────────────────────────┬───────┬──────────────────┬───────────────┤
│  Application                 │  AMIs │  Availability    │  Status       │
├──────────────────────────────┼───────┼──────────────────┼───────────────┤
│  API-GATEWAY                 │  n/a  │  (1)             │  ✅ UP (1)    │
│  SECURITY-SERVICE            │  n/a  │  (1)             │  ✅ UP (1)    │
│  QUANTITY-MEASUREMENT-APP    │  n/a  │  (1)             │  ✅ UP (1)    │
└──────────────────────────────┴───────┴──────────────────┴───────────────┘
```

---

## 📚 Swagger UI

> Swagger is available on individual service ports (not the gateway)

| Service | Swagger URL | Status |
|---------|------------|--------|
| 🟢 Security Service | [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) | ✅ Available |
| 🟡 Quantity Measurement App | [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html) | ✅ Available |
| 🔴 API Gateway | ❌ Not Available | Gateway only routes — no business APIs |

---

## ⚙️ Environment Configuration

### Port Summary

```
┌────────────────────────────────┬──────────┐
│  Service                       │  Port    │
├────────────────────────────────┼──────────┤
│  🌐 Eureka Server              │  8761    │
│  🔴 API Gateway                │  8080    │
│  🟢 Security Service           │  8081    │
│  🟡 Quantity Measurement App   │  8082    │
│  🗄️  MySQL                     │  3306    │
│  ⚛️  Frontend (React)          │  3000    │
└────────────────────────────────┴──────────┘
```

---

### JWT Configuration

```yaml
jwt:
  secret: **YOUR_JWT_SECRET**
  expiration: 86400000   # 24 hours in milliseconds (86400 seconds)
```

> ⚠️ **IMPORTANT:** The **exact same** JWT secret must be configured in **both** `security-service` AND `api-gateway`. If they differ, the gateway will reject all tokens!

---

### GitHub OAuth2 Configuration

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: YOUR_GITHUB_CLIENT_ID         # ← from GitHub OAuth App
            client-secret: YOUR_GITHUB_CLIENT_SECRET  # ← from GitHub OAuth App
            scope: user:email
```

> 📖 **How to get GitHub credentials:**
> 1. Go to GitHub → Settings → Developer Settings → OAuth Apps
> 2. Click "New OAuth App"
> 3. Set callback URL to `http://localhost:8081/login/oauth2/code/github`
> 4. Copy Client ID and Client Secret

---

## ❌ Common Errors & Fixes

| ❌ Error | 🔍 Cause | ✅ Fix |
|---------|---------|-------|
| `Connection refused on 8761` | Eureka Server not running | Start `eureka-server` first and wait for full startup |
| `401 Unauthorized` | Missing or invalid JWT token | Add `Authorization: Bearer <token>` header |
| `No instances available` | Service not yet registered | Wait ~30 seconds after service startup for Eureka registration |
| `Access denied for user 'root'` | Wrong MySQL password | Update `password` in `application.yml` |
| `Unknown database 'security_db'` | Database not created | Run `CREATE DATABASE security_db;` and `CREATE DATABASE quantity_db;` |
| `Port 8080 already in use` | Another process using the port | Kill it: `taskkill /PID <pid> /F` (Windows) or `kill -9 <pid>` (Mac/Linux) |
| `Could not resolve placeholder` | Wrong YAML indentation | Check spacing in `application.yml` — use spaces, not tabs |
| `Failed to load ApplicationContext` | Dependency issue or missing config | Check `pom.xml` and run Maven reload |
| `JWT signature mismatch` | Different secrets in services | Ensure **same** JWT secret in security-service AND api-gateway |

---

## ✔️ Microservice Rules Followed

| # | Rule | Description | Status |
|---|------|-------------|--------|
| 1 | **Single Responsibility** | Each service does exactly one job | ✅ |
| 2 | **Own Database** | Each service has its own isolated database | ✅ |
| 3 | **Service Registry** | All services register with Eureka on startup | ✅ |
| 4 | **API Gateway** | Single entry point for all incoming traffic | ✅ |
| 5 | **Load Balanced URLs** | `lb://` prefix instead of hardcoded host:port | ✅ |
| 6 | **Same JWT Secret** | Consistent secret shared across security + gateway | ✅ |
| 7 | **Gateway Handles JWT** | Downstream services trust gateway — no re-validation | ✅ |
| 8 | **Unique Ports** | Each service runs on its own port | ✅ |
| 9 | **Correct Startup Order** | Eureka → Services → Gateway | ✅ |
| 10 | **Unique Package Names** | `com.security`, `com.quantity`, `com.gateway`, `com.eureka` | ✅ |
| 11 | **DTOs for Communication** | No JPA entities exposed via REST | ✅ |
| 12 | **Actuator Health Checks** | All services expose `/actuator/health` | ✅ |
| 13 | **YAML Configuration** | `application.yml` used in all services | ✅ |

---
