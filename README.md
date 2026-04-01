## 📅 14 March 2026

## 🔹 UC16 – JDBC Database Integration  
**Branch:** `feature/UC16-JDBCPersistence`

### Objective
Enable persistent storage of measurement data using a **relational SQL database** while maintaining clean architecture and high performance.

Key goals:
- Enable **persistent storage of measurement data**
- Implement **industry-standard connection pooling** for optimized performance
- Secure database operations against vulnerabilities such as **SQL Injection**
- Seamlessly replace **local cache storage with database storage** using **Dependency Injection**
- Ensure **complete mocked test coverage** for the persistence layer

---

### Implementation

#### 1. JDBC Persistence Layer
- Integrated a **JDBC-based persistence layer**
- Configured an **embedded H2 SQL Database** for seamless data storage and retrieval

#### 2. Connection Pooling
Implemented `ConnectionPool` utility:
- Utilized **HikariCP** for **high-performance database connection management**
- Ensured efficient and reliable connection reuse

#### 3. Database Repository
Created `QuantityMeasurementDatabaseRepository`:
- Used **Parameterized Prepared Statements** to prevent **SQL Injection**
- Implemented SQL logic for **saving and retrieving historical measurement data**

#### 4. Dependency Injection
Refactored `QuantityMeasurementApp`:
- Enabled **dynamic injection of the database repository**
- Ensured **business logic remained unchanged**

#### 5. Testing
Achieved **100% test coverage**:
- Used **JUnit** and **Mockito**
- Mocked repository interactions
- Validated **Controller and Service layer behavior independently**

---

### 🔗 Source Code

[feature/UC16-JDBCPersistence](https://github.com/abhishekkushwaha-2003/QuantityMeasurementApp/tree/feature/UC16-JDBCPersistence)
