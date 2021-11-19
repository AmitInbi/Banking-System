import Models.Customer;

//import jdk.swing.interop.SwingInterOpUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import java.io.FileReader;

public class API {
    private static Customer customer;

    // Create a local Customer object by called customer ID from db_Customer
    public void createConnection(int ID)  throws IOException, ParseException {
        String id = ID+"";
        // Parse JSON file to JSON object
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("Module/src/dbs/db_Customer.json"));
        JSONObject jsonObject = (JSONObject) obj;

        JSONObject customer_asJSONObject = (JSONObject) jsonObject.get(id);

//        // Get Customer by id as JSONArray
//        JSONArray customer_asJSONArray = (JSONArray) jsonObject.get(id);
//        // Convert customer of type JSONArray to type JSONObject
//        JSONObject customer_asJSONObject = (JSONObject) customer_asJSONArray.get(0);

        this.customer = new Customer(
                ID,
                (String)customer_asJSONObject.get("firstName"),
                (String)customer_asJSONObject.get("lastName"),
                (long) customer_asJSONObject.get("balance"));
    }


    public long getCustomerBalance() { return this.customer.getBalance(); }

    public String getCustomerName() { return this.customer.getFirstName() + " " + this.customer.getLastName(); }

    public void customerDeposit(int amount) { this.customer.deposit(amount); }

}
