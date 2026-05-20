# Intelligent Bus Driver Guidance System

A Java Maven project for the Intelligent Bus Driver Guidance System.
Developed as part of Assignment 4 (ISYS3413/ISYS3475/ISYS1118).

## Project Structure

```
intelligent-bus-driver-system/
├── .github/workflows/ci.yml
├── src/
│   ├── main/java/com/busdriver/
│   │   ├── Bus.java
│   │   ├── Driver.java
│   │   ├── exception/ValidationException.java
│   │   ├── repository/BusRepository.java
│   │   ├── repository/DriverRepository.java
│   │   ├── validator/BusValidator.java
│   │   └── validator/DriverValidator.java
│   └── test/java/com/busdriver/
│       ├── integration/BusIntegrationTest.java
│       ├── integration/DriverIntegrationTest.java
│       ├── unit/BusUnitTest.java
│       └── unit/DriverUnitTest.java
├── pom.xml
└── .gitignore
```

## Requirements
- Java 17+
- Maven 3.8+

## Build & Test
```bash
mvn compile          # Build
mvn test             # Run all tests
mvn test -Dtest="com.busdriver.unit.*"          # Unit tests only
mvn test -Dtest="com.busdriver.integration.*"   # Integration tests only
```

## Validation Conditions

### Driver Conditions (D1-D5)
- **D1**: Driver ID - exactly 10 chars, first 2 digits 2-9, 2+ special chars in pos 3-8, last 2 uppercase
- **D2**: Address - Street Number|Street Name|City|State|Country
- **D3**: Birthdate - DD-MM-YYYY
- **D4**: >10 years experience = cannot change license type
- **D5**: Driver ID and name are immutable

### Bus Conditions (B1-B5)
- **B1**: Bus ID - exactly 8 digits
- **B2**: Capacity cannot increase during update
- **B3**: Drivers >50 years cannot drive buses with capacity >= 50
- **B4**: Only >=5 years experience for electric buses
- **B5**: Only Heavy/PublicTransport license for electric/hybrid buses
