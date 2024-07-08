package com.example.banve;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvFlights;
    private ArrayList<Flight> flights;
    private ArrayAdapter<Flight> adapter;
    private DBHelper dbHelper;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        loggedInUsername = intent.getStringExtra("username");

        // Kiểm tra xem đã có username đăng nhập hay chưa
        if (loggedInUsername == null) {
            // Nếu chưa có, chuyển sang LoginActivity để đăng nhập
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Đóng MainActivity để ngăn người dùng quay lại nếu không đăng nhập
            return;
        }

        // Nếu đã đăng nhập, tiếp tục load danh sách chuyến bay và các hoạt động khác
        lvFlights = findViewById(R.id.lvFlights);
        flights = new ArrayList<>();
        dbHelper = new DBHelper(this);

        loadFlights();

        lvFlights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FlightDetailActivity.class);
                intent.putExtra("flightDetail", flights.get(position));
                intent.putExtra("username", loggedInUsername);
                startActivity(intent);
            }
        });

        // Cài đặt sự kiện cho nút xem lịch sử đặt vé
        Button btnBookingHistory = findViewById(R.id.btnBookingHistory);
        btnBookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookingListActivity.class);
                intent.putExtra("username", loggedInUsername);
                startActivity(intent);
            }
        });
    }


    private void loadFlights() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_FLIGHT, null);

        if (cursor.moveToFirst()) {
            do {
                int flightID = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_FLIGHT_ID));
                String flightNumber = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_FLIGHT_NUMBER));
                String departure = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DEPARTURE));
                String arrival = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ARRIVAL));
                String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));
                String departureTime = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DEPARTURE_TIME));
                String arrivalTime = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ARRIVAL_TIME));
                String airlineCode = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_AIRLINE_CODE));
                double price = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_PRICE));

                Flight flight = new Flight(flightID, flightNumber, departure, arrival, date, departureTime, arrivalTime, airlineCode, price);
                flights.add(flight);
            } while (cursor.moveToNext());
        }

        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, flights);
        lvFlights.setAdapter(adapter);
    }
    public void viewBookingHistory(View view) {
        Intent intent = new Intent(MainActivity.this, BookingListActivity.class);
        intent.putExtra("username", loggedInUsername);
        startActivity(intent);
    }
    public void onSearchFlightButtonClick(View view) {
        Intent intent = new Intent(this, SearchFlightActivity.class);
        startActivity(intent);
    }

}
