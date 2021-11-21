package Models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Customer extends Users implements UsersInterface {
    private long balance;

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

    /** Deposit money to the account and update DB.
     @param amount: amount of money to withdraw from account.
     @return true if deposit is successful.
     **/
    public boolean deposit(int amount) {
        this.balance += amount;
        opLog_add("" + super.getTimeStamp() + ":deposit:" + amount);
        updateDB();
        return true;
    }

    /** withdraws money. doesn't set warning but fails to withdraw funds if there is insufficient amount
     * Updates DB if withdrawal is successful
     @param amount: amount of money to withdraw from account.
    @return true if withdrawal is successful, false otherwise
    **/
    public boolean simpleWithdraw(int amount) {
        // Check if there are sufficient funds to withdraw
        if(this.balance - amount > 0) {
            this.balance -= amount;
            opLog_add(super.getTimeStamp() + ":withdraw:" + amount);
            updateDB();
            return true;
        } else {
            opLog_add(super.getTimeStamp() + ":FAILED_Withdraw:" + amount);
            return false;
        }
    }

    /** Withdraws money. if balance reaches below 0, asks for confirmation.
     * Updates DB if withdrawal is successful
     * This method is Java oriented since the confirmation is requested via terminal.
     * @param money: amount of money to withdraw from account.
     * @return true if withdrawal is successful, false otherwise
     */
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

    /** Return JSONObject from customer
     * containing all firstName, lastName, balance and opLog fields
     * @param
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON(){
        JSONObject customer = new JSONObject();
        customer.put("firstName", this.getFirstName());
        customer.put("lastName", this.getLastName());
        customer.put("balance", this.getBalance());

        // Get opLog as String[] and cast to JSONArray
        JSONArray jsArray = new JSONArray();
        for (int i = 0; i < this.getOpLog().length; i++) { jsArray.add(this.getOpLog()[i]); }
        customer.put("opLog", jsArray);

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
