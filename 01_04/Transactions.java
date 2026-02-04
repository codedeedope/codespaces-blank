
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Transactions {

    public static void main(String[] args) {
        ArrayList<String> transactions = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {

            for (;;) {
                System.out.println("1. Add Transaction");
                System.out.println("2. Exit");
                System.out.print("Enter choice:...");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Eat newline

                switch (choice) {
                    case 1:
                        System.out.println("Add transaction.");
                        System.out.println("Enter type:...");
                        String type = scanner.nextLine();
                        System.out.println("Enter amount:...");
                        String amount = scanner.nextLine();
                        String date = LocalDate.now().toString();
                        String transaction = String.format("%s, %s, %s", type, amount, date);
                        transactions.add(transaction);
                        System.out.println("Transaction added: " + transaction);
                        break;
                    case 2:
                        return;
                }
            }
        }
    }
}
