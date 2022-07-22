package com.example.logtracker.basicActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import com.example.logtracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE}
                        , PackageManager.PERMISSION_GRANTED);

        if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }

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