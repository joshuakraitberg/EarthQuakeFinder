package com.example.josh.assign6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.charset.Charset;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utils {

    private static final String TAG = "Utils";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static List<JSONObject> fetchEarthquakeData(String url) {
        try {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                JSONArray a = json.getJSONArray("features");
                ArrayList<JSONObject> l = new ArrayList<>();
                for (int i=0;i<a.length();++i) { l.add(a.getJSONObject(i)); }
                return l;

            } catch (JSONException e) {
                Log.e(TAG, "JSONException: " + e.getMessage());
            } finally {
                is.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }

        return null;
    }
}
