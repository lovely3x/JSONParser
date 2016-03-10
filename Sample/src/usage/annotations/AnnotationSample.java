package usage.annotations;

import com.lovely3x.jsonparser.Config;
import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.source.JSONSourceImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lovely3x on 15-11-15.
 * 注解使用例子
 */
public class AnnotationSample {

    public static void main(String[] args) {
        AnnotationSample as = new AnnotationSample();
        JSONObject jo = as.readJSONObject();

        as.makeClass(jo, "Book");

        as.formatJSON(jo);
        as.makeObject(jo);

        com.lovely3x.jsonparser.model.JSONArray ja = as.readJSONArray();
        as.makeObjects(ja);

        jo = as.readAnnotationJSONObject();

        as.makeObjectsByAnnotation(jo);

        //这里使用java对象来创建json串
        //我们是支持 通过map和list的方式创建的
        //这里就不演示了
        Book book = new Book("lovely3x", 88.88f, "JSONParser用法", System.currentTimeMillis(), 1);
        as.objectToJSON(book);
    }


    /**
     * 服务端要求传递 json 字符串
     * 不好封装还易出错?
     * 没事,我们可以自动封装
     *
     * @param book 需要封装的对象
     */
    private void objectToJSON(Book book) {
        String result = new JSONObject(book).toString();
        System.out.println(result);
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
     * 服务端字段命名名不符合我胃口,我要自定义,当然没问题
     * 注解来帮忙
     *
     * @param object 数据源
     */
    private void makeObjectsByAnnotation(JSONObject object) {
        //可以从结果看出来,使用注解的方式可以不受服务端的字段名的约束
        //我们自己想要怎么命名我们自己的字段名就怎样命名
        Book book = object.createObject(Book.class);
        System.out.println("\n使用注解方式生成");
        System.out.println(book);

        book = object.createObject(Book.class);
        System.out.println("\n不使用使用注解方式生成");
        System.out.println(book);
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
            return new JSONObject(new JSONSourceImpl(getClass().getResourceAsStream("../Book.json2")));

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
            return new com.lovely3x.jsonparser.model.JSONArray(new JSONSourceImpl(getClass().getResourceAsStream("../Books.json2")), Config.createDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取用于annotation 实例的JSONObject
     *
     * @return
     */
    public JSONObject readAnnotationJSONObject() {
        try {
            return new com.lovely3x.jsonparser.model.JSONObject(new JSONSourceImpl(getClass().getResourceAsStream("../BookAnnotation.json2")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
