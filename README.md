# Quantity Measurement Application  
## Test-Driven Development (TDD) | OOP | SOLID | Interface Segregation Principle  

---

## 📅 24 Feb 2026  
### 🔹 UC14 – Temperature Measurement with Selective Arithmetic Support & IMeasurable Refactoring  
**Branch:** `feature/UC14-TemperatureMeasurementSelectiveArithmetic`

### Objective
- Add Temperature measurement support (Celsius, Fahrenheit, Kelvin)  
- Support equality and conversion for temperature  
- Restrict unsupported arithmetic operations (add, subtract, divide)  
- Refactor `IMeasurable` to allow optional arithmetic operations  
- Maintain backward compatibility with UC1–UC13  

### Implementation
- Introduced `TemperatureUnit` enum (CELSIUS, FAHRENHEIT, KELVIN)  
- Added `SupportsArithmetic` functional interface  
- Added default methods in `IMeasurable` for operation validation  
- Used lambda expressions for non-linear temperature conversion formulas  
- Disabled arithmetic operations for temperature via override  
- Updated `Quantity` to validate operation support before execution  
- Preserved cross-category type safety using generics  

### Result
- Temperature supports only equality and conversion  
- Unsupported operations throw `UnsupportedOperationException`  
- Interface Segregation Principle properly applied  
- No changes required for Length, Weight, or Volume units  
- All UC1–UC13 test cases pass without modification  
- System now supports category-specific operational constraints  

Future measurement categories with different rules can be added without breaking the architecture.

- [feature/UC14-TemperatureMeasurement](https://github.com/abhishekkushwaha-2003/QuantityMeasurementApp/tree/feature/UC14-TemperatureMeasurement)

---
