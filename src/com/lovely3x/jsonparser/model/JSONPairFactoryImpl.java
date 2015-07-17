package com.lovely3x.jsonparser.model;

import com.lovely3x.jsonparser.source.JSONSource;

/**
 * json 对工厂
 * Created by lovely3x on 15-6-29.
 */
public class JSONPairFactoryImpl implements JSONPairFactory {

    @Override
    public JSONValue getJSONValue(String value) {
        return new JSONValueImpl(value);
    }

    @Override
    public JSONKey getJSONKey(String key) {
        return new JSONKeyImpl(key);
    }

    @Override
    public JSONValue getJSONValue(JSONSource value) {
        return getJSONValue(value.input());
    }

    @Override
    public JSONKey getJSONKey(JSONSource key) {
        return getJSONKey(key.input());
    }
}
