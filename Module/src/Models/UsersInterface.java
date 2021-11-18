package Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public interface UsersInterface {
    public boolean updateDB() throws IOException, ParseException;

    public JSONObject toJSON();

}
