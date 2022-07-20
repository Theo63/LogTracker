package com.example.logtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.jar.Attributes;

public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Button SaveBtn, ResetStartingHours, DisplayTotalHours, ResetDB, ExportDB;
    EditText startingHours;
    Spinner aircraftSpinner;
    String aircraft;
    Integer pos1;

    HashMap<String, Integer> totalHours;
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

        aircraftSpinner = (Spinner) findViewById(R.id.aircraft_pref_spinner1);

        // Create an ArrayAdapter using the string array and a default spinner layout for ALL Spinners
        ArrayAdapter<CharSequence> aircraft_adapter = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, android.R.layout.simple_spinner_item); //Aircraft_types is in res/values/strings.xml

        // Specify the layout to use when the list of choices appears
        aircraft_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        aircraftSpinner.setAdapter(aircraft_adapter);
        aircraftSpinner.setOnItemSelectedListener(this);

        startingHours = (EditText) findViewById(R.id.startingHours);



        //save starting hours for each type
        SaveBtn = (Button) findViewById(R.id.Save_prefs_Button);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //store name mail and hours

                String hours1 = startingHours.getText().toString();
                boolean isInserted = flightsDBprefs.addStartingHours(aircraft,hours1);
                if (isInserted) {
                    Toast.makeText(PreferencesActivity.this, " Your starting hours info is now stored", Toast.LENGTH_LONG).show();
                    aircraftSpinner.setSelection(0);
                    startingHours.setText("");
                } else
                    Toast.makeText(PreferencesActivity.this, "Starting hours already registered for this type", Toast.LENGTH_LONG).show();

            }
        });

        //reset button for starting hours
        ResetStartingHours = (Button) findViewById(R.id.resetStrartingHours);
        ResetStartingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting a warning dialog before resetting the database
                AlertDialog alertDialog = new AlertDialog.Builder(PreferencesActivity.this).create();
                alertDialog.setTitle("Starting Hours Reset Warning !");
                alertDialog.setMessage("ALL starting hours will be reset. Are you sure ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                flightsDBprefs.resetStartingHoursTable(); // database reset after pressing ok
                                Toast.makeText(PreferencesActivity.this,"Starting Hours Cleared",Toast.LENGTH_LONG).show();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        DisplayTotalHours = (Button) findViewById(R.id.displayTotalHours);
        DisplayTotalHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalHours = flightsDBprefs.getTotalHours();

            }
        });





        ResetDB = (Button) findViewById(R.id.Reset_database_Button);
        ResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting a warning dialog before resetting the database
                AlertDialog alertDialog = new AlertDialog.Builder(PreferencesActivity.this).create();
                alertDialog.setTitle("Database Reset Warning !");
                alertDialog.setMessage("ALL data will be reset. Are you sure ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                flightsDBprefs.resetDB(); // database reset after pressing ok
                                Toast.makeText(PreferencesActivity.this,"Database is now empty",Toast.LENGTH_LONG).show();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                });
                alertDialog.show();
            }
        });

        ExportDB = (Button) findViewById(R.id.DB_exportButton);
        ExportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String path=flightsDBprefs.writeFlightsToExcel();
                Toast.makeText(PreferencesActivity.this,"FLights.xls is in your internal storage home directory" ,Toast.LENGTH_LONG).show();
            }
        });
    }



    //////spinners ////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.aircraft_pref_spinner1 && !spinnerInput.equals("choose")){
            // first spinner selected
//            Toast.makeText(parent.getContext(),"first spinner" + spinnerInput, Toast.LENGTH_SHORT).show();
            pos1= pos;
            aircraft=spinnerInput; // set the type of aircraft
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}