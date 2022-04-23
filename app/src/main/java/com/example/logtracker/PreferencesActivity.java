package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Attributes;

public class PreferencesActivity extends AppCompatActivity {
    public static Button SaveBtn, ResetDB;
    EditText NameInput, email, hours;
    public CheckBox rememberCheckbox;

    LogtrackerDBHandler flightsDBprefs;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        flightsDBprefs = new LogtrackerDBHandler(this); //creates db obj
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedpreferences.edit();

        NameInput = (EditText) findViewById(R.id.NameInput);
        email = (EditText) findViewById(R.id.mailInput);
        hours = (EditText) findViewById(R.id.StartingHoursInput);
        rememberCheckbox = (CheckBox) findViewById(R.id.rememberMeCheckbox);

        checkSharedPreferences();

        SaveBtn = (Button) findViewById(R.id.Save_prefs_Button);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rememberCheckbox.isChecked()){
                    // set checkbox when app starts
                    editor.putString(getString(R.string.Checkbox),"True");
                    editor.commit();
                    //store name mail and hours
                    String na = NameInput.getText().toString();
                    editor.putString(getString(R.string.Name),na);
                    editor.commit();

                    String em = email.getText().toString();
                    editor.putString(getString(R.string.Email),em);
                    editor.commit();

                    String ho = hours.getText().toString();
                    editor.putString(getString(R.string.Hours),ho);
                    editor.commit();


                }else{
                    // set checkbox when app starts
                    editor.putString(getString(R.string.Checkbox),"False");
                    editor.commit();
                    //store name mail and hours
                    editor.putString(getString(R.string.Name),"");
                    editor.commit();

                    editor.putString(getString(R.string.Email),"");
                    editor.commit();

                    editor.putString(getString(R.string.Hours),"");
                    editor.commit();

                }
                String em = email.getText().toString();
                Integer ho = Integer.parseInt(hours.getText().toString());

                editor.commit();
                Toast.makeText(PreferencesActivity.this, " Your contact info is now stored", Toast.LENGTH_LONG).show();
            }
        });

        ResetDB = (Button) findViewById(R.id.Reset_database_Button);
        ResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightsDBprefs.resetDB();
            }
        });
    }

    private void checkSharedPreferences(){
        String Checkbox = sharedpreferences.getString(getString(R.string.Checkbox), "False");
        String nameCheck = sharedpreferences.getString(getString(R.string.Name), "");
        String mailCheck = sharedpreferences.getString(getString(R.string.Email), "");
        String hoursCheck = sharedpreferences.getString(getString(R.string.Hours), "");

        NameInput.setText(nameCheck);
        email.setText(mailCheck);
        hours.setText(hoursCheck);

        if(Checkbox.equals("True")){
            rememberCheckbox.setChecked(true);
        }else{
            rememberCheckbox.setChecked(false);
        }



    }
}