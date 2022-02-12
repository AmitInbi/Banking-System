package Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Auth {
    static public int validatePW(String username, String pw2val) throws IOException, ParseException {
        int id = -1;
        //  Parse JSON file to JSON object
        JSONParser parser = new JSONParser();
        JSONObject logins = (JSONObject) parser.parse(new FileReader("Module/src/dbs/db_Logins.json"));
        // Get customers actual pw from DB to compare
        JSONObject customer_asJSONObject = (JSONObject) logins.get(username);
        if (customer_asJSONObject != null){
            String pw = (String) customer_asJSONObject.get("pw");
            int real_id = ((Number) customer_asJSONObject.get("id")).intValue();

            if (pw.equals(pw2val)){
                id = real_id;
            }
        }
        logins = null; // Release the Logins DB after getting users password for the garbage collector

        return id;
    }
}
