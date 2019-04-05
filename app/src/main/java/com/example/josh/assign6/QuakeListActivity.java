package com.example.josh.assign6;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuakeListActivity extends AppCompatActivity {

    private static final String TAG = "QuakeListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_list);

        loadQuakes();

        registerQuakeHyperlinks();
    }

    private void loadQuakes() {
        Intent intent = getIntent();

        URLMaker maker = new URLMaker(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson");
        maker.add("&","limit", intent.getStringExtra("limit"));
        maker.add("&","starttime", intent.getStringExtra("starttime"));
        maker.add("&","minmagnitude", intent.getStringExtra("minmagnitude"));
        maker.add("&","orderby", intent.getStringExtra("orderby"));

        QuakeAsyncTask task = new QuakeAsyncTask(
                getApplicationContext(), findViewById(R.id.root),
                R.layout.quake_item, R.id.quake_list);
        task.execute(maker.make());
    }

    private void registerQuakeHyperlinks() {
        ListView list = findViewById(R.id.quake_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject item = (JSONObject) parent.getItemAtPosition(position);
                    JSONArray coords = item.getJSONObject("geometry").getJSONArray("coordinates");
                    URLMaker maker = new URLMaker("https://www.openstreetmap.org/?");
                    String lat = coords.get(1).toString();
                    String lon = coords.get(0).toString();
                    maker.add("&","mlat", lat);
                    maker.add("&","mlon",  lon);
                    maker.add("#", "map",  String.format("5/%s/%s", lat, lon));
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW, Uri.parse(maker.make()));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Log.e(TAG, "Error opening link: " + e.getMessage());
                }
            }
        });
    }
}
