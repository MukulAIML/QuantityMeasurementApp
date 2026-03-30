# QuantityMeasurementApp

📅 **Start Date:** 19 Jan, 2026  

---

## 📌 Project Overview
QuantityMeasurementApp is a progressive implementation of measurement operations including length, weight, volume, and temperature.  
The project evolves step-by-step through multiple use cases, gradually introducing concepts like unit conversion, arithmetic operations, and enterprise-level architecture.

---

## 🚀 Daily Progress / Use Cases

### 🔹 UC-1 → `feature/UC1-FeetEquality`
- Implemented logic to compare measurements in feet

### 🔹 UC-2 → `feature/UC2-InchEquality`
- Added comparison support for values in inches

### 🔹 UC-3 → `feature/UC3-GenericLength`
- Created a generic quantity class to avoid code duplication

### 🔹 UC-4 → `feature/UC4-YardEquality`
- Added equality check support for yard units

### 🔹 UC-5 → `feature/UC5-UnitConversion`
- Enabled conversion between different measurement units

### 🔹 UC-6 → `feature/UC6-UnitAddition`
- Implemented addition of two values with different units

### 🔹 UC-7 → `feature/UC7-TargetUnitAddition`
- Allowed addition with output in a specific target unit

### 🔹 UC-8 → `feature/UC8-StandaloneUnit`
- Refactored unit handling into a separate structure

### 🔹 UC-9 → `feature/UC9-WeightMeasurement`
- Added support for weight measurements

### 🔹 UC-10 → `feature/UC10-GenericQuantity`
- Enhanced generic class to support multiple measurement types

### 🔹 UC-11 → `feature/UC11-VolumeMeasurement`
- Implemented volume comparison, conversion, and addition

### 🔹 UC-12 → `feature/UC12-SubtractionDivision`
- Added subtraction and division operations

### 🔹 UC-13 → `feature/UC13-CentralizedArithmeticLogic`
- Centralized arithmetic logic for better reusability

### 🔹 UC-14 → `feature/UC14-TemperatureMeasurement`
- Added temperature measurement with limited operations

### 🔹 UC-15 → `feature/UC15-NTierArchitecture`
- Refactored project into N-Tier architecture

### 🔹 UC-16 → `feature/UC16-DatabaseIntegration`
- Integrated database using JDBC for data persistence

### 🔹 UC-17 → `feature/UC17-SpringIntegration`
- Converted project to Spring Boot

### 🔹 UC-18 → `feature/UC18-Authentication`
- Added JWT authentication with Spring Security

---

## 🛠️ Tech Stack
- Java
- Spring Boot
- JDBC
- MySQL (or any relational DB)
- JWT Authentication
- Maven

---

## 📂 Branching Strategy
- `main` → Stable production-ready code  
- `dev` → Development branch  
- `feature/*` → Individual use case implementations  

---

## ✨ Features
- Unit comparison (Length, Weight, Volume, Temperature)
- Unit conversion across measurement systems
- Arithmetic operations (Add, Subtract, Divide)
- Generic and reusable architecture
- N-Tier architecture implementation
- Database integration
- Secure authentication using JWT

---

## 📈 Project Evolution
This project demonstrates a step-by-step transformation from:
- Basic unit comparison  
➡️ Generic reusable logic  
➡️ Multi-measurement support  
➡️ Enterprise architecture (N-Tier)  
➡️ Full-stack backend with Spring Boot & Security  

---

## 👨‍💻 Author
**Mukul Verma**

---

## 📌 Notes
- Each use case is implemented in a separate feature branch.
- Follow commit history for detailed understanding of progression.
