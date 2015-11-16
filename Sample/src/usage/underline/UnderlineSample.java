package usage.underline;

import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.source.JSONSourceImpl;
import usage.annotations.Book;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lovely3x on 15-11-15.
 * 注解使用例子
 */
public class UnderlineSample {

    public static void main(String[] args) {
        UnderlineSample as = new UnderlineSample();
        JSONObject jo = as.readJSONObject();
        as.makeClass(jo, "Book");

        as.formatJSON(jo);
        as.makeObject(jo);

        com.lovely3x.jsonparser.model.JSONArray ja = as.readJSONArray();
        as.makeObjects(ja);

    }

    /**
     * 解析一组对象
     *
     * @param array 数据来源
     */
    private void makeObjects(com.lovely3x.jsonparser.model.JSONArray array) {
        try {
            System.out.println(array.createObjects(Book.class));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 手动解析太繁琐?
     * 自动解析来帮你
     *
     * @param jsonObject 创建类的数据源
     */
    private void makeObject(JSONObject jsonObject) {
        Book obj = jsonObject.createObject(Book.class);
        System.out.println(obj);
    }


    /**
     * 嫌弃格式太乱?
     * 格式化后更容易辨识
     *
     * @param jsonObject 需要格式化的对象
     */
    private void formatJSON(JSONObject jsonObject) {
        String result = jsonObject.format();
        System.out.println(result);
    }


    //生成一个Book类文件在项目的根目录下
    //你也可以输出到指定的地方
    private void makeClass(JSONObject jo, String name) {
        try {
            jo.createClass(name, new FileOutputStream(name + ".java")).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSONObject readJSONObject() {
        try {
            return new JSONObject(new JSONSourceImpl(getClass().getResourceAsStream("../Book.json")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取一个json数组
     *
     * @return JSONArray对象
     */
    public com.lovely3x.jsonparser.model.JSONArray readJSONArray() {
        try {
            return new com.lovely3x.jsonparser.model.JSONArray(new JSONSourceImpl(getClass().getResourceAsStream("../Books.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
