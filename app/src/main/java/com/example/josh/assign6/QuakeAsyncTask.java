package com.example.josh.assign6;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;


class QuakeAsyncTask extends AsyncTask<String, Void, List<JSONObject>> {

    private Context context;
    private View root;
    private int layoutid;
    private int listid;

    public QuakeAsyncTask(Context context, View root, int layoutid, int listid) {
        this.context = context;
        this.root = root;
        this.layoutid = layoutid;
        this.listid  = listid;
    }

    @Override
    protected List<JSONObject> doInBackground(String... stringurl) {
        return Utils.fetchEarthquakeData(stringurl[0]);
    }

    public void onPostExecute(@Nullable List<JSONObject> postExecuteResult) {
        if (postExecuteResult != null) {
            QuakeAdapter results = new QuakeAdapter(
                    context, layoutid, postExecuteResult);
            ListView view = root.findViewById(listid);
            view.setAdapter(results);
        }
    }
}