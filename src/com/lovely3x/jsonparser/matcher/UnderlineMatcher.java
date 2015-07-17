package com.lovely3x.jsonparser.matcher;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.TypeTable;
import com.lovely3x.jsonparser.log.Log;
import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.ObjectCreatorConfig;

import java.lang.reflect.Field;

/**
 * 下划线匹配器,常规数据库命名法则是 单词小写 多个单词间使用 '_' 分开 这个就适用于这种情况,
 * 当然,如果是完全相同也是可以的
 * Created by lovely3x on 15-7-1.
 */
public class UnderlineMatcher implements JSONMatcher {
    private static final String TAG = "UnderlineMatcher";
    /**
     * 匹配 关键字 下划线
     */
    public final char MATCH_KEYWORDS = '_';

    @Override
    public ObjectCreatorConfig match(ObjectCreatorConfig newConfig) {
        final JSONKey jsonKey = newConfig.jsonKey;
        final Field field = newConfig.field;
        newConfig.isEqual = isEqual(field.getName(), jsonKey.getKey());
        newConfig.jsonValueType = TypeTable.getJSONTypeByJavaType(field.getType().getName());
        return newConfig;
    }

    /**
     * 判断给定的这个字段名和json的字段名是否相同
     *
     * @param fieldName
     * @param jsonKyeName
     * @return
     */
    private boolean isEqual(String fieldName, String jsonKyeName) {
        //如果完全匹配,则直接返回 true
        if (fieldName.equals(jsonKyeName)) return true;

        final int count = jsonKyeName.length();
        StringBuilder sb = new StringBuilder();

        char preChar = '\0';
        for (int i = 0; i < count; i++) {
            char currentJsonKeyChar = jsonKyeName.charAt(i);
            switch (currentJsonKeyChar) {
                case MATCH_KEYWORDS:
                    //pass
                    break;
                default:
                    switch (preChar) {
                        case MATCH_KEYWORDS:
                            sb.append((char) (currentJsonKeyChar - 32));
                            break;
                        default:
                            sb.append(currentJsonKeyChar);
                            break;
                    }
                    break;
            }
            preChar = currentJsonKeyChar;
        }
        return sb.toString().equals(fieldName);
    }

    @Override
    public void putValue(Object instance, JSONMatcher matcher,
                         com.lovely3x.jsonparser.model.JSONObject jsonObject,
                         ObjectCreatorConfig config) throws IllegalAccessException {
        Field field = config.field;
        switch (config.jsonValueType) {
            case JSONType.JSON_TYPE_BOOLEAN:
                field.setBoolean(instance, (Boolean) matcher.valueRule(jsonObject.getBoolean(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_INT:
                field.setInt(instance, (Integer) matcher.valueRule(jsonObject.getInt(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_LONG:
                field.setLong(instance, (Long) matcher.valueRule(jsonObject.getLong(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_FLOAT:
                field.setFloat(instance, (Float) matcher.valueRule(jsonObject.getFloat(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_DOUBLE:
                field.setDouble(instance, (Double) matcher.valueRule(jsonObject.getDouble(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_STRING:
                field.set(instance, matcher.valueRule(jsonObject.getString(config.jsonKey)));
                break;
            case JSONType.JSON_TYPE_OBJECT:
                Log.e(TAG, "需要创建json对象,但是是不被支持的");
                break;
            case JSONType.JSON_TYPE_ARRAY:
                Log.e(TAG, "需要创建jsonArray,但是是不被支持的");
                break;
        }
    }

    @Override
    public Object valueRule(Object value) {
        return value;
    }
}
