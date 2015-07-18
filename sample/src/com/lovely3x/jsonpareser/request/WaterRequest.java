package com.lovely3x.jsonpareser.request;

import com.lovely3x.jsonpareser.bean.Index;
import com.lovely3x.jsonpareser.bean.Response;
import com.lovely3x.jsonpareser.bean.Water;
import com.lovely3x.jsonparser.model.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by lovely3x on 15-7-18.
 * 这个接口主要用来获取天气信息
 */
public class WaterRequest {
    public static String url = "http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=B122767f9cf32ad2c5a17d97835d053e";

    public Response getWaterInfo(String location) {
        StringBuilder sb = new StringBuilder();
        Response res = new Response();
        try {
            HttpURLConnection connect = (HttpURLConnection) new URL(String.format(url, location)).openConnection();
            connect.setRequestMethod("GET");
            connect.connect();
            InputStream is = connect.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jo = new JSONObject(sb.toString());
            res.errorCode = jo.getInt("error");
            res.isSucessful = res.errorCode == 0;
            if (res.isSucessful) {
                jo = jo.getJSONArray("results").getJSONOObject(0);
                List<Water> waters = jo.getJSONArray("weather_data").createObjects(Water.class);
                List<Index> indexs = jo.getJSONArray("index").createObjects(Index.class);
                res.obj = new Object[]{waters, indexs};
            }


        } catch (Exception e) {
            e.printStackTrace();
            res.isSucessful = false;
        }

        return res;
    }
}
