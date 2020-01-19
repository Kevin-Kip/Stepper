package com.example.stepper.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlaceApi {
    public ArrayList<String> autoComplete(String input, String apiKey) {
        ArrayList<String> arrayList = new ArrayList<>();
        HttpURLConnection httpURLConnection = null;
//        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" +
//                "input=$name&key=$apiKey";

        StringBuilder jsonResult = new StringBuilder();

        try {
            StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            urlBuilder.append("input=" + input);
            urlBuilder.append("&key=" + apiKey);

            URL url = new URL(urlBuilder.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());

            int read;
            char[] buff = new char[1024];

            while ((read = inputStreamReader.read(buff)) != -1) {
                jsonResult.append(buff, 0, read);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray predictions = jsonObject.getJSONArray("predictions");
            for (int i = 0; i < predictions.length(); i++) {
                arrayList.add(predictions.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
