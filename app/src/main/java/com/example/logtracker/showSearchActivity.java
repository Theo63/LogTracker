package com.example.logtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.logtracker.basicActivities.PreferencesActivity;
import com.example.logtracker.basicActivities.SearchActivity;
import java.util.ArrayList;

public class showSearchActivity extends AppCompatActivity implements flightRVAdapter.OnDeleteListener {
    ArrayList<ArrayList> flightResults;
    LogtrackerDBHandler flightsDB;

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
        System.out.println("item clicked");
        // setting a warning dialog before resetting the database
        AlertDialog alertDialog = new AlertDialog.Builder(showSearchActivity.this).create();
        alertDialog.setTitle("Flight Deletion");
        alertDialog.setMessage("The flight will be permanently deleted.\n Are you sure ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flightsDB.deleteFlight(flightResults.get(position).get(0).toString());//delete from database
                        flightResults.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        Toast.makeText(showSearchActivity.this,"Flight successfully deleted",Toast.LENGTH_SHORT).show();
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
}