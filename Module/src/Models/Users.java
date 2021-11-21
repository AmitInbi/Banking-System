package Models;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Users {
    private static int idGen = 0;   //auto-increments id
    final private String id;
    private String firstName;
    private String lastName;
    private List<String> opLog = new ArrayList<>();

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

    public void opLog_add(String op) { this.opLog.add(op); }

    public String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    @Override
    public String toString(){
        return "ID:"+getId()+
                "  Name:"+getFirstName()+" "+getLastName();


    }
}
