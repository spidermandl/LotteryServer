package com.evstudio.lottery.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eric on 15/1/13.
 */


public class HttpInvoker {


    public static final String GET_URL = "http://api.map.baidu.com/geocoder/v2/?ak=67af81f989735445e5772e87d91e517f&output=json&pois=0";


    public static final String POST_URL = "http://api.map.baidu.com/geocoder/v2/?ak=67af81f989735445e5772e87d91e517f&output=json&pois=0&location=31.150681,121.1241781";


    public static String getAddressByLocation(String lati, String lng) throws IOException {

        StringBuffer stringBuffer = new StringBuffer();
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        String getURL = GET_URL + "&location=" + lati + "," + lng;
        URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        // 建立与服务器的连接，并未发送数据
        connection.connect();
        // 发送数据到服务器并使用Reader读取返回的数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines;
        while ((lines = reader.readLine()) != null) {
            stringBuffer.append(lines);
        }
        reader.close();
        // 断开连接
        connection.disconnect();

        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        try {
            String strJson = HttpInvoker.getAddressByLocation("31.197739341358194","121.50825575916464");
            System.out.println( strJson );
            JsonParser jsonparer = new JsonParser();
            JsonObject jsonObject =  jsonparer.parse(strJson).getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("result");
            JsonElement jsonElement = jsonObject.get("formatted_address");
            strJson = jsonElement.getAsString();
            System.out.println(strJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}