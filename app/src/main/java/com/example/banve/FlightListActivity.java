package com.example.banve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FlightListActivity extends AppCompatActivity {

    private ListView listViewFlights;
    private FlightListAdapter flightListAdapter;
    private DBHelper dbHelper;
    private String loggedInUsername; // Add this variable to hold the logged-in username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        listViewFlights = findViewById(R.id.listViewFlights);
        dbHelper = DBHelper.getInstance(this);

        // Retrieve search results and username from intent
        ArrayList<Flight> searchResults = getIntent().getParcelableArrayListExtra("searchResults");
        loggedInUsername = getIntent().getStringExtra("username");

        // Initialize and set adapter
        flightListAdapter = new FlightListAdapter(this, searchResults);
        listViewFlights.setAdapter(flightListAdapter);


    }
}
