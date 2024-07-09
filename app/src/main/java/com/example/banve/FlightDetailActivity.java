package com.example.banve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FlightDetailActivity extends AppCompatActivity {

    private TextView tvFlightNumber, tvDeparture, tvArrival, tvDate, tvDepartureTime, tvArrivalTime, tvAirlineCode, tvPrice;
    private Flight flight;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        initializeViews();
        retrieveIntentData();
        displayFlightDetails();
        setupBookButton();
    }

    private void initializeViews() {
        tvFlightNumber = findViewById(R.id.tvFlightNumber);
        tvDeparture = findViewById(R.id.tvDeparture);
        tvArrival = findViewById(R.id.tvArrival);
        tvDate = findViewById(R.id.tvDate);
        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvAirlineCode = findViewById(R.id.tvAirlineCode);
        tvPrice = findViewById(R.id.tvPrice);
        Button btnBook = findViewById(R.id.btnBook);
    }

    private void retrieveIntentData() {
        flight = getIntent().getParcelableExtra("flightDetail");
        username = getIntent().getStringExtra("username");

        if (flight == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin chuyến bay", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (username == null) {
            Toast.makeText(this, "Lỗi: Tên người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }


    private void displayFlightDetails() {
        if (flight != null) {
            tvFlightNumber.setText("Mã chuyến bay: " + flight.getFlightNumber());
            tvDeparture.setText("Điểm khởi hành: " + flight.getDeparture());
            tvArrival.setText("Điểm đến: " + flight.getArrival());
            tvDate.setText("Ngày: " + flight.getDate());
            tvDepartureTime.setText("Giờ khởi hành: " + flight.getDepartureTime());
            tvArrivalTime.setText("Giờ đến: " + flight.getArrivalTime());
            tvAirlineCode.setText("Mã hãng: " + flight.getAirlineCode());
            String priceText = String.format("Giá: %.0f VND", flight.getPrice());
            tvPrice.setText(priceText);        }
    }

    private void setupBookButton() {
        Button btnBook = findViewById(R.id.btnBook);
        btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(FlightDetailActivity.this, BookingActivity.class);
            intent.putExtra("flightDetail", flight);
            intent.putExtra("username", username);
            intent.putExtra("flightPrice", flight.getPrice());
            startActivity(intent);
        });
    }
}
