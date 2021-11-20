package Models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class Customer extends Users implements UsersInterface {
    private long balance;
    private List<String> opLog = new ArrayList<>();


    public Customer(String firstName, String lastName, long balance) {
        super(firstName, lastName);
        this.balance = balance;
    }

    public Customer(int id, String firstName, String lastName, long balance) {
        super(id, firstName, lastName);
        this.balance = balance;
    }

    public Customer getCustomer(){ return this; }

    public long getBalance() { return this.balance; }

    public String[] getOpLog() {
        String[] arr = new String[this.opLog.size()];
        for (int i = 0; i < arr.length; i++) { arr[i] = this.opLog.get(i); }
        return arr; 
    }

    //deposits money.
    public boolean deposit(int amount) {
        this.balance += amount;
        this.opLog.add(super.getTimeStamp() + ":deposit:" + amount);
        updateDB();
        return true;
    }

    /** withdraws money. doesn't set warning but fails to withdraw funds if there is insufficient amount
    @param amount: amount of money to withdraw
    @return true if withdrawal is successful, false otherwise
    **/
    public boolean simpleWithdraw(int amount) {
        // Check if there are sufficient funds to withdraw
        if(this.balance - amount > 0) {
            this.balance -= amount;
            this.opLog.add(super.getTimeStamp() + ":withdraw:" + amount);
            updateDB();
            return true;
        } else {
            this.opLog.add(super.getTimeStamp() + ":FAILED_Withdraw:" + amount);
            return false;
        }

    }

    //withdraws money. if balance reaches below 0, sets a warning.
    public boolean withdraw(int money) {
        Scanner s = new Scanner(System.in);
        char ans = 'Y';
        long sum = this.balance - money;
        if (sum < 0) {
                System.out.println("Are you sure you want to withdraw? [Y/N] \nBalance after withdrawal: "+sum);
                ans = s.nextLine().toUpperCase().charAt(0);       //Saves the first char of user answer and makes it uppercase
        }
        if (ans == 'Y') {
            this.balance -= money;
            updateDB();
            return true;
        }
        return false;
    }

    // Return JSONObject from customer
    @Override
    public JSONObject toJSON(){
        JSONObject customer = new JSONObject();
        customer.put("firstName", this.getFirstName());
        customer.put("lastName", this.getLastName());
        customer.put("balance", this.getBalance());
        customer.put("opLog", this.getOpLog());
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
