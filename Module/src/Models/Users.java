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

public class Users implements UsersInterface {
    private static int idGen = 0;   //auto-increments id
    final private String id;
    private String firstName;
    private String lastName;
    List<String> opLog = new ArrayList<>();

    public Users(String firstName, String lastName) {
        //auto increment id for every User
        this.id = "" + idGen++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Users(int id, String firstName, String lastName) {
        //auto increment id for every User
        this.id = ""+id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Getter for the opLog field,
     * Casts List<String> opLog to type String[]
     * @return String[] of opLog
     */
    public String[] getOpLog() {
        String[] arr = new String[this.opLog.size()];
        for (int i = 0; i < arr.length; i++) { arr[i] = this.opLog.get(i); }
        return arr;
    }

    public void opLog_add(String op) {
        this.opLog.add(op);
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    @Override
    public String toString(){
        return "ID:"+getId()+
                "  Name:"+getFirstName()+" "+getLastName();
    }

    /** Return JSONObject from user
     * containing all firstName, lastName fields
     * @param
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON(){
        JSONObject user = new JSONObject();
        user.put("firstName", this.getFirstName());
        user.put("lastName", this.getLastName());

        String[] opLog_arr = new String[this.opLog.size()];
        for (int i = 0; i < opLog_arr.length; i++) { opLog_arr[i] = this.opLog.get(i); }

        // Get opLog as String[] and cast to JSONArray
        JSONArray jsArray = new JSONArray();
        for (int i = 0; i < opLog_arr.length; i++) { jsArray.add(opLog_arr[i]); }
        user.put("opLog", jsArray);

        return user;
    }

    // Method calls customer_db and updates it with the updated data
    @Override
    public boolean updateDB(String db){
        try {
            //  Parse JSON file to JSON object
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(db));
            JSONObject jsonObject = (JSONObject) obj;

            // Remove current customer and load new one to the jsonObject
            jsonObject.remove(this.getId());
            jsonObject.put(this.getId(), this.toJSON());

            // Write updated jsonObject to db_Customer
            FileWriter file = new FileWriter(db);
            file.write(jsonObject.toJSONString());
            file.flush();
            return true;

        } catch (Exception e){ return false; }
    }
}
