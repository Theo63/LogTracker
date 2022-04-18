package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registrationForm(View view){
        //create intent to start registration activity
        Intent intentReg = new Intent(this, registration.class);

        //ask android to start registration activity as a subactivity
        startActivity(intentReg);
    }
}