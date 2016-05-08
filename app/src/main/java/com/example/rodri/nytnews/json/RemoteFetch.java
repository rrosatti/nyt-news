package com.example.rodri.nytnews.json;

import com.example.rodri.nytnews.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rodri on 5/8/2016.
 */
public class RemoteFetch {

    private static final String NYT_KEY = "123456";
    private static final String SEARCH_NYTIMES_API =
            "http://api.nytimes.com/svc/search/v1/article?format=json&&api-key=" + NYT_KEY;

    public JSONObject getJSON(String query) {
        String stringURL = SEARCH_NYTIMES_API;
        stringURL  += "&q=" + query;

        try {

            URL url = new URL(stringURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String temp = "";
            while((temp = reader.readLine()) != null) {
                json.append(temp).append("\n");
            }
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if (data.getJSONObject("response").getJSONObject("meta").getInt("code") != 200) {
                return null;
            }

            return data;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
