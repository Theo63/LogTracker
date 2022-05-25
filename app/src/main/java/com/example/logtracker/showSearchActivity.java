package com.example.logtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class showSearchActivity extends AppCompatActivity {
    ArrayList<ArrayList> flightResults;


    RecyclerView rvList;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);
        //gets an Arraylist of an Arraylist to display
        flightResults = new ArrayList<>();
        flightResults = (ArrayList) getIntent().getSerializableExtra("search values");


        //sets the Recyvler View to the activity
        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);


        ///we sent the results to the the adapter
        myAdapter = new flightRVAdapter(flightResults);
        rvList.setAdapter(myAdapter);



    }

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, SearchActivity.class));
    }



}