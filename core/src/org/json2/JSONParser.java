package org.json2;

import com.lovely3x.jsonparser.Config;
import com.lovely3x.jsonparser.model.JSONKey;
import com.lovely3x.jsonparser.model.JSONValue;
import com.lovely3x.jsonparser.source.JSONSource;

import java.util.*;

/**
 * json解析器
 * 使用org.json2 提供的源代码实现
 * Created by lovely3x on 16-1-26.
 */
public class JSONParser implements com.lovely3x.jsonparser.parser.JSONParser {

    private final Config mConfig;

    public JSONParser(Config config) {
        this.mConfig = config;
    }

    @Override
    public List<JSONValue> parseJSONArray(JSONSource source) {
        List<JSONValue> jsonValues = null;
        try {
            JSONArray ja = new JSONArray(source.input());
            List<Object> values = ja.getValues();
            jsonValues = new ArrayList<>(values.size());
            for (Object object : values) {
                jsonValues.add(mConfig.pairFactory.getJSONValue(JSON.toString(object)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonValues;
    }

    @Override
    public Map<JSONKey, JSONValue> parseJSONObject(JSONSource source) {
        HashMap<JSONKey, JSONValue> map = null;
        try {
            JSONObject jo = new JSONObject(source.input());
            Map<String, Object> nameValuePair = jo.getNameValuePairs();
            map = new HashMap<>(nameValuePair.size(), 1.0f);
            Set<String> keySet = nameValuePair.keySet();
            for (String key : keySet) {
                map.put(mConfig.pairFactory.getJSONKey(key), mConfig.pairFactory.getJSONValue(JSON.toString(nameValuePair.get(key))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
