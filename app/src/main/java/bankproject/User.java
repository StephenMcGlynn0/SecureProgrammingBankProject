package bankproject;

public class User {
    private String username;
    private double balance;

    public User(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }
    

    public String getUsername(){
        return this.username;
    }

    public boolean deposit(double amount){
        if(amount > 0){
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount){
        if(this.balance - amount >=0 && amount > 0){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public double checkBalance(){
        return this.balance;
    }
}
