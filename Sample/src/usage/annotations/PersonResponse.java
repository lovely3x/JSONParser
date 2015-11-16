package usage.annotations;

import com.lovely3x.jsonparser.annotations.JSONArray;

import java.util.List;

/**
 * Created by lovely3x on 15-11-15.
 */
public class PersonResponse extends Response {

    @JSONArray(value = "data", object = Person.class)
    List<Person> personList;

    @Override
    public String toString() {
        return super.toString() + "\tPersonResponse{" +
                "personList=" + personList +
                '}';
    }
}
