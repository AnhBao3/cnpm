package com.example.banve;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchFlightActivity extends AppCompatActivity {

    private EditText etSearchTerm;
    private Button btnSearch;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);

        etSearchTerm = findViewById(R.id.etSearchTerm);
        btnSearch = findViewById(R.id.btnSearch);
        dbHelper = DBHelper.getInstance(this);

        btnSearch.setOnClickListener(v -> searchFlights());
    }

    private void searchFlights() {
        String searchTerm = etSearchTerm.getText().toString().trim();

        // Check if search term is filled
        if (TextUtils.isEmpty(searchTerm)) {
            Toast.makeText(this, "Vui lòng nhập điểm đi hoặc điểm đến", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform search logic based on searchTerm (departure or arrival)
        ArrayList<Flight> searchResults = dbHelper.getFlightsByDepartureOrArrival(searchTerm);

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy chuyến bay phù hợp", Toast.LENGTH_SHORT).show();
        } else {
            // Pass search results to FlightListActivity
            Intent intent = new Intent(this, FlightListActivity.class);
            intent.putParcelableArrayListExtra("searchResults", searchResults);
            startActivity(intent);
        }
    }

}
