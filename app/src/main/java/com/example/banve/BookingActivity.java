package com.example.banve;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private TextView tvFlightInfo;
    private Button btnBook;
    private Flight flight;
    private DBHelper dbHelper;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tvFlightInfo = findViewById(R.id.tvFlightInfo);
        btnBook = findViewById(R.id.btnBook);

        flight = getIntent().getParcelableExtra("flightDetail");
        loggedInUsername = getIntent().getStringExtra("username");
        dbHelper = new DBHelper(this);

        if (flight != null) {
            tvFlightInfo.setText(flight.toString());
        }

        btnBook.setOnClickListener(v -> bookFlight());
    }

    private void bookFlight() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem loggedInUsername có giá trị null hay không
        if (loggedInUsername == null) {
            Toast.makeText(this, "Lỗi: Tên người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = getUserId(loggedInUsername);
        int flightId = getFlightId(flight.getFlightNumber());

        Log.d("BookingActivity", "User ID: " + userId);
        Log.d("BookingActivity", "Flight ID: " + flightId);

        if (userId == -1 || flightId == -1) {
            Toast.makeText(this, "Lỗi: Không thể tìm thấy thông tin người dùng hoặc chuyến bay", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_BOOKING_USER_ID, userId);
        values.put(DBHelper.COLUMN_BOOKING_FLIGHT_ID, flightId);
        values.put(DBHelper.COLUMN_BOOKING_DATE, flight.getDate());

        try {
            long result = db.insert(DBHelper.TABLE_BOOKING, null, values);
            if (result != -1) {
                Toast.makeText(this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();

                Booking booking = new Booking((int) result, loggedInUsername, flight, "Chưa thanh toán");
                Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
                intent.putExtra("booking", booking);
                intent.putExtra("flightPrice", flight.getPrice()); // Pass the flight price
                startActivity(intent);

            } else {
                Toast.makeText(this, "Đặt vé thất bại", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("BookingActivity", "Error booking flight", e);
            Toast.makeText(this, "Đặt vé thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private int getUserId(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelper.TABLE_USER, new String[]{DBHelper.COLUMN_USER_ID}, DBHelper.COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_ID));
                cursor.close();
                return userId;
            } else {
                Log.e("BookingActivity", "User not found with username: " + username);
            }
        } catch (Exception e) {
            Log.e("BookingActivity", "Error getting user ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }

    private int getFlightId(String flightNumber) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelper.TABLE_FLIGHT, new String[]{DBHelper.COLUMN_FLIGHT_ID}, DBHelper.COLUMN_FLIGHT_NUMBER + "=?", new String[]{flightNumber}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int flightId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_FLIGHT_ID));
                cursor.close();
                return flightId;
            } else {
                Log.e("BookingActivity", "Flight not found with flight number: " + flightNumber);
            }
        } catch (Exception e) {
            Log.e("BookingActivity", "Error getting flight ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }

}
