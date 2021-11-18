package Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;


public class Customer extends Users implements UsersInterface {
    private int balance;

    public Customer(String firstName, String lastName, int balance) {
        super(firstName, lastName);
        this.balance = balance;
    }

    public Customer(int id, String firstName, String lastName, int balance) {
        super(id, firstName, lastName);
        this.balance = balance;
    }


    public Customer getCustomer(){ return this; }

    public int getBalance() {
        return balance;
    }

    //deposits money.
    public boolean deposit(int money) {
        this.balance += money;
        updateDB();
        return true;
    }
    //withdraws money. if balance reaches below 0, sets a warning.
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

        updateDB();
        return false;
    }

    // Return JSONObject from customer
    @Override
    public JSONObject toJSON(){
        JSONObject customer = new JSONObject();
        customer.put("firstName", this.getFirstName());
        customer.put("lastName", this.getLastName());
        customer.put("balance", this.getBalance());
        return customer;
    }

    @Override
    public String toString() {
        return super.toString()
                +"   Balance:"+getBalance();
    }

    // Method calls customer_db and updates it with the updated data
    @Override
    public boolean updateDB(){
        try {
            //  Parse JSON file to JSON object
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("Module/src/dbs/db_Customer.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // Remove current customer and load new one to the jsonObject
            jsonObject.remove(this.getId());
            jsonObject.put(this.getId(), this.toJSON());

            // Write updated jsonObject to db_Customer
            FileWriter file = new FileWriter("Module/src/dbs/db_Customer.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            return true;

        } catch (Exception e){ return false; }
    }
}
