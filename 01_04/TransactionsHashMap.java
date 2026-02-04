
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class TransactionsHashMap {

    public static void main(String[] args) {
        HashMap<Integer, String> transactions = new HashMap<>();
        int maxId = 1;

        try (Scanner scanner = new Scanner(System.in)) {
            for (;;) {
                System.out.println("1. Exit");
                System.out.println("2. Add Transaction");
                System.out.println("3. Count transactions yesterday");
                System.out.print("Enter choice:...");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Eat newline

                switch (choice) {
                    case 1:
                        System.out.println("Bye.");
                        return;
                    case 2: // Add transaction
                        System.out.println("Add transaction.");
                        System.out.println("Enter type:...");
                        String type = scanner.nextLine();
                        System.out.println("Enter amount:...");
                        String amount = scanner.nextLine();
                        String date = LocalDate.now().toString();
                        // String date = LocalDate.now().minusDays(1).toString(); //DBG
                        String transaction = String.format("%s,%s,%s", type, amount, date);
                        transactions.put(maxId++, transaction);
                        System.out.println("Transaction added: " + transaction);
                        break;
                    case 3: // Count transactions yesterday
                        String yesterday = LocalDate.now().minusDays(1).toString();
                        int count = (int) transactions.values().stream()
                                .map(t -> t.split(",")[2])
                                .filter(d -> d.equals(yesterday))
                                .count();
                        System.out.println(String.format("Transactions count yesterday: %s", count));
                        break;
                }
            }
        }
    }
}
