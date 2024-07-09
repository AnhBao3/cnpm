package com.example.banve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FlightListActivity extends AppCompatActivity {

    private ListView listViewFlights;
    private FlightListAdapter flightListAdapter;
    private DBHelper dbHelper;
    private String loggedInUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        listViewFlights = findViewById(R.id.listViewFlights);
        dbHelper = DBHelper.getInstance(this);
        ArrayList<Flight> searchResults = getIntent().getParcelableArrayListExtra("searchResults");
        loggedInUsername = getIntent().getStringExtra("username");
        flightListAdapter = new FlightListAdapter(this, searchResults);
        listViewFlights.setAdapter(flightListAdapter);
        listViewFlights.setOnItemClickListener((parent, view, position, id) -> {
            Flight selectedFlight = searchResults.get(position);
            Intent intent = new Intent(FlightListActivity.this, FlightDetailActivity.class);
            intent.putExtra("flightDetail", selectedFlight);
            intent.putExtra("username", loggedInUsername);
            startActivity(intent);
        });
    }
}
