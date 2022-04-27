package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));

    }

    public void registrationForm(View view){
        //create intent to start RegistrationActivity activity
        Intent intentReg = new Intent(MainActivity.this, RegistrationActivity.class );

        //ask android to start RegistrationActivity activity as a subactivity
        startActivity(intentReg);
    }

    public void preferencesForm(View view){
        Intent intentPrefs = new Intent(MainActivity.this, PreferencesActivity.class );
        startActivity(intentPrefs);
    }

    public void searchForm(View view){
        Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intentSearch);
    }
}