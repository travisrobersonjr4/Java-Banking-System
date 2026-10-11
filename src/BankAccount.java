import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** An educational account using cents internally to avoid floating point money errors. */
public class BankAccount {
    private final String accountNumber;
    private final String owner;
    private final String pin;
    private long balanceCents;
    private final List<String> transactions = new ArrayList<>();

    public BankAccount(String accountNumber, String owner, String pin, long openingBalanceCents) {
        if (accountNumber == null || accountNumber.isBlank() || owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Account number and owner are required.");
        }
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must contain exactly 4 digits.");
        }
        if (openingBalanceCents < 0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.pin = pin;
        this.balanceCents = openingBalanceCents;
        transactions.add("Account opened with " + formatMoney(openingBalanceCents));
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwner() { return owner; }
    public long getBalanceCents() { return balanceCents; }
    public boolean checkPin(String attemptedPin) { return pin.equals(attemptedPin); }
    public List<String> getTransactions() { return Collections.unmodifiableList(transactions); }

    public void deposit(long cents) {
        requirePositive(cents);
        balanceCents = Math.addExact(balanceCents, cents);
        transactions.add("Deposited " + formatMoney(cents));
    }

    public void withdraw(long cents) {
        requirePositive(cents);
        if (cents > balanceCents) throw new IllegalArgumentException("Insufficient funds.");
        balanceCents -= cents;
        transactions.add("Withdrew " + formatMoney(cents));
    }

    public void transferTo(BankAccount receiver, long cents) {
        if (receiver == null || receiver == this) throw new IllegalArgumentException("Choose another valid account.");
        requirePositive(cents);
        if (cents > balanceCents) throw new IllegalArgumentException("Insufficient funds.");
        long receiverNewBalance = Math.addExact(receiver.balanceCents, cents);
        balanceCents -= cents;
        receiver.balanceCents = receiverNewBalance;
        transactions.add("Transferred " + formatMoney(cents) + " to " + receiver.accountNumber);
        receiver.transactions.add("Received " + formatMoney(cents) + " from " + accountNumber);
    }

    private static void requirePositive(long cents) {
        if (cents <= 0) throw new IllegalArgumentException("Amount must be greater than $0.00.");
    }

    public static long parseMoney(String input) {
        try {
            return new BigDecimal(input.trim()).setScale(2, RoundingMode.UNNECESSARY)
                    .movePointRight(2).longValueExact();
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Enter an amount such as 25 or 25.50.");
        }
    }

    public static String formatMoney(long cents) {
        return "$" + BigDecimal.valueOf(cents, 2).setScale(2).toPlainString();
    }
}
