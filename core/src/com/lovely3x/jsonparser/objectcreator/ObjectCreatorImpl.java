package com.lovely3x.jsonparser.objectcreator;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.log.Log;
import com.lovely3x.jsonparser.matcher.JSONMatcher;
import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.model.ObjectCreatorConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 对象创建器,默认的实现
 * Created by lovely3x on 15-7-1.
 */
public class ObjectCreatorImpl<T> implements ObjectCreator<T> {

    private static final String TAG = "ObjectCreatorImpl";

    @Override
    public T create(JSONObject jsonObject, Class<T> t, JSONMatcher matcher) {
        ArrayList<JSONKey> keys = new ArrayList<>(jsonObject.keySet());
        Field[] fields = t.getDeclaredFields();

        final int jsonKeyCount = keys.size();
        final int fieldCount = fields.length;

        ObjectCreatorConfig config = new ObjectCreatorConfig();
        try {
            Object object = Class.forName(t.getName()).newInstance();
            for (int i = 0; i < jsonKeyCount; i++) {
                config.reset();
                JSONKey key = keys.get(i);
                config.jsonKey = key;
                for (int j = 0; j < fieldCount; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    config.field = field;
                    config.isEqual = false;
                    config.jsonValueType = JSONType.JSON_TYPE_INVALIDATE;
                    ObjectCreatorConfig tem = matcher.match(config);
                    if (tem != null && tem.isEqual) {
                        try {
                            matcher.putValue(object, matcher, jsonObject, tem);
                        } catch (Exception e) {
                            Log.e(TAG, e);
                        }
                    }
                }
            }
            return (T) object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
