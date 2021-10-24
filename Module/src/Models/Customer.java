package Models;

import java.util.Scanner;

public class Customer extends Users implements UsersInteface{
    private int balance;

    public Customer(String firstName, String lastName, int balance) {
        super(firstName, lastName);
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    //deposits money.
    @Override
    public boolean deposit(int money) {
        this.balance += money;
        return true;
    }
    //withdraws money. if balance reaches below 0, sets a warning.
    @Override
    public boolean withdraw(int money) {
        Scanner s = new Scanner(System.in);
        char ans = 'Y';
        int sum = this.balance - money;
        if (sum < 0)
            {
                System.out.println("Are you sure you want to withdraw? [Y/N] \nBalance after withdrawal: "+sum);
                ans = s.nextLine().toUpperCase().charAt(0);       //Saves the first char of user answer and makes it uppercase
            }
        if (ans == 'Y') {
            this.balance -= money;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString()
                +"   Balance:"+getBalance();
    }
}
