package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.logtracker.basicActivities.PreferencesActivity;

import java.util.ArrayList;

public class showTotalHoursActivity extends AppCompatActivity {
    ArrayList<ArrayList> totalHourResults;


    RecyclerView hoursList;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        setContentView(R.layout.activity_show_total_hours);
        //gets an Arraylist of an Arraylist to display
        totalHourResults = new ArrayList<>();
        totalHourResults = (ArrayList) getIntent().getSerializableExtra("total hours");


        //sets the Recyvler View to the activity
        hoursList = findViewById(R.id.HoursList);
        hoursList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        hoursList.setLayoutManager(layoutManager);


        ///we sent the results to the the adapter
        myAdapter = new totalHoursAdapter(totalHourResults);
        hoursList.setAdapter(myAdapter);



    }

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, PreferencesActivity.class));
    }

}