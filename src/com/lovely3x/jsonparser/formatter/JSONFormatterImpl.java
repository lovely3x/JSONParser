package com.lovely3x.jsonparser.formatter;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.model.JSONArray;
import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.model.JSONValue;

import java.util.ArrayList;

/**
 * Created by lovely3x on 15-7-2.
 * json格式化实现类
 */
public class JSONFormatterImpl implements JSONFormatter {

    /**
     * 缩进符号
     */
    private String block;
    /**
     * 换行符符号
     */
    private String newLine;

    /**
     * 使用默认的换行符和指定的缩进符号进行创建格式化对象
     *
     * @param block 缩进符号
     */
    public JSONFormatterImpl(String block) {
        this(block, "\n");
    }

    /**
     * 使用指定的的换行符和指定的缩进符号进行创建格式化对象
     *
     * @param block   缩进符号
     * @param newLine 换行符号
     */
    public JSONFormatterImpl(String block, String newLine) {
        this.block = block;
        this.newLine = newLine;
    }

    /**
     * 使用默认的四个空格键作为分隔符号和默认的换行符号
     */
    public JSONFormatterImpl() {
        this("    ");
    }

    @Override
    public CharSequence formatJSONObject(JSONObject object) {
        return new StringBuilder().append("{").append(formatJSONObject(object, 0)).append("\n}");
    }

    /**
     * 格式化jsonObject
     *
     * @param object 需要格式化的jsonObject
     * @param level  本次格式化的内容为第几级别
     * @return 本级别格式化好的字符流
     */
    public CharSequence formatJSONObject(JSONObject object, int level) {
        StringBuilder sb = new StringBuilder();
        ArrayList<JSONKey> keys = new ArrayList<>(object.keySet());
        final int count = keys.size();
        for (int i = 0; i < count; i++) {
            JSONKey key = keys.get(i);
            JSONValue value = object.getValue(key);
            switch (value.guessType()) {
                case JSONType.JSON_TYPE_ARRAY://array
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('"').append(key.getKey()).append('"').append(' ').append(':').append(' ').append('[');
                    sb.append(formatJSONArray(object.getJSONArray(key), level + 1));
                    sb.append(newLine).append(addSpace(level + 1)).append(']').append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_OBJECT://object
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('"').append(key.getKey()).append('"').append(' ').append(':').append(' ').append('{');
                    sb.append(formatJSONObject(object.getJSONObject(key), level + 1));
                    sb.append(newLine).append(addSpace(level + 1)).append('}').append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_BOOLEAN://boolean
                case JSONType.JSON_TYPE_INT://int
                case JSONType.JSON_TYPE_LONG://long
                case JSONType.JSON_TYPE_FLOAT://float
                case JSONType.JSON_TYPE_DOUBLE://double
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('"').append(key.getKey()).append('"').append(' ').append(':').append(' ');
                    sb.append(value.getString()).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_STRING://
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('"').append(key.getKey()).append('"').append(' ').append(':').append(' ').append('"').append(value.getString()).append('"').append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_NULL://null
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('"').append(key.getKey()).append('"').append(' ').append(':').append(' ').append("null").append(i + 1 == count ? "" : ",");
                    break;
            }
        }
        return sb;
    }

    /**
     * 添加空白格子
     *
     * @param level 第几级
     * @return 添加的空白格子
     */
    private StringBuilder addSpace(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(block);
        }
        return sb;
    }

    @Override
    public CharSequence formatJSONArray(JSONArray array) {
        return new StringBuilder().append("[" + formatJSONArray(array, 0)).append("\n]");
    }

    /**
     * 格式化jsonArray
     *
     * @param array 需要格式化的jsonArray
     * @param level 本次格式化的内容为第几级别
     * @return 本级别格式化好的字符流
     */
    public CharSequence formatJSONArray(JSONArray array, int level) {
        StringBuilder sb = new StringBuilder();
        final int count = array.length();
        for (int i = 0; i < count; i++) {
            JSONValue value = array.getValue(i);
            switch (value.guessType()) {
                case JSONType.JSON_TYPE_ARRAY://array
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('[').append(formatJSONArray(value.getJSONArray(), level + 1)).append(newLine).append(addSpace(level + 1)).append(']')
                            .append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_OBJECT://object
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append('{').append(formatJSONObject(value.getJSONObject(), level + 1)).append(newLine)
                            .append(addSpace(level + 1)).append('}').append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_BOOLEAN://boolean
                case JSONType.JSON_TYPE_INT://int
                case JSONType.JSON_TYPE_LONG://long
                case JSONType.JSON_TYPE_FLOAT://float
                case JSONType.JSON_TYPE_DOUBLE://double
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append(value.getString()).append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_STRING://
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append(value.getString()).append('"').append(i + 1 == count ? "" : ",");
                    break;
                case JSONType.JSON_TYPE_NULL://null
                    sb.append(newLine);
                    sb.append(addSpace(level)).append(block);
                    sb.append("null").append(i + 1 == count ? "" : ",");
                    break;
            }
        }
        return sb;
    }
}
