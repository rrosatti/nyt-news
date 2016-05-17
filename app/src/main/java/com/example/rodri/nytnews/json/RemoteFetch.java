package com.example.rodri.nytnews.json;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rodri on 5/8/2016.
 */
public class RemoteFetch {

    private static final String NYT_KEY = "0a3792df6de44dbcac1814bdd51cc074";
    private static final String SEARCH_NYTIMES_API =
            "http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=" + NYT_KEY;

    /**
     *
     * Items from the parameters vector:
     * p[0] = query
     * p[1] = page number
     * p[2] = sort
     *
     * @param parameters
     * @return
     */
    public JSONObject getJSON(String[] parameters) {
        String stringURL = SEARCH_NYTIMES_API;

        stringURL  += "&q=" + parameters[0];
        if (!parameters[1].equals(""))
            stringURL += "&page=" + parameters[1];
        if (!parameters[2].equals(""))
            stringURL += "&sort=" + parameters[2];

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

            if (data == null) {
                return null;
            }

            return data;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
