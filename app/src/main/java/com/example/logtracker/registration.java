package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
public static Button DateBtn, TimeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        DateBtn = (Button) findViewById(R.id.datePicker);
        TimeBtn = (Button) findViewById(R.id.timePicker);

        Spinner type_of_aircraftSpinner = (Spinner) findViewById(R.id.aircraft_type_spinner);
        Spinner type_of_flightSpinner = (Spinner) findViewById(R.id.type_of_flight_spinner);
        Spinner light_condSpinner = (Spinner) findViewById(R.id.light_conditions_spinner);
        Spinner flight_rulesSpinner = (Spinner) findViewById(R.id.flight_rules_spinner);
        Spinner duty_on_boardSpinner = (Spinner) findViewById(R.id.duty_on_board_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout for ALL Spinners
        ArrayAdapter<CharSequence> type_of_aircraftadapter = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, android.R.layout.simple_spinner_item); //Aircraft_types is in res/values/strings.xml

        ArrayAdapter<CharSequence> type_of_flightadapter = ArrayAdapter.createFromResource(this,
                R.array.Flight_types, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> light_condadapter = ArrayAdapter.createFromResource(this,
                R.array.Light_Conditions, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> flight_rulesadapter = ArrayAdapter.createFromResource(this,
                R.array.Flight_rules, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> duty_on_boardadapter = ArrayAdapter.createFromResource(this,
                R.array.Duty_on_Board, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        type_of_aircraftadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_of_flightadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        light_condadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flight_rulesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duty_on_boardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        type_of_aircraftSpinner.setAdapter(type_of_aircraftadapter);
        type_of_aircraftSpinner.setOnItemSelectedListener(this);

        type_of_flightSpinner.setAdapter(type_of_flightadapter);
        type_of_flightSpinner.setOnItemSelectedListener(this);

        light_condSpinner.setAdapter(light_condadapter);
        light_condSpinner.setOnItemSelectedListener(this);

        flight_rulesSpinner.setAdapter(flight_rulesadapter);
        flight_rulesSpinner.setOnItemSelectedListener(this);

        duty_on_boardSpinner.setAdapter(duty_on_boardadapter);
        duty_on_boardSpinner.setOnItemSelectedListener(this);

    }



    public void showPickerDialog(View v) {
        if (v.getId()== R.id.datePicker){
            DatePickerFragment newDateFragment = new DatePickerFragment();
            newDateFragment.show(getSupportFragmentManager(), "datePicker");
        }
        else{
            TimePickerFragment newTimeFragment = new TimePickerFragment();
            newTimeFragment.show(getSupportFragmentManager(), "timePicker");
        }

    }


    //when a date is picket we change the value of the button
    public static void setDateButton(ArrayList<Integer> dates){
        DateBtn.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
    }
    // when a time is picked we change the time value of the button
    public static void setTimeButton(ArrayList<Integer> time){
        if (time.get(1)>=0 && time.get(1)<10){
            TimeBtn.setText(String.valueOf(time.get(0)+ " : 0"+ time.get(1)));
        }
        else{TimeBtn.setText(String.valueOf(time.get(0)+ " : "+ time.get(1)));}
    }



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.aircraft_type_spinner && !spinnerInput.equals("choose")){
            // first spinner selected
            Toast.makeText(parent.getContext(),"first spinner" + spinnerInput, Toast.LENGTH_SHORT).show();
        }
        else if (parent.getId() == R.id.type_of_flight_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
            Toast.makeText(parent.getContext(),"second spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
        }
        else if (parent.getId() == R.id.light_conditions_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
            Toast.makeText(parent.getContext(),"third spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
        }
        else if (parent.getId() == R.id.flight_rules_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
            Toast.makeText(parent.getContext(),"fourth spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
        }
        else if (parent.getId() == R.id.duty_on_board_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
            Toast.makeText(parent.getContext(),"fifth spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



}