package com.lovely3x.jsonparser.matcher;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.TypeTable;
import com.lovely3x.jsonparser.annotations.JSON;
import com.lovely3x.jsonparser.annotations.JSONArray;
import com.lovely3x.jsonparser.annotations.JSONObject;
import com.lovely3x.jsonparser.model.ObjectCreatorConfig;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 注解匹配器,通过注解的方式来判断一个json的key是否和这个字段名匹配
 * Created by lovely3x on 15-7-11.
 */
public class AnnotationMatcher implements JSONMatcher {

    private static final String TAG = "AnnotationMather";

    @Override
    public ObjectCreatorConfig match(ObjectCreatorConfig newConfig) {
        JSON jsonField = newConfig.field.getAnnotation(JSON.class);
        if (jsonField != null) {
            return configWithBaseType(jsonField, newConfig);
        }
        JSONArray jsonArrayField = newConfig.field.getAnnotation(JSONArray.class);
        if (jsonArrayField != null) {
            return configWithJSONArrayType(jsonArrayField, newConfig);
        }
        JSONObject jsonObjectField = newConfig.field.getAnnotation(JSONObject.class);
        if (jsonObjectField != null) {
            return configWithJSONObjectType(jsonObjectField, newConfig);
        }
        return newConfig;
    }

    @Override
    public void putValue(Object instance, JSONMatcher matcher,
                         com.lovely3x.jsonparser.model.JSONObject jsonObject,
                         ObjectCreatorConfig config) throws IllegalAccessException,
            ClassNotFoundException,
            InstantiationException {

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
                Object subObject = createObject(config.fieldValueType);
                if (subObject != null) {
                    field.set(instance, matcher.valueRule(
                            jsonObject.getJSONObject(config.jsonKey).
                                    createObject(subObject.getClass(), new AnnotationMatcher())));
                }
                break;
            case JSONType.JSON_TYPE_ARRAY:
                //创建容器对象
                Object container = createObject(config.fieldValueType);
                Class subClass = Class.forName(config.subFieldValueType);
                if (container != null) {
                    field.set(instance, matcher.valueRule(jsonObject.getJSONArray(config.jsonKey).createObjects((Class<? extends List>) container.getClass(), subClass)));
                }
                break;
        }
    }

    /**
     * 根据给定类名创建对象
     *
     * @param name 需要创建对象的类名
     * @return null 或者 创建的对象
     */
    private Object createObject(String name) {
        try {
            return Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 配置JSON Object属性
     *
     * @param jsonObjectField 指定的json对象注解字段
     * @param newConfig       指定的配置文件
     * @return 配置文件
     */
    private ObjectCreatorConfig configWithJSONObjectType(JSONObject jsonObjectField, ObjectCreatorConfig newConfig) {
        if (jsonObjectField == null) {
            newConfig.isEqual = false;
            return newConfig;
        }
        if (newConfig.jsonKey != null && newConfig.jsonKey.getKey() != null) {
            if (newConfig.jsonKey.getKey().equals(jsonObjectField.value())) {
                newConfig.isEqual = true;
                newConfig.jsonValueType = JSONType.JSON_TYPE_OBJECT;
                newConfig.fieldValueType = jsonObjectField.object().getName();
            }
        } else {
            newConfig.isEqual = false;
        }
        return newConfig;
    }

    /**
     * 配置jsonArray属性
     *
     * @param jsonArrayField 指定的jsonArray注解字段
     * @param newConfig      指定的配置文件
     * @return 配置文件
     */
    private ObjectCreatorConfig configWithJSONArrayType(JSONArray jsonArrayField, ObjectCreatorConfig newConfig) {
        if (jsonArrayField == null) {
            newConfig.isEqual = false;
            return newConfig;
        }
        if (newConfig.jsonKey != null && newConfig.jsonKey.getKey() != null) {
            if (newConfig.jsonKey.getKey().equals(jsonArrayField.value())) {
                newConfig.isEqual = true;
                newConfig.jsonValueType = JSONType.JSON_TYPE_ARRAY;
                newConfig.fieldValueType = jsonArrayField.container().getName();
                newConfig.subFieldValueType = jsonArrayField.object().getName();
            }
        } else {
            newConfig.isEqual = false;
        }
        return newConfig;
    }

    /**
     * 配置基础属性
     *
     * @param jsonField 指定的json注解字段
     * @param newConfig 指定的配置文件
     * @return 配置文件
     */
    private ObjectCreatorConfig configWithBaseType(JSON jsonField, ObjectCreatorConfig newConfig) {
        if (jsonField == null) {
            newConfig.isEqual = false;
            return newConfig;
        }
        if (newConfig.jsonKey != null && newConfig.jsonKey.getKey() != null) {
            if (newConfig.jsonKey.getKey().equals(jsonField.value())) {
                newConfig.isEqual = true;
                newConfig.jsonValueType = TypeTable.getJSONTypeByJavaType(newConfig.field.getType().getName());
            }
        } else {
            newConfig.isEqual = false;
        }
        return newConfig;
    }

    @Override
    public Object valueRule(Object value) {
        return value;
    }
}
