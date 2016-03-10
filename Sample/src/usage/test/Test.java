package usage.test;

import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.source.JSONSource;
import com.lovely3x.jsonparser.source.JSONSourceImpl;
import org.json2.JSONException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovely3x on 15-11-15.
 */
public class Test {

    private static final String TAG = "Test";


    public static void main(String args[]) throws IOException, JSONException {


    }

    public static void main_(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        long startTime = System.nanoTime();
        List<Dynamic> dynamics = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            ArrayList<String> imgs = new ArrayList<>();
            imgs.add("http://www.img.com");

            Dynamic dynamic = Dynamic.class.getConstructor(
                    String.class, String.class, String.class, String.class, List.class, String.class).newInstance(
                    "今天天气真好", "好像下雨一样，我很喜欢", String.valueOf(i), "小小", imgs, "http://www.head.com");
            dynamics.add(dynamic);
        }
        System.out.println("create dynamic consumed time " + ((System.nanoTime() - startTime) / 1000000));
        /*consumed time 35269
        create object 1000000 consumed time 27438*/
        startTime = System.nanoTime();
        com.lovely3x.jsonparser.model.JSONArray jsonArray = new com.lovely3x.jsonparser.model.JSONArray((List) dynamics);
        String result = jsonArray.toString();
        System.out.println("consumed time " + ((System.nanoTime() - startTime) / 1000000));
        startTime = System.nanoTime();
        jsonArray = new com.lovely3x.jsonparser.model.JSONArray(result);
        List<Dynamic> dynamic = jsonArray.createObjects(Dynamic.class);
        System.out.println("create object " + dynamic.size() + " consumed time " + ((System.nanoTime() - startTime) / 1000000));
        //System.out.println(result);
        // com.lovely3x.jsonparser.model.JSONArray ja = new com.lovely3x.jsonparser.model.JSONArray(result);
        //  System.out.println(ja.createObjects(Dynamic.class));
    }

    public static void main_1(String[] args) {
        String value = "{\"dynamicname\":\"{\"items\":[{\"img_uri\":\"http://192.168.0.132:8080/fishing/data/topicimg/2015/12/18/104_20151218140700018113.jpg\",\"text\":\"null\",\"sequence\":0,\"type\":1},{\"img_uri\":\"http://192.168.0.132:8080/fishing/data/topicimg/2015/12/18/104_20151218140700019423.jpg\",\"text\":\"null\",\"sequence\":1,\"type\":1},{\"img_uri\":\"null\",\"text\":\"峨眉山的猴子\",\"sequence\":w,\"type\":2}]}\"}";
        JSONObject jo = new JSONObject(value);
        System.out.print(jo.getPair().keySet());

    }

    private JSONSource getRoster() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Roster.json2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONSource getClasses() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Classes.json2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONSource getPersonResponse() {
        try {
            return new JSONSourceImpl(getClass().getResourceAsStream("../Response.json2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
