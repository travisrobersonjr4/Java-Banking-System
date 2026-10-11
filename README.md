# Java Banking System

A simple banking program made in Java. It runs in the terminal, so there's no website or app interface.

You can create an account, log in with an account number and PIN, check your balance, deposit or withdraw money, and send money to another account. There's also a transaction history.

## How to run it

You'll need Java installed. Open the project in VS Code, or run these commands from the project folder:

```bash
javac -d out src/BankAccount.java src/BankingSystem.java
java -cp out BankingSystem
```

## Try it out

You can make a new account from the main menu. The program will show you your account number and log you in.

There are also two test accounts:

- Account `1001`, PIN `1234`
- Account `1002`, PIN `5678`

## A few things to know

The program uses Java classes and a map to keep track of accounts. It checks for invalid amounts and won't let you withdraw more than you have.

Right now, accounts are only saved while the program is running. Once you close it, any accounts you created are gone. This is just a practice project, not a real banking application. Don't use a real PIN.

I'd like to add permanent account storage and a better interface later.
