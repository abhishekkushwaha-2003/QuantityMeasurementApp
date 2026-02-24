# Quantity Measurement Application  
## Test-Driven Development (TDD) | OOP | Clean Code | DRY Principle  

---

## 📅 23 Feb 2026  
### 🔹 UC13 – Centralized Arithmetic Logic (DRY Enforcement)  
**Branch:** `feature/UC13-CentralizedArithmeticLogic`

### Objective
- Eliminate code duplication in add, subtract, and divide methods  
- Enforce DRY principle through centralized arithmetic handling  
- Preserve public API and behavior from UC12  
- Improve maintainability and scalability  

### Implementation
- Introduced private `ArithmeticOperation` enum (ADD, SUBTRACT, DIVIDE)  
- Implemented centralized `performBaseArithmetic()` helper method  
- Created `validateArithmeticOperands()` for unified validation  
- Moved base-unit conversion logic into helper  
- Refactored `add()`, `subtract()`, and `divide()` to delegate to helper  
- Preserved implicit and explicit target unit handling  
- Ensured consistent error handling across all operations  
- Maintained immutability and backward compatibility  

### 🏗 Result
- Validation logic defined once (Single Source of Truth)  
- Conversion logic centralized  
- No duplication across arithmetic methods  
- Public API unchanged  
- All UC12 test cases pass without modification  
- System now fully DRY-compliant for arithmetic operations  

Future operations (e.g., Multiply, Modulo) can be added without duplicating validation or conversion logic.

- [feature/UC13-CentralizedArithmeticLogic](https://github.com/abhishekkushwaha-2003/QuantityMeasurementApp/tree/feature/UC13-CentralizedArithmeticLogic)

---
