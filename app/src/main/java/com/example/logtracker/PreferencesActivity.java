package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends AppCompatActivity {
    public static Button SaveBtn, ResetDB;
    EditText Name, email, hours;
    LogtrackerDBHandler flightsDBprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        flightsDBprefs = new LogtrackerDBHandler(this); //creates db obj

        SaveBtn = (Button) findViewById(R.id.Save_prefs_Button);
        ResetDB = (Button) findViewById(R.id.Reset_database_Button);
        ResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightsDBprefs.resetDB();
            }
        });
    }
}