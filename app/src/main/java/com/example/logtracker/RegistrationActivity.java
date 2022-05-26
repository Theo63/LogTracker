package com.example.logtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
public static Button DateBtn, TimeBtn, RegisterBtn;
LogtrackerDBHandler flightsDB;
//public static ArrayList<Integer> dateSelected,timeSelected = new ArrayList<>();
//public static String aircraftType,typeofFlight, lightCond, flightRules,dutyonBoard, aircraftID = "";
//public static Integer landingsInput = 0 ;
EditText aircraftid, landings, arrival, destination;
Spinner type_of_aircraftSpinner,type_of_flightSpinner,light_condSpinner,flight_rulesSpinner,duty_on_boardSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        flightsDB = new LogtrackerDBHandler(this); //creates db obj
        aircraftid = (EditText) findViewById(R.id.aircraft_id_input);
        landings = (EditText) findViewById(R.id.landings_input);
        arrival = (EditText) findViewById(R.id.arrivalInput);
        destination = (EditText) findViewById(R.id.destinationInput);
        DateBtn = (Button) findViewById(R.id.datePicker);
        TimeBtn = (Button) findViewById(R.id.timePicker);
        RegisterBtn = (Button) findViewById(R.id.registerButton);
        Snackbar errorSnack = Snackbar.make(findViewById(R.id.registrationLayout),
                "Please fill all the fields", 1500);

        type_of_aircraftSpinner = (Spinner) findViewById(R.id.aircraft_type_spinner);
        type_of_flightSpinner = (Spinner) findViewById(R.id.type_of_flight_spinner);
        light_condSpinner = (Spinner) findViewById(R.id.light_conditions_spinner);
        flight_rulesSpinner = (Spinner) findViewById(R.id.flight_rules_spinner);
        duty_on_boardSpinner = (Spinner) findViewById(R.id.duty_on_board_spinner);

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

        //Register button functionality
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getInputValues();
                    boolean dataCompletion = flightLog.fieldCompletion();
                    if (dataCompletion) {
                                    // setting a warning dialog before resetting the database
                                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                                    alertDialog.setTitle("Logbook Warning");
                                    alertDialog.setMessage("Writting to logbook cannot be undone. Have you checked your flight data?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    boolean isInserted = flightsDB.addFlight();
                                                    if (isInserted) {
                                                        Toast.makeText(RegistrationActivity.this, "Flight Registered successfully", Toast.LENGTH_LONG).show();
                                                        resetActivity();
                                                    } else
                                                        Toast.makeText(RegistrationActivity.this, "Error in Flight RegistrationActivity", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"No",
                                                        new DialogInterface.OnClickListener()

                                                {
                                                    @Override
                                                    public void onClick (DialogInterface
                                                    dialogInterface,int i){
                                                    alertDialog.dismiss();
                                                }
                                                });
                                    alertDialog.show();

                    }else {
                        Toast.makeText(RegistrationActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    errorSnack.show();
                }

            }
        });
    }



///////////////// Date and time fragment section Look @ their classes too //////////////////////////
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
        //set the button to date selected
        DateBtn.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
        flightLog.setDateSelected(dates);//store date
    }

    // when a time is picked we change the time value of the button
    public static void setTimeButton(ArrayList<Integer> time){
        if (time.get(1)>=0 && time.get(1)<10){
            TimeBtn.setText(String.valueOf(time.get(0)+ " : 0"+ time.get(1)));
        }
        else{TimeBtn.setText(String.valueOf(time.get(0)+ " : "+ time.get(1)));}
        flightLog.setTimeSelected(time);//store time
    }



/////////////////////////////////// Spinners section /////////////////////////////////////////////
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.aircraft_type_spinner && !spinnerInput.equals("choose")){
            // first spinner selected
//            Toast.makeText(parent.getContext(),"first spinner" + spinnerInput, Toast.LENGTH_SHORT).show();
            flightLog.setAircraftType(spinnerInput); // set the type of aircraft
        }
        else if (parent.getId() == R.id.type_of_flight_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
//            Toast.makeText(parent.getContext(),"second spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
            flightLog.setTypeofFlight(spinnerInput);//set type of flight string
        }
        else if (parent.getId() == R.id.light_conditions_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
//            Toast.makeText(parent.getContext(),"third spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
            flightLog.setLightCond(spinnerInput); //set the light conditions
        }
        else if (parent.getId() == R.id.flight_rules_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
//            Toast.makeText(parent.getContext(),"fourth spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
            flightLog.setFlightRules(spinnerInput); //set the flight rules
        }
        else if (parent.getId() == R.id.duty_on_board_spinner && !spinnerInput.equals("choose")){
            // second spinner selected
//            Toast.makeText(parent.getContext(),"fifth spinner"+ spinnerInput, Toast.LENGTH_SHORT).show();
            flightLog.setDutyonBoard(spinnerInput); // set the duty on board
        }

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    //pick input values in register button
    private void getInputValues(){
        flightLog.setLandingsInput(Integer.parseInt(landings.getText().toString()));
        flightLog.setAircraftID(aircraftid.getText().toString());
        flightLog.setArrival(arrival.getText().toString());
        flightLog.setDestination(destination.getText().toString());
    }

    //reset object and layout after successful data entry
    private void resetActivity(){
        new flightLog(); //reset all object values
        DateBtn.setText("   /    /");
        TimeBtn.setText("   :");
        type_of_aircraftSpinner.setSelection(0);
        type_of_flightSpinner.setSelection(0);
        light_condSpinner.setSelection(0);
        flight_rulesSpinner.setSelection(0);
        duty_on_boardSpinner.setSelection(0);
        aircraftid.setText("");
        landings.setText("");
        arrival.setText("");
        destination.setText("");


    }


}