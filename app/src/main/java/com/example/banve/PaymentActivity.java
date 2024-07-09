package com.example.banve;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {

    private EditText etCardNumber, etCardExpiry, etCardCVV, etCardAmount;
    private Button btnPayByCard, btnPayByCash;
    private TextView tvTotalAmount;
    private DBHelper dbHelper;
    private Booking booking;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardExpiry = findViewById(R.id.etCardExpiry);
        etCardCVV = findViewById(R.id.etCardCVV);
        etCardAmount = findViewById(R.id.etCardAmount);
        btnPayByCard = findViewById(R.id.btnPayByCard);
        btnPayByCash = findViewById(R.id.btnPayByCash);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        dbHelper = new DBHelper(this);
        booking = getIntent().getParcelableExtra("booking");
        double flightPrice = getIntent().getDoubleExtra("flightPrice", 0.0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(flightPrice);
        tvTotalAmount.setText("Tổng số tiền: " + formattedPrice + " VND");
        btnPayByCard.setOnClickListener(v -> payByCard(flightPrice));
        btnPayByCash.setOnClickListener(v -> payByCash());
    }


    private void payByCard(double flightPrice) {
        String cardNumber = etCardNumber.getText().toString().trim();
        String cardExpiry = etCardExpiry.getText().toString().trim();
        String cardCVV = etCardCVV.getText().toString().trim();

        String cardAmount = etCardAmount.getText().toString().trim();
        if (cardNumber.isEmpty() || cardExpiry.isEmpty() || cardCVV.isEmpty() || cardAmount.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin thẻ", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(cardAmount);
        if (amount >= flightPrice) {
            updatePaymentStatus("Đã thanh toán");
            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Số tiền không đủ", Toast.LENGTH_SHORT).show();
        }
    }

    private void payByCash() {
        updatePaymentStatus("Chờ thanh toán tại quầy");
        Toast.makeText(this, "Bạn đã chọn thanh toán bằng tiền mặt tại quầy", Toast.LENGTH_SHORT).show();
    }

    private void updatePaymentStatus(String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PAYMENT_STATUS, status);
        String whereClause = DBHelper.COLUMN_BOOKING_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(booking.getBookingId())};
        db.update(DBHelper.TABLE_BOOKING, values, whereClause, whereArgs);
        db.close();
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        intent.putExtra("username", booking.getUsername());
        startActivity(intent);
    }
}
