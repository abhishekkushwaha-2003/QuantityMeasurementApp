# Quantity Measurement Application  
## Test-Driven Development (TDD) | OOP | Clean Code | DRY Principle  

---

## 📅 23 Feb 2026  
### 🔹 UC12 – Subtraction & Division Operations on Quantity  
**Branch:** `feature/UC12-SubtractionAndDivision`

### Objective
- Add subtraction support between quantities  
- Add division support returning dimensionless result  
- Maintain immutability and type safety  
- Support cross-unit arithmetic within same category  

### Implementation
- Added `subtract()` method (implicit & explicit target unit)  
- Added `divide()` method returning `double`  
- Converted operands to base unit before arithmetic  
- Implemented validation (null, cross-category, division by zero)  
- Rounded subtraction results to two decimal places  
- Ensured non-commutative behavior (A − B ≠ B − A, A ÷ B ≠ B ÷ A)  
- Added comprehensive JUnit test coverage  

### Result
System now supports:
- Equality  
- Conversion  
- Addition  
- Subtraction  
- Division  

Across:
- Length  
- Weight  
- Volume  

- [feature/UC12-SubtractionAndDivision](https://github.com/abhishekkushwaha-2003/QuantityMeasurementApp/tree/feature/UC12-SubtractionAndDivision)

---
