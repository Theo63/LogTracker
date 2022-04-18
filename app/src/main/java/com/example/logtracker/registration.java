package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class registration extends AppCompatActivity {
public static Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn = (Button) findViewById(R.id.datePicker);
    }











    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }
    public static void setButton(ArrayList<Integer> dates){
        btn.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
    }
}