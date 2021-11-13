import Models.Customer;

import jdk.swing.interop.SwingInterOpUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;


//import com.google.gson.*;
////import com.google.gson.GsonBuilder;
//import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

public class API {
    public static Customer getCustomer(String id) throws IOException, ParseException {
        // Parse JSON file to JSON objext
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("Module/src/dbs/db_Customer.json"));
        JSONObject jsonObject = (JSONObject) obj;
        // Get Customer by id as JSONArray
        JSONArray customer = (JSONArray) jsonObject.get(id);
        // Convert customer of type JSONArray to type JSONObject
        JSONObject customer_obj = (JSONObject) customer.get(0);

        Customer cust = new Customer(
                Integer.parseInt(id),
                (String)customer_obj.get("firstName"),
                (String)customer_obj.get("lastName"),
                Integer.parseInt((String) customer_obj.get("balance")));

        return cust;

//        Gson gson = new Gson();
//        JsonReader reader = new JsonReader(new FileReader("Module/src/dbs/db_Customer.json"));
////        String customers = gson.fromJson(reader, String.class);
//        HashMap<String, Object > json = gson.fromJson(reader, HashMap.class);
//        System.out.println(json.toString());
//
//        BufferedReader br = null;
//        br = new BufferedReader(new FileReader("Module/src/dbs/db_Customer.json"));
//        Customer cust = gson.fromJson(br, Customer.class);
//        System.out.println(cust);
//
//       // String customer = (String)json.getString(id);
//        //System.out.println(customer);
//        return cust.toString();
    }
}
