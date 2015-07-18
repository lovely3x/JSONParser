package com.lovely3x.jsonpareser.request;

import com.lovely3x.jsonpareser.bean.Person;
import com.lovely3x.jsonpareser.bean.Response;
import com.lovely3x.jsonparser.matcher.AnnotationMatcher;

/**
 * Created by lovely3x on 15-7-18.
 */
public class UserRequest {

    public Response getUser() {
        Response res = new Response();
        res.isSucessful = true;
        com.lovely3x.jsonparser.model.JSONObject jo = new com.lovely3x.jsonparser.model.JSONObject(buildJSON());
        Person person = jo.createObject(Person.class, new AnnotationMatcher());
        res.obj = person;
        return res;

    }

    private String buildJSON() {
        String str = "{\n" +
                "    \"id\" : 1001,\n" +
                "    \"sex\" : 0,\n" +
                "    \"friends\" : [{\n" +
                "        \"id\" : 1002,\n" +
                "        \"sex\" : 1,\n" +
                "        \"friends\" : [\n" +
                "        ],\n" +
                "        \"name\" : \"Jennifer\"\n" +
                "    },{\n" +
                "        \"id\" : 1003,\n" +
                "        \"sex\" : 1,\n" +
                "        \"friends\" : [\n" +
                "        ],\n" +
                "        \"name\" : \"Jennifer\"\n" +
                "    },{\n" +
                "        \"id\" : 1004,\n" +
                "        \"sex\" : 1,\n" +
                "        \"friends\" : [\n" +
                "        ],\n" +
                "        \"name\" : \"Jennifer\"\n" +
                "    }\n" +
                "    ],\n" +
                "    \"name\" : \"Lovely3x\",\n" +
                "    \"partner\" : {\n" +
                "        \"id\" : 1002,\n" +
                "        \"sex\" : 1,\n" +
                "        \"friends\" : [\n" +
                "        ],\n" +
                "        \"name\" : \"Jennifer\"\n" +
                "    }\n" +
                "}";
        return str;
    }
}
