package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
public static Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        btn = (Button) findViewById(R.id.datePicker);

        Spinner type_of_aircraftSpinner = (Spinner) findViewById(R.id.type_spinner);
        Spinner type_of_flightSpinner = (Spinner) findViewById(R.id.type_spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> type_of_aircraftadapter = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, android.R.layout.simple_spinner_item); //Aircraft_types is in res/values/strings.xml

        ArrayAdapter<CharSequence> type_of_flightadapter = ArrayAdapter.createFromResource(this,
                R.array.Flight_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        type_of_aircraftadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        type_of_flightadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type_of_aircraftSpinner.setAdapter(type_of_aircraftadapter);
        type_of_aircraftSpinner.setOnItemSelectedListener(this);

        type_of_flightSpinner.setAdapter(type_of_flightadapter);
        type_of_flightSpinner.setOnItemSelectedListener(this);

    }



    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }
    public static void setButton(ArrayList<Integer> dates){
        btn.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
    }



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.type_spinner && !spinnerInput.equals("choose")){
            // first spinner selected
            Toast.makeText(parent.getContext(),"first spinner" + spinnerInput, Toast.LENGTH_SHORT).show();
        } else if (parent.getId() == R.id.type_spinner2 && !spinnerInput.equals("choose")){
            // second spinner selected
            Toast.makeText(parent.getContext(),"second spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



}