package com.lovely3x.jsonparser.source;

import com.lovely3x.jsonparser.conversation.rule.JSONGenerateRule;
import com.lovely3x.jsonparser.conversation.utils.JavaObjectToJSONUtils;

import java.util.Map;

/**
 * Created by lovely3x on 15-7-2.
 * map json源
 */
public class MapJSONSource implements JSONSource {

    private final Map mObjectsMap;

    private String mSource;

    private final JSONGenerateRule rule;

    public MapJSONSource(Map map, JSONGenerateRule rule) {
        this.mObjectsMap = map;
        this.rule = rule;
    }

    @Override
    public String input() {
        try {
            mSource = new JavaObjectToJSONUtils(rule).parseMap(mObjectsMap).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mSource;
    }


}
