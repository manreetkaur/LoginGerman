# LoginGerman Project

## Overview

The LoginGerman project is a test automation suite for validating login functionality. It uses Selenium for browser automation and TestNG for test management. Allure is used for generating and serving test reports.

## Project Structure

- `src/main/java/` - Contains the main application code (if any).
- `src/test/java/` - Contains the test classes and test cases.
- `src/test/resources/` - Contains TestNG XML configuration files.
- `target/` - Contains compiled classes and build artifacts.

## Dependencies

- **Selenium**: For browser automation.
- **TestNG**: For test management and execution.
- **Allure TestNG Adapter**: For generating and serving test reports.

## Setup and Installation

### Prerequisites

- **Java 22** 
- **Maven 3.6** 
- **Selenium WebDriver** (appropriate driver binaries should be installed and configured)

### Build the Project
```sh
mvn clean install
```
### Run Tests with Maven:

- Execute the tests defined in src/test/java and specified in src/test/resources/testng.xml:
```sh
mvn clean test
```

### Generate and Serve the Allure Report:
```sh
 mvn allure:serve
```
### Clone the Repository

```sh
git clone https://github.com/yourusername/LoginGerman.git
cd LoginGerman


