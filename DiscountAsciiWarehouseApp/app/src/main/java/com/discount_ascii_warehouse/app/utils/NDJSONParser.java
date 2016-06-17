package com.discount_ascii_warehouse.app.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by mariobraga on 6/13/16.
 */
public class NDJSONParser {

    public static  <T extends Object> List<T> parse(String ndjson, Class<T> anyClass) {

        List<T> list = new ArrayList<>();

        try {
            String[] listJson = ndjson.split("\n");

            if (listJson != null && !"".equals(listJson[0])) {

                Gson gson = new Gson();

                for (int i = 0; i < listJson.length; i++) {
                    String json = listJson[i];
                    T object = gson.fromJson(json, anyClass);
                    list.add(object);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return list;
    }
}
