package com.codetracker.logtracker.basicActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;


import com.codetracker.logtracker.CustomDialogClass;
import com.codetracker.logtracker.DatePickerFragment;
import com.codetracker.logtracker.LogtrackerDBHandler;
import com.codetracker.logtracker.R;
import com.codetracker.logtracker.showSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Button dateBtn1, dateBtn2, SearchBtn, helpButton;
    public EditText aircraftIDText, locationFromText, locationToText;
    public Spinner aircraftTypeSpinner, typeofFlightSpinner, dutyonBoardSpinner;
    public static HashMap<String, String> searchValues;

    ArrayList<ArrayList> flightResults;
    LogtrackerDBHandler flightsDB;

//    Snackbar errorSnack = Snackbar.make(findViewById(R.id.registrationLayout);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        dateBtn1 = (Button) findViewById(R.id.dateFromButton);
        dateBtn2 = (Button) findViewById(R.id.dateUntilButton);
        SearchBtn = (Button) findViewById(R.id.searchBtn);
        helpButton = (Button) findViewById(R.id.helpButton);


        aircraftTypeSpinner = (Spinner) findViewById(R.id.aircraft_type_spinnerSearch);
        typeofFlightSpinner = (Spinner) findViewById(R.id.type_of_flight_spinnerSearch);
        dutyonBoardSpinner = (Spinner) findViewById(R.id.duty_on_board_spinnerSearch);

        aircraftIDText = (EditText) findViewById(R.id.aircraft_id_inputSearch);
        locationFromText = (EditText) findViewById(R.id.locationFromSearch);
        locationToText = (EditText) findViewById(R.id.locationToSearch);

        searchValues = new HashMap<String, String>(); //put all values for search in a hashmap
        flightResults = new ArrayList<>();
        flightsDB = new LogtrackerDBHandler(this); //creates db obj
        // key values are  : fromDate, untilDate , aircraftType, typeOfFlight, dutyOnBoard, aircraftId, fromLocation, toLocation

        ///////// Spiner Section //////
        ArrayAdapter<CharSequence> type_of_aircraftadapterSearch = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, R.layout.spinner_item); //Aircraft_types is in res/values/strings.xml

        ArrayAdapter<CharSequence> type_of_flightadapterSearch = ArrayAdapter.createFromResource(this,
                R.array.Flight_types, R.layout.spinner_item);

        ArrayAdapter<CharSequence> duty_on_boardadapterSearch = ArrayAdapter.createFromResource(this,
                R.array.Duty_on_Board, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        type_of_aircraftadapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_of_flightadapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duty_on_boardadapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        aircraftTypeSpinner.setAdapter(type_of_aircraftadapterSearch);
        aircraftTypeSpinner.setOnItemSelectedListener(this);

        typeofFlightSpinner.setAdapter(type_of_flightadapterSearch);
        typeofFlightSpinner.setOnItemSelectedListener(this);

        dutyonBoardSpinner.setAdapter(duty_on_boardadapterSearch);
        dutyonBoardSpinner.setOnItemSelectedListener(this);
        ////// end of spiner section ///////

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputValuesSearch(); //collect editText values
                flightResults = flightsDB.getFlightSearch(searchValues); //call LogtrackerDBHandler to get
                System.out.println(searchValues);
                //// show results in showSearchActivity /////
                Intent intentShow = new Intent(SearchActivity.this, showSearchActivity.class );
                intentShow.putExtra("search values", flightResults);
                startActivity(intentShow);
                finish();

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd=new CustomDialogClass(SearchActivity.this);
                cdd.show();
                cdd.setInfo("• Press Search with no filters to display all flights \n\n• Set any filters to get specific flights ");
            }
        });


    }


    ////// Date from - to  button section //////
    public void showPickerDialog(View v) {
        if (v.getId()== R.id.dateFromButton){
            DatePickerFragment newDateFragment = new DatePickerFragment();
            newDateFragment.show(getSupportFragmentManager(), "fromPicker");
        }
        else if (v.getId()== R.id.dateUntilButton){
            DatePickerFragment newDateFragment = new DatePickerFragment();
            newDateFragment.show(getSupportFragmentManager(), "untilPicker");
        }

    }


    //when a date is picket we change the value of the button
    public static void setDateButton(ArrayList<Integer> dates, int mode ){
        //set the button to date selected
        if (mode == 0){
            dateBtn1.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
            searchValues.put("fromDate", getDateString(dates));
            //System.out.println("from date: "+getDateString(dates));

        }
        else if (mode == 1){
            dateBtn2.setText(String.valueOf(dates.get(0))+" / "+dates.get(1)+" / "+dates.get(2));
            searchValues.put("untilDate",getDateString(dates));
            //System.out.println("TO date"+getDateString(dates));
        }
        //store date


    }
    public static String getDateString(ArrayList<Integer> dateSelected) { //get dates to string in sql format
        String date = "";
        int counter=0;
        for (Integer item: dateSelected){
            String temp = "";
            if (item>0 && item<10){ // YYYY-MM-DD format for SQL
                temp = "0"+item.toString();
                date=temp+date;
            }
            else {
                date=item+date;
            }
            if (counter<2){
                date="-"+date;
                counter++;
            }
        }
        return date;
    }
    /////////////////////// end of from - to button section //////////////////////////


    ///////////////////Spinner on item selected method /////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.aircraft_type_spinnerSearch && !spinnerInput.equals("choose")){
            // first spinner selected
            searchValues.put("aircraftType", spinnerInput); // set the type of aircraft
        }
        else if (parent.getId() == R.id.type_of_flight_spinnerSearch && !spinnerInput.equals("choose")){
            // second spinner selected
            searchValues.put("typeOfFlight", spinnerInput);//set type of flight string
        }
        else if (parent.getId() == R.id.duty_on_board_spinnerSearch && !spinnerInput.equals("choose")){
            // third spinner selected
            searchValues.put("dutyOnBoard", spinnerInput); //set the light conditions
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //////////////////////////// end of spinner section /////////////////////////////////


    //get input values from EditText when search button is pressed
    public void getInputValuesSearch(){
        searchValues.put("aircraftId",aircraftIDText.getText().toString());
        searchValues.put("fromLocation",locationFromText.getText().toString());
        searchValues.put("toLocation",locationToText.getText().toString());
    }

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, MainActivity.class));
    }



}