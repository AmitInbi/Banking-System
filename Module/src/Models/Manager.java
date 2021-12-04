package Models;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

public class Manager extends Users {
    public Manager(String firstName, String lastName) {
        super(firstName, lastName);
    }

    private Object parseCusts(){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("Module/src/dbs/db_Customer.json"));
            return obj;
        } catch (Exception e){ return null; }
    }

    public JSONObject getCustomerById(int id){
            //Create a json_customer by fetched key (=id) after parsing db (using parseCusts())
            JSONObject json_customer = (JSONObject) ((JSONObject) parseCusts()).get(id+"");
            return json_customer;
    }

    public void getCustomerByName(String name) {
        JSONObject json_all_customers = (JSONObject) parseCusts();

        //Iterates over all json elements. TODO: Grab only the ones with the given name.

        json_all_customers.keySet().forEach(keyStr ->
        {
            Object keyvalue = json_all_customers.get(keyStr);
            JSONObject json_keyvalue = (JSONObject) keyvalue;
            //Checks if given string is found in any customer name
            if ((json_keyvalue.get("firstName").toString() + " " + json_keyvalue.get("lastName").toString()).contains(name))
                System.out.println(json_keyvalue);
            //System.out.println("debug");

            //System.out.println("key: "+ keyStr + " value: " + keyvalue);

            //for nested objects iteration if required
            //if (keyvalue instanceof JSONObject)
            //    printJsonObject((JSONObject)keyvalue);
            //System.out.println(keyStr);
        });


    }

}
