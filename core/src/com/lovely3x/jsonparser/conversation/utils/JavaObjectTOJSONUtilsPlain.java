package com.lovely3x.jsonparser.conversation.utils;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.TypeTable;
import com.lovely3x.jsonparser.conversation.rule.JSONGenerateRule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java 对象转换为json的工具类
 * 普通模式 不会对引用再次解析
 * Created by lovely3x on 15-7-2.
 */
public class JavaObjectTOJSONUtilsPlain {

    /**
     * 转换规则
     */
    private final JSONGenerateRule mRule;
    /**
     * 栈深度
     */
    private final int stackLevel;

    private int objectStackLevel;

    private int mapStackLevel;

    private int listStackLevel;


    /**
     * @param rule 转换规则
     */
    public JavaObjectTOJSONUtilsPlain(JSONGenerateRule rule, int stackLevel) {
        this.mRule = rule;
        this.stackLevel = stackLevel;
        objectStackLevel = stackLevel;
        mapStackLevel = stackLevel;
        listStackLevel = stackLevel;
    }

    /**
     * 将 list 数据转换为 json 数据
     */
    public StringBuilder parseList(List objects) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        if (listStackLevel < 0) return sb;
        sb.append('[');
        final int count = objects == null ? 0 : objects.size();
        for (int i = 0; i < count; i++) {
            Object object = objects.get(i);
            if (object instanceof List) {
                listStackLevel--;
                StringBuilder sSb = parseList((List) object);
                sb.append(i == 0 ? "" : ",").append(sSb);
            } else if (object instanceof Map) {
                mapStackLevel--;
                sb.append(i == 0 ? "" : ",").append(parseMap((Map) object));
            } else if (object instanceof Integer) {//int
                sb.append(i == 0 ? "" : ",").append(object);
            } else if (object instanceof Long) {//long
                sb.append(i == 0 ? "" : ",").append(object);
            } else if (object instanceof Float) {//float
                sb.append(i == 0 ? "" : ",").append(object);
            } else if (object instanceof Double) {//double
                sb.append(i == 0 ? "" : ",").append(object);
            } else if (object instanceof Boolean) {//boolean
                sb.append(i == 0 ? "" : ",").append(object);
            } else if (object instanceof String) {//String
                sb.append(i == 0 ? "" : ",").append('"').append(object).append('"');
            } else {
                objectStackLevel--;
                sb.append(i == 0 ? "" : ",").append(parseObject(object));
            }
        }
        sb.append(']');
        return sb;
    }

    /**
     * 将对象转换为json字符串
     */
    public StringBuilder parseObject(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        if (objectStackLevel < 0) return sb;
        Field[] fields = object == null ? new Field[0] : object.getClass().getDeclaredFields();
        final int count = fields.length;
        if (count > 0) sb.append('{');
        for (int i = 0; i < count; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            int type = TypeTable.getJSONTypeByJavaType(field.getType().getName());
            switch (type) {
                case JSONType.JSON_TYPE_BOOLEAN:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append(field.getBoolean(object)).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_INT:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append(field.getInt(object)).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_LONG:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append(field.getLong(object)).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_FLOAT:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append(field.getFloat(object)).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_DOUBLE:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append(field.getDouble(object)).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_STRING:
                    sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                    sb.append(':').append('"').append(field.get(object)).append('"').append(i + 1 == count ? "" : ",");
                    break;
                default:
                    Object fieldInstance = field.get(object);
                    if (fieldInstance == null) {
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append("null").append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Map) {//map
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        mapStackLevel--;
                        sb.append(':').append(parseMap((Map) fieldInstance)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof List) {//list
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        this.listStackLevel--;
                        sb.append(':').append(parseList((List) fieldInstance)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Boolean) {//boolean
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Byte) {//byte
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Short) {//short
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Character) {//char
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Integer) {//int
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Long) {//long
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Float) {//float
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else if (fieldInstance instanceof Double) {//double
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        sb.append(':').append(field.get(object)).append(i + 1 == count ? "" : ",");
                    } else {
                        sb.append('"').append(mRule.keyRule(field.getName())).append('"');
                        objectStackLevel--;
                        sb.append(':').append(parseObject(fieldInstance)).append(i + 1 == count ? "" : ",");
                    }
                    break;
            }
        }
        //虽然在数据解析途中判断了,但是也很可能无效,因为解析途中根据的是字段的数量判断的
        //如果发生了字段无效,那么则判断也就无效了

        if (count > 0) {
            if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
            sb.append('}');
        }
        return sb;
    }


    /**
     * 将 map 转换为json字符串
     * <p/>
     * {key=list,key=object,key=map}
     *
     * @return 转换后的结果
     */
    public StringBuilder parseMap(Map map) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        if (mapStackLevel < 0) return sb;
        ArrayList keys = map == null ? new ArrayList() : new ArrayList(map.keySet());
        final int count = keys.size();
        sb.append('{');
        for (int i = 0; i < count; i++) {
            Object key = keys.get(i);
            Object value = map.get(key);
            if (value instanceof Map) {
                mapStackLevel--;
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(parseMap((HashMap) value));
            } else if (value instanceof List) {
                this.listStackLevel--;
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(parseList((List) value));
            } else if (value instanceof Integer) {//int
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(value);
            } else if (value instanceof Long) {//long
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(value);
            } else if (value instanceof Float) {//float
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(value);
            } else if (value instanceof Double) {//double
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(value);
            } else if (value instanceof Boolean) {//boolean
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(value);
            } else if (value instanceof String) {//String
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append('"').append(value).append('"');
            } else {
                objectStackLevel--;
                sb.append(i == 0 ? "" : ",").append('"').append(key).append('"').append(':').append(parseObject(value));
            }
        }
        sb.append('}');
        return sb;
    }
}
