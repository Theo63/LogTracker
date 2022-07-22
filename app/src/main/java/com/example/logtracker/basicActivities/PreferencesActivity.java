package com.example.logtracker.basicActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logtracker.LogtrackerDBHandler;
import com.example.logtracker.R;
import com.example.logtracker.showTotalHoursActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Button SaveBtn, ResetStartingHours, DisplayTotalHours, ResetDB, ExportDB;
    EditText startingHours;
    Spinner aircraftSpinner;
    String aircraft;
    Integer pos1;
    ArrayList<ArrayList> hoursArrayList;
    ArrayList<String> typeHoursList;
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
        hoursArrayList = new ArrayList<>();
        typeHoursList = new ArrayList<>();
        aircraftSpinner = (Spinner) findViewById(R.id.aircraft_pref_spinner1);


        // Create an ArrayAdapter using the string array and a default spinner layout for ALL Spinners
        ArrayAdapter<CharSequence> aircraft_adapter = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, R.layout.spinner_item); //Aircraft_types is in res/values/strings.xml

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
                    Snackbar successSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                            "Your starting hours info is now stored", 1200);
                    successSnack.show();
                    aircraftSpinner.setSelection(0);
                    startingHours.setText("");
                } else {
                    Snackbar missingfieldsSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                            "Type already registered or a field is missing", 1200);
                    missingfieldsSnack.show();
                }


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
                                Snackbar clearedSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Starting Hours Cleared", 1200);
                                clearedSnack.show();
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
                for(String key: totalHours.keySet()) {
                    typeHoursList.add(key);
                    String hours = "";
                    hours = totalHours.get(key)/60+" Ηours and "+totalHours.get(key)%60+" Μinutes ";
                    typeHoursList.add(hours);
                    hoursArrayList.add(typeHoursList);
                    typeHoursList = new ArrayList<>();
                }





                Intent intentShow = new Intent(PreferencesActivity.this, showTotalHoursActivity.class );
                intentShow.putExtra("total hours", hoursArrayList);
                startActivity(intentShow);

                finish();
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
                                Snackbar dbemptySnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Database is now empty", 1500);
                                dbemptySnack.show();
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
                Snackbar exportExcelSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                        "FLights.xls is in your internal storage home directory", 1500);
                exportExcelSnack.show();
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

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, MainActivity.class));
    }
}