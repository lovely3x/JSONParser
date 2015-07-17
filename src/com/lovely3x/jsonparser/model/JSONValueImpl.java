package com.lovely3x.jsonparser.model;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.source.JSONSourcImpl;

/**
 * json对 的值
 * Created by lovely3x on 15-6-29.
 */
public class JSONValueImpl implements JSONValue {
    /**
     * 保存value
     */
    private String value;


    /**
     * 通过指定的值创建JSONValue对象
     *
     * @param value
     */
    public JSONValueImpl(String value) {
        this.value = value;
    }

    /**
     * 获取value
     *
     * @return
     */
    public String getValue() {
        return value.trim();
    }

    /**
     * 设置value
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 简单的处理key 因为原始的key是包含 '"' 和 '\' 符号的
     *
     * @return
     */
    private String processString(String key) {
        StringBuilder sb = new StringBuilder(key);
        if (key.startsWith("\"") && key.endsWith("\"")) {
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public JSONArray getJSONArray() {
        return new JSONArray(new JSONSourcImpl(value));
    }

    @Override
    public JSONObject getJSONObject() {
        return new JSONObject(new JSONSourcImpl(value));
    }

    @Override
    public String getString() {
        return processString(value);
    }

    /**
     * 简单的过滤
     *
     * @param originalString
     * @return
     */
    private String filter(String originalString) {
        if (originalString == null) return null;
        return originalString.trim();
    }

    @Override
    public int getInt() {
        if (isNull()) {
            return 0;
        }
        return Integer.parseInt(filter(value));
    }

    @Override
    public int getCastInt() {
        int result = -1;
        int type = guessType();
        switch (type) {
            case JSONType.JSON_TYPE_LONG:
                long lValue = getLong();
                result = (int) lValue;
                break;
            case JSONType.JSON_TYPE_INT:
                result = getInt();
                break;
            case JSONType.JSON_TYPE_DOUBLE:
            case JSONType.JSON_TYPE_FLOAT:
                double dValue = getDouble();
                result = (int) dValue;
                break;
        }
        return result;
    }

    @Override
    public double getDouble() {
        if (isNull()) {
            return 0.0D;
        }
        return Double.parseDouble(filter(value));
    }

    @Override
    public float getFloat() {
        if (isNull()) {
            return 0.0F;
        }
        return Float.parseFloat(filter(value));

    }

    @Override
    public boolean getBoolean() {
        if (isNull()) {
            return false;
        }
        return Boolean.parseBoolean(filter(value));
    }

    @Override
    public long getLong() {
        if (isNull()) {
            return 0;
        }
        return Long.parseLong(filter(value));
    }

    /**
     * 判断指定的这个字符串是否为null
     *
     * @return
     */
    private boolean isNull() {
        return value == null || "null".equals(value);
    }


    @Override
    public int guessType() {
        String value = filter(this.value);
        int type = JSONType.JSON_TYPE_INVALIDATE;
        //无效的值
        if (value == null || value.trim().length() == 0) {
            return type;
        }
        if (value.startsWith("[") && value.endsWith("]")) {//json array
            type = JSONType.JSON_TYPE_ARRAY;
        } else if (value.startsWith("{") && value.endsWith("}")) {//json object
            type = JSONType.JSON_TYPE_OBJECT;
        } else if (value.startsWith("\"") && value.endsWith("\"")) {//string
            type = JSONType.JSON_TYPE_STRING;
        } else if (value.matches("^[\\+|\\-]?\\d+\\.\\d+([e|E]{1}[\\+|\\-]?\\d+)?$")) {//浮点数匹配
            if (Double.parseDouble(value) > Float.MAX_VALUE / 2) {
                type = JSONType.JSON_TYPE_DOUBLE;//double float
            } else {
                type = JSONType.JSON_TYPE_FLOAT;//single float
            }
        } else if (value.matches("^[\\+|\\-]?\\d+$")) {//整形匹配
            if (Long.parseLong(value) > Integer.MAX_VALUE) {
                type = JSONType.JSON_TYPE_LONG;//long int
            } else {
                type = JSONType.JSON_TYPE_INT;// int
            }
        } else if (value.matches("^[true|false]$")) {
            type = JSONType.JSON_TYPE_BOOLEAN;
        } else if (value.matches("^null$")) {
            type = JSONType.JSON_TYPE_NULL;//null
        }
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
