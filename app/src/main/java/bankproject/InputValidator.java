package bankproject;

public class InputValidator {
    public static boolean isValidUsername(String username) {
        //allows all letters and numbers and username must be between 4 and 20 characters
        return username != null && !username.trim().isEmpty() && username.matches("^[a-zA-Z0-9_]{4,20}$");
    }

    public static boolean isValidAmount(String amount) {
        //makes sure that amount is valid, only accepts 2 decimals after the dot 3 wont work,
        return amount.matches("\\d+(\\.\\d{1,2})?");
    }

    public static boolean isValidPassword(String password) {
        //regex enforces atleast 10 characters, atleast one uppercase letter, and atleast one special character
        return password != null && !password.trim().isEmpty() && password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~-]).{10,}$");
    }
}
