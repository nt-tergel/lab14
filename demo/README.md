# Lab14 Calculator Project

A Maven-based Java calculator project demonstrating Git workflow with GitHub Actions CI/CD.

## Project Structure

- `src/main/java/labxx/sict/must/edu/mn/`: Main source code
- `src/test/java/labxx/sict/must/edu/mn/`: JUnit test files
- `checkstyle.xml`: Code style configuration
- `.github/workflows/ci.yml`: GitHub Actions CI workflow

## Building and Testing

### Build the project

```bash
cd demo
mvn clean compile
```

### Run tests

```bash
cd demo
mvn test
```

### Check code style

```bash
cd demo
mvn checkstyle:check
```

### Generate coverage report

```bash
cd demo
mvn jacoco:report
```

### Run all checks

```bash
cd demo
mvn clean test checkstyle:check jacoco:check
```

## Coverage Report

After running tests, view the JaCoCo coverage report:

```
target/site/jacoco/index.html
```

## Requirements

- Java 17
- Maven 3.8.9 or later

## CI/CD

This project uses GitHub Actions for continuous integration:

- Checkstyle code style validation
- JUnit 5 tests
- JaCoCo 100% branch coverage requirement

All checks must pass before merging to main branch.
