package Models;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Users {
    private static int idGen = 0;   //auto-increments id
    final private String id;
    private String firstName;
    private String lastName;

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

    public String getTimeStamp() {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//        return timeStamp;
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    @Override
    public String toString(){
        return "ID:"+getId()+
                "  Name:"+getFirstName()+" "+getLastName();


    }
}
