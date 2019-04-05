package com.example.josh.assign6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class QuakeAdapter extends ArrayAdapter<JSONObject> {

    private static final String TAG = "QuakeAdapter";

    private int layoutid;

    private static class ViewHolder {
        TextView quake_item_text;
    }

    QuakeAdapter(Context context, int layoutid, List<JSONObject> items) {
        super(context, layoutid, items);
        this.layoutid = layoutid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).
                    inflate(layoutid, parent, false);
            holder.quake_item_text = convertView.findViewById(R.id.quake_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject item = getItem(position);
        if (item != null) {
            String text = "";
            try {
                JSONObject prop = item.getJSONObject("properties");
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
                text = String.format(
                        "<b>%s</b>\n%s",
                        prop.getString("title"),
                        sdf.format(new Date(prop.getLong("time"))));
                holder.quake_item_text.setBackgroundResource(
                        (prop.getDouble("mag") >= 7.5) ? R.color.red : R.color.orange);
            }
            catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
            holder.quake_item_text.setText(Html.fromHtml(text));

        }
        return convertView;
    }

}
