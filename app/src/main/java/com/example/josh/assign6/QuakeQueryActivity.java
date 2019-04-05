package com.example.josh.assign6;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class QuakeQueryActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_query);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        loadStartDate();
        loadOrderBySpinner();

        registerStartDatePickerListener();
        registerSubmitListener();
    }

    private void loadStartDate() {
        TextView v = findViewById(R.id.start_date_text);
        v.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, day));
    }

    private void loadOrderBySpinner() {
        Spinner spinner = findViewById(R.id.orderby_mag_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.orderby_mag_item,
                new String[] {"magnitude", "date"});
        spinner.setAdapter(adapter);
    }

    private void registerStartDatePickerListener() {
        Button btn = findViewById(R.id.start_date_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog picker = new DatePickerDialog(
                        QuakeQueryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int _year, int _month, int dayOfMonth) {
                                year = _year;
                                month = _month;
                                day = dayOfMonth;
                                loadStartDate();
                            }
                        },
                        year,
                        month,
                        day
                );
                picker.show();
            }
        });
    }

    private void registerSubmitListener() {
        Button btn = findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText limit = findViewById(R.id.num_of_quakes_edit);
                Spinner orderby = findViewById(R.id.orderby_mag_spinner);
                TextView starttime = findViewById(R.id.start_date_text);

                Intent intent = new Intent(
                        QuakeQueryActivity.this, QuakeListActivity.class);

                String s = (String) orderby.getSelectedItem();
                intent.putExtra("minmagnitude", "7.0");
                intent.putExtra("limit", limit.getText().toString());
                intent.putExtra("orderby", (s == "date" ? "time" : s));
                intent.putExtra("starttime", starttime.getText());

                startActivity(intent);
            }
        });
    }
}
