package usage.test;

import com.lovely3x.jsonparser.matcher.MixMatcher;
import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.objectcreator.SuperObjectCreator;
import com.lovely3x.jsonparser.source.JSONSource;
import com.lovely3x.jsonparser.source.JSONSourceImpl;
import usage.annotations.PersonResponse;

import java.io.IOException;

/**
 * Created by lovely3x on 15-11-15.
 */
public class Test {

    private static final String TAG = "Test";

    public static void main(String[] args) {
        Test test = new Test();
        JSONObject jo = new JSONObject(test.getPersonResponse());
        PersonResponse objs = (PersonResponse) jo.createObject(
                PersonResponse.class,
                new SuperObjectCreator<PersonResponse>(),
                new MixMatcher());
        System.out.println(objs);


    }

    private JSONSource getRoster() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Roster.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONSource getClasses() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Classes.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONSource getPersonResponse() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Response.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
