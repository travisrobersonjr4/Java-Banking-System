# Java Banking System

A console-based banking simulation built with **Java 17**, demonstrating object-oriented programming, collections, validation, and basic transaction operations.

## Features
- Create an account with an automatically generated account number and four-digit demo PIN
- Automatically sign in after creating an account, or log in later
- Check balances, deposit and withdraw funds
- Transfer money between accounts
- View transaction history
- Reject invalid amounts and insufficient-funds withdrawals

## Run the project
From the repository root, with Java 17 installed:

```bash
javac -d out src/BankAccount.java src/BankingSystem.java
java -cp out BankingSystem
```

You can also open the repository folder in VS Code with the Java Extension Pack and run `BankingSystem.main()`.

## Example accounts

| Account | Demo PIN | Initial balance |
| --- | --- | --- |
| 1001 (Alex) | 1234 | $500.00 |
| 1002 (Jordan) | 5678 | $250.00 |

For a new account, select **Create account**; the generated account number will be displayed and you will be signed in automatically.

## Implementation

The project uses two classes: `BankingSystem` handles the menu and a `LinkedHashMap` of accounts; `BankAccount` implements balances and transactions. Amounts are represented internally as `long` cents to avoid floating-point rounding errors.

**Educational demonstration only:** data is stored in memory and disappears when the application exits. PINs are stored as plain text for demonstration purposes, so never enter a real PIN. It is not suitable for handling actual banking data.

## Ideas for future improvements

Persist data to a database, securely hash PINs, write automated JUnit tests, and expose functionality through a Spring Boot API.
