import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/** Console-based demo banking application. Data resets when the program exits. */
public class BankingSystem {
    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private int nextAccountNumber = 1003;

    public static void main(String[] args) {
        new BankingSystem().run();
    }

    public BankingSystem() {
        accounts.put("1001", new BankAccount("1001", "Alex", "1234", 50_000));
        accounts.put("1002", new BankAccount("1002", "Jordan", "5678", 25_000));
    }

    private void run() {
        System.out.println("=== JAVA BANKING SYSTEM ===");
        System.out.println("Demo login: account 1001 / PIN 1234, or account 1002 / PIN 5678");
        while (true) {
            System.out.println("\n1. Log in  2. Create account  3. Exit");
            String choice = ask("Choose: ");
            if (choice.equals("1")) login();
            else if (choice.equals("2")) createAccount();
            else if (choice.equals("3")) { System.out.println("Goodbye!"); return; }
            else System.out.println("Invalid menu option.");
        }
    }

    private void createAccount() {
        String owner = ask("Your name: ").trim();
        if (owner.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        String pin = ask("Choose a 4-digit PIN: ");
        if (!pin.matches("\\d{4}")) { System.out.println("PIN must be exactly 4 digits."); return; }
        String number = String.valueOf(nextAccountNumber++);
        BankAccount newAccount = new BankAccount(number, owner, pin, 0);
        accounts.put(number, newAccount);
        System.out.println("\n========================================");
        System.out.println("        ACCOUNT CREATED SUCCESSFULLY");
        System.out.println("========================================");
        System.out.println("Your account number: " + number);
        System.out.println("Save this number to log in later.");
        System.out.println("You are now logged in automatically.");
        System.out.println("========================================");
        accountMenu(newAccount);
    }

    private void login() {
        BankAccount account = accounts.get(ask("Account number: ").trim());
        String pin = ask("PIN: ");
        if (account == null || !account.checkPin(pin)) {
            System.out.println("Invalid account number or PIN."); return;
        }
        System.out.println("Welcome, " + account.getOwner() + "!");
        accountMenu(account);
    }

    private void accountMenu(BankAccount account) {
        while (true) {
            System.out.println("\n1. Balance  2. Deposit  3. Withdraw  4. Transfer  5. History  6. Log out");
            String choice = ask("Choose: ");
            try {
                switch (choice) {
                    case "1": System.out.println("Balance: " + BankAccount.formatMoney(account.getBalanceCents())); break;
                    case "2": {
                        long amount = BankAccount.parseMoney(ask("Deposit amount: $"));
                        account.deposit(amount);
                        System.out.println("Deposit successful."); break;
                    }
                    case "3": {
                        long amount = BankAccount.parseMoney(ask("Withdrawal amount: $"));
                        account.withdraw(amount);
                        System.out.println("Withdrawal successful."); break;
                    }
                    case "4": {
                        BankAccount recipient = accounts.get(ask("Recipient account number: ").trim());
                        long amount = BankAccount.parseMoney(ask("Transfer amount: $"));
                        account.transferTo(recipient, amount);
                        System.out.println("Transfer successful."); break;
                    }
                    case "5": {
                        System.out.println("Transaction history:");
                        for (String entry : account.getTransactions()) System.out.println("- " + entry);
                        break;
                    }
                    case "6": System.out.println("Logged out."); return;
                    default: System.out.println("Invalid menu option.");
                }
            } catch (IllegalArgumentException | ArithmeticException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) { System.out.println("\nInput closed. Goodbye!"); System.exit(0); }
        return scanner.nextLine();
    }
}
