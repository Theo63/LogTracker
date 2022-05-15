package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Button DateBtn, RegisterBtn;
    LogtrackerDBHandler flightsDB;
//    Snackbar errorSnack = Snackbar.make(findViewById(R.id.registrationLayout);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        DateBtn = (Button) findViewById(R.id.datePicker);

    }
//
//    RegisterBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            try {
//                getInputValues();
//                System.out.println(flightLog.getDateSelected()+"\n"+flightLog.getAircraftType()+"\n"+flightLog.getAircraftID()+"\n"+flightLog.getLandingsInput()+"\n"+
//                        flightLog.getTimeSelected()+"\n"+flightLog.getLightCond()+"\n"+flightLog.getFlightRules()+"\n"+flightLog.getDutyonBoard());
//                boolean dataCompletion = flightLog.fieldCompletion();
//                if (dataCompletion){
//                    boolean isInserted = flightsDB.addFlight();
//                    if (isInserted) {
//                        Toast.makeText(RegistrationActivity.this, "Flight Registered successfully", Toast.LENGTH_LONG).show();
//                        resetActivity();
//                    }
//
//                    else
//                        Toast.makeText(RegistrationActivity.this,"Error in Flight RegistrationActivity",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(RegistrationActivity.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
//                }
//
//            }
//            catch(Exception e) {
//                errorSnack.show();
//            }
//
//        }
//
//    }
//        );

    public void showPickerDialog(View v) {
//        if (v.getId()== R.id.datePicker){
            DatePickerFragment newDateFragment = new DatePickerFragment();
            newDateFragment.show(getSupportFragmentManager(), "datePicker");
//        }
//        else{
//            TimePickerFragment newTimeFragment = new TimePickerFragment();
//            newTimeFragment.show(getSupportFragmentManager(), "timePicker");
//        }

    }

    //when a date is picket we change the value of the button
    public static void setDateButton(ArrayList<Integer> dates){
        //set the button to date selected
        DateBtn.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
        flightLog.setDateSelected(dates);//store date
    }

    private void resetActivity(){
        new flightLog(); //reset all object values
        DateBtn.setText("   /    /");
//        TimeBtn.setText("   :");



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}