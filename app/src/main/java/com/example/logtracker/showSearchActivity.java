package com.example.logtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logtracker.basicActivities.PreferencesActivity;
import com.example.logtracker.basicActivities.SearchActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class showSearchActivity extends AppCompatActivity implements flightRVAdapter.OnDeleteListener {
    ArrayList<ArrayList> flightResults;
    LogtrackerDBHandler flightsDB;
    private int sumHoursInt ;///
    private String hoursSumStr;

    TextView sumHoursText;

    RecyclerView rvList;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        setContentView(R.layout.activity_show_search);
        //gets an Arraylist of an Arraylist to display
        flightResults = new ArrayList<>();
        flightResults = (ArrayList) getIntent().getSerializableExtra("search values");
        flightsDB = new LogtrackerDBHandler(this); //creates db obj

        ///get total hours which is last item and remove it and set it to text
        ArrayList<String> temp = new ArrayList<>();
        temp = flightResults.get(flightResults.size()-1);
        flightResults.remove(flightResults.size()-1);
        sumHoursInt = Integer.parseInt(temp.get(0));///
        hoursSumStr = Integer.toString(sumHoursInt/60) +" : "+ String.format("%02d", (sumHoursInt % 60) );
        sumHoursText = (TextView) findViewById(R.id.hoursTextView);
        sumHoursText.setText("Your selection total hours:  "+ hoursSumStr);


        //sets the Recyvler View to the activity
        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);


        ///we sent the results to the the adapter
        myAdapter = new flightRVAdapter(flightResults, this);
        rvList.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, SearchActivity.class));
    }


    @Override
    public void onDeleteClick(int position) {
        //delete happens here
        //System.out.println("calculated : "+getDurationMinutes(flightResults.get(position).get(8).toString()));
        // setting a warning dialog before resetting the database
        AlertDialog alertDialog = new AlertDialog.Builder(showSearchActivity.this).create();
        alertDialog.setTitle("Flight Deletion");
        alertDialog.setMessage("The flight will be permanently deleted.\n Are you sure ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flightsDB.deleteFlight(flightResults.get(position).get(0).toString());//delete from database
                        String textTotal = getDurationMinutes(flightResults.get(position).get(8).toString());
                        sumHoursText.setText("Total hours for your selection:  " + textTotal);

                        flightResults.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        Snackbar successfully_deletedSnack = Snackbar.make(findViewById(R.id.showFlightsLayout),
                                "Flight successfully deleted", 1200);
                        successfully_deletedSnack.show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private String getDurationMinutes(String time){
        String[] durationOfSelection  = time.split(":");
        int dur = (Integer.parseInt(durationOfSelection[0])*60) + Integer.parseInt(durationOfSelection[1]);
        int temp = sumHoursInt-dur;
        hoursSumStr = Integer.toString(temp/60) +" : "+ String.format("%02d", (temp % 60) );
        sumHoursInt = temp;
        return hoursSumStr;
        //old version - replace function with this
        //String[] durationOfSelection  = cursor.getString(j).split(":");
        //sumHours += ((Integer.parseInt(durationOfSelection[0])*60) + Integer.parseInt(durationOfSelection[1]));

    }


}