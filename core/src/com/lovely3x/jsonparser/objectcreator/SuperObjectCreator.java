package com.lovely3x.jsonparser.objectcreator;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.log.Log;
import com.lovely3x.jsonparser.matcher.JSONMatcher;
import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.model.ObjectCreatorConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 默认的实现 {@link ObjectCreator }是不能对父类的字段映射的
 * 这个类可以对父类的字段映射 <p></br></p>
 * Created by lovely3x on 15-11-15.
 */
public class SuperObjectCreator<T> implements ObjectCreator {

    private static final String TAG = "SuperObjectCreator";

    /**
     * 是否迭代父类的字段集
     */
    private boolean iteratorSuperFields;

    /**
     * 默认迭代父类的字段集
     */
    public SuperObjectCreator() {
        this(true);
    }

    /**
     * 是否迭代父类的字段
     *
     * @param iteratorSuperFields true 表示迭代父类的字段集,false 则表示不迭代
     */
    public SuperObjectCreator(boolean iteratorSuperFields) {
        this.iteratorSuperFields = iteratorSuperFields;
    }

    /**
     * 这个字段是否能够修改(目前非常量的都可以修改)
     *
     * @param field 需要判断的字段
     * @return true 或 false
     */
    protected boolean canModify(Field field) {
        return field != null && ((field.getModifiers() & Modifier.FINAL) == 0);
    }

    /**
     * 创建一个类对象的实例
     *
     * @param clazz 需要创建类对象的实例
     * @return null or 对象实例
     */
    protected Object createInstance(Class clazz) {
        try {
            return Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T create(JSONObject jsonObject, Class t, JSONMatcher matcher) {

        Object instance = createInstance(t);
        if (instance == null) return null;

        Class tmp = t.getSuperclass();
        while (tmp != null && !Object.class.getName().equals(tmp.getName())) {
            process(jsonObject, tmp, instance, matcher);
            tmp = tmp.getSuperclass();
        }

        process(jsonObject, t, instance, matcher);

        return (T) instance;
    }


    /**
     * 处理类生成
     *
     * @param jsonObject 需要生成的jsonObject
     * @param t          当前字段查询类对象
     * @param instance   字段赋值执行实例
     * @param matcher    jsonKey和类字段匹配器
     */
    protected void process(JSONObject jsonObject, Class t, Object instance, JSONMatcher matcher) {
        ArrayList<JSONKey> keys = new ArrayList<>(jsonObject.keySet());
        Field[] fields = t.getDeclaredFields();

        ObjectCreatorConfig config = new ObjectCreatorConfig();
        try {
            for (JSONKey key : keys) {
                config.reset();
                config.jsonKey = key;
                for (Field field : fields) {
                    //常量使不能改的
                    if (!canModify(field)) continue;
                    field.setAccessible(true);
                    config.field = field;
                    config.isEqual = false;
                    config.jsonValueType = JSONType.JSON_TYPE_INVALIDATE;
                    ObjectCreatorConfig tem = matcher.match(config);
                    if (tem != null && tem.isEqual) {
                        try {
                            matcher.putValue(instance, matcher, jsonObject, tem);
                        } catch (Exception e) {
                            Log.e(TAG, e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
