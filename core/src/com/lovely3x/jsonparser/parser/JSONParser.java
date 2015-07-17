package com.lovely3x.jsonparser.parser;

import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.JSONValue;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lovely3x on 15-6-29.
 * json解析器
 */
public interface JSONParser {
    /**
     * 解析json数组
     *
     * @return
     */
    List<JSONValue> parseJSONArray();

    /**
     * 解析json对象
     *
     * @return
     */
    HashMap<JSONKey, JSONValue> parseJSONObject();


}
