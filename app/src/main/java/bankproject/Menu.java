package bankproject;

import java.util.Scanner;
import java.io.Console; // looking for ways to read password and keep it hidden, this package has a good built in method

public class Menu {
    Scanner scan = new Scanner(System.in);
    Console con = System.console();
    DatabaseActions DBActions = new DatabaseActions();

    public void printMainMenu() {
        int choice = -1;
        try {
            System.out.println("Welcome To Stevies Bank\n");
            System.out.println("1. Login\n2. Create Account\n0. Exit");
            System.out.print("Enter choice:");
            choice = Integer.parseInt(scan.nextLine()); // changed to this method because scan.nextInt() seemed to cause
                                                        // a
            // loop somehow, this method works
            System.out.println(choice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Choice entered, try again, 0 if you want to quit");
            printMainMenu();
        }

        switch (choice) {
            case 1 -> {
                printLoginMenu();
            }
            case 2 -> {
                accountCreationMenu();
            }
            case 0 -> {
                System.out.println("Thanks for visiting, bye");
            }
            default -> {
                System.out.println("Invalid Number Entered, 0 to exit");
                printMainMenu();
            }
        }

    }

    private void printLoginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String username = scan.nextLine();

        char[] password = con.readPassword("Enter Password: ");
        String stringPassword = String.valueOf(password);

        User user = DBActions.login(username, stringPassword); // if its null it means it doesnt exist

        if (user != null) {
            // Step 1: Generate OTP
            String otp = PasswordUtils.generateOTP();
            System.out.println("Your login code is: " + otp); // simulate SMS/email

            // Step 2: Ask for it
            System.out.print("Enter the 6-digit code: ");
            String inputOtp = scan.nextLine();

            if (otp.equals(inputOtp.trim())) {
                System.out.println("Login successful! Welcome, " + username);
                printUserMenu(user);
                printMainMenu();
            } else {
                System.out.println("Incorrect code. Login failed.");
                printMainMenu();
            }

        } else {
            System.out.println("Invalid credentials. Please try again.");
            printMainMenu();
        }
    }

    private void printUserMenu(User user) {
        int choice = -1;

        do {
            System.out.println("\nWelcome " + user.getUsername());
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("0. Logout");

            System.out.print("Enter choice: ");
            String input = scan.nextLine(); // Always use nextLine() then parse safely

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Skip the rest of the loop and ask again
            }

            switch (choice) {
                case 1:
                    System.out.printf("%n***** Your balance: %.2f *****%n", user.checkBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    String depositInput = scan.nextLine();
                    try {
                        double deposit = Double.parseDouble(depositInput);
                        if (InputValidator.isValidAmount(depositInput.trim()) && user.deposit(deposit)) {
                            DBActions.updateBalance(user);
                            System.out.println("Deposit successful.");
                        } else {
                            System.out.println("Invalid deposit amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    String withdrawInput = scan.nextLine();
                    try {
                        double withdraw = Double.parseDouble(withdrawInput);
                        if (InputValidator.isValidAmount(withdrawInput.trim()) && user.withdraw(withdraw)) {
                            DBActions.updateBalance(user);
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Invalid or insufficient amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void accountCreationMenu() {
        System.out.println("Create your account");
        System.out.print("Username(If you want to quit leave either field blank): ");
        String username = scan.nextLine();
        char[] password = con.readPassword("Enter Password(Must be 10 Characters, 1 Upper, and 1 special): ");
        String newPass = String.valueOf(password);
        // check if the username and password is valid before adding to database
        if (!InputValidator.isValidUsername(username)) {
            System.out.println("Invalid username.");
        } else if (!InputValidator.isValidPassword(newPass)) {
            System.out.println("Invalid password.");
        } else if (DBActions.registerUser(username, newPass)) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Registration failed (maybe username already exists).");
        }
        printMainMenu();
    }
}
