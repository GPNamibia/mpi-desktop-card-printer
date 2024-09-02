package com.mpi.main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class ConfigReader {
    private String url;

    public ConfigReader() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("config.json"));
            JSONObject jsonObject = (JSONObject) obj;
            url = (String) jsonObject.get("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }
}