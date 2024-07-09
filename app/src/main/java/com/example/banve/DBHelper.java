package com.example.banve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flight_ticket_app.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String TABLE_FLIGHT = "Flight";
    public static final String COLUMN_FLIGHT_ID = "FlightID";
    public static final String COLUMN_FLIGHT_NUMBER = "FlightNumber";
    public static final String COLUMN_DEPARTURE = "Departure";
    public static final String COLUMN_ARRIVAL = "Arrival";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_DEPARTURE_TIME = "DepartureTime";
    public static final String COLUMN_ARRIVAL_TIME = "ArrivalTime";
    public static final String COLUMN_AIRLINE_CODE = "AirlineCode";
    public static final String COLUMN_PRICE = "Price";
    public static final String TABLE_BOOKING = "Booking";
    public static final String COLUMN_BOOKING_ID = "BookingID";
    public static final String COLUMN_BOOKING_USER_ID = "UserID";
    public static final String COLUMN_BOOKING_FLIGHT_ID = "FlightID";
    public static final String COLUMN_BOOKING_DATE = "BookingDate";
    public static final String COLUMN_PAYMENT_STATUS = "PaymentStatus";

    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT NOT NULL UNIQUE," +
                    COLUMN_PASSWORD + " TEXT NOT NULL)";

    private static final String SQL_CREATE_FLIGHT =
            "CREATE TABLE " + TABLE_FLIGHT + " (" +
                    COLUMN_FLIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FLIGHT_NUMBER + " TEXT NOT NULL UNIQUE," +
                    COLUMN_DEPARTURE + " TEXT NOT NULL," +
                    COLUMN_ARRIVAL + " TEXT NOT NULL," +
                    COLUMN_DATE + " TEXT NOT NULL," +
                    COLUMN_DEPARTURE_TIME + " TEXT NOT NULL," +
                    COLUMN_ARRIVAL_TIME + " TEXT NOT NULL," +
                    COLUMN_AIRLINE_CODE + " TEXT NOT NULL," +
                    COLUMN_PRICE + " REAL NOT NULL)";

    private static final String SQL_CREATE_BOOKING =
            "CREATE TABLE " + TABLE_BOOKING + " (" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_BOOKING_USER_ID + " INTEGER NOT NULL," +
                    COLUMN_BOOKING_FLIGHT_ID + " INTEGER NOT NULL," +
                    COLUMN_BOOKING_DATE + " TEXT NOT NULL," +
                    COLUMN_PAYMENT_STATUS + " TEXT NOT NULL DEFAULT 'Unpaid'," + // New column
                    "FOREIGN KEY (" + COLUMN_BOOKING_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")," +
                    "FOREIGN KEY (" + COLUMN_BOOKING_FLIGHT_ID + ") REFERENCES " + TABLE_FLIGHT + "(" + COLUMN_FLIGHT_ID + "))";

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_FLIGHT);
        db.execSQL(SQL_CREATE_BOOKING);

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COLUMN_PAYMENT_STATUS + " TEXT NOT NULL DEFAULT 'Unpaid'");
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "user1");
        values.put(COLUMN_PASSWORD, "password1");
        long userResult = db.insert(TABLE_USER, null, values);
        Log.d("DBHelper", "Inserted user ID: " + userResult);

        values.clear();
        values.put(COLUMN_USERNAME, "user2");
        values.put(COLUMN_PASSWORD, "password2");
        long userResult2 = db.insert(TABLE_USER, null, values);
        Log.d("DBHelper", "Inserted user ID: " + userResult2);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN123");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Hà Nội");
        values.put(COLUMN_DATE, "2024-07-10");
        values.put(COLUMN_DEPARTURE_TIME, "08:00");
        values.put(COLUMN_ARRIVAL_TIME, "10:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 1500000.0);
        long flightResult1 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult1);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN456");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Đà Nẵng");
        values.put(COLUMN_DATE, "2024-07-11");
        values.put(COLUMN_DEPARTURE_TIME, "09:00");
        values.put(COLUMN_ARRIVAL_TIME, "11:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 1200000.0);
        long flightResult2 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult2);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN789");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Nha Trang");
        values.put(COLUMN_DATE, "2024-07-12");
        values.put(COLUMN_DEPARTURE_TIME, "10:00");
        values.put(COLUMN_ARRIVAL_TIME, "12:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 1000000.0);
        long flightResult3 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult3);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN890");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Singapore");
        values.put(COLUMN_DATE, "2024-07-13");
        values.put(COLUMN_DEPARTURE_TIME, "14:00");
        values.put(COLUMN_ARRIVAL_TIME, "17:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 2500000.0);
        long flightResult4 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult4);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN567");
        values.put(COLUMN_DEPARTURE, "Hà Nội");
        values.put(COLUMN_ARRIVAL, "Bangkok");
        values.put(COLUMN_DATE, "2024-07-14");
        values.put(COLUMN_DEPARTURE_TIME, "15:00");
        values.put(COLUMN_ARRIVAL_TIME, "17:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 3000000.0);
        long flightResult5 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult5);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN678");
        values.put(COLUMN_DEPARTURE, "Đà Nẵng");
        values.put(COLUMN_ARRIVAL, "Kuala Lumpur");
        values.put(COLUMN_DATE, "2024-07-15");
        values.put(COLUMN_DEPARTURE_TIME, "16:00");
        values.put(COLUMN_ARRIVAL_TIME, "18:30");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 3500000.0);
        long flightResult6 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult6);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN7890");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Tokyo");
        values.put(COLUMN_DATE, "2024-07-16");
        values.put(COLUMN_DEPARTURE_TIME, "07:00");
        values.put(COLUMN_ARRIVAL_TIME, "15:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 8000000.0);
        long flightResult7 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult7);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN4567");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Sydney");
        values.put(COLUMN_DATE, "2024-07-17");
        values.put(COLUMN_DEPARTURE_TIME, "20:00");
        values.put(COLUMN_ARRIVAL_TIME, "06:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 10000000.0);
        long flightResult8 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult8);

        values.clear();
        values.put(COLUMN_FLIGHT_NUMBER, "VN1234");
        values.put(COLUMN_DEPARTURE, "Hồ Chí Minh");
        values.put(COLUMN_ARRIVAL, "Paris");
        values.put(COLUMN_DATE, "2024-07-18");
        values.put(COLUMN_DEPARTURE_TIME, "23:00");
        values.put(COLUMN_ARRIVAL_TIME, "08:00");
        values.put(COLUMN_AIRLINE_CODE, "VNA");
        values.put(COLUMN_PRICE, 12000000.0);
        long flightResult9 = db.insert(TABLE_FLIGHT, null, values);
        Log.d("DBHelper", "Inserted flight ID: " + flightResult9);
    }


    public void closeDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int userId = -1;

        try {
            cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID}, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            } else {
                Log.e("DBHelper", "User not found with username: " + username);
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting user ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }

    public void addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_USER_ID, getUserId(booking.getUsername()));
        values.put(COLUMN_BOOKING_FLIGHT_ID, booking.getFlight().getFlightID());
        values.put(COLUMN_BOOKING_DATE, booking.getFlight().getDate());
        values.put(COLUMN_PAYMENT_STATUS, booking.getPaymentStatus());

        long result = db.insert(TABLE_BOOKING, null, values);
        db.close(); // Đóng kết nối

        Log.d("DBHelper", "Booking inserted with ID: " + result);
    }

    public ArrayList<Booking> getAllBookings(String username) {
        ArrayList<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKING +
                    " INNER JOIN " + TABLE_FLIGHT +
                    " ON " + TABLE_BOOKING + "." + COLUMN_BOOKING_FLIGHT_ID +
                    " = " + TABLE_FLIGHT + "." + COLUMN_FLIGHT_ID +
                    " WHERE " + TABLE_BOOKING + "." + COLUMN_BOOKING_USER_ID +
                    " = (SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USER +
                    " WHERE " + COLUMN_USERNAME + " = ?)", new String[]{username});

            if (cursor.moveToFirst()) {
                do {
                    int bookingId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
                    int flightId = cursor.getInt(cursor.getColumnIndex(COLUMN_FLIGHT_ID));
                    String flightNumber = cursor.getString(cursor.getColumnIndex(COLUMN_FLIGHT_NUMBER));
                    String departure = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE));
                    String arrival = cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    String departureTime = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME));
                    String arrivalTime = cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_TIME));
                    String airlineCode = cursor.getString(cursor.getColumnIndex(COLUMN_AIRLINE_CODE));
                    double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                    String paymentStatus = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_STATUS));

                    Flight flight = new Flight(flightId, flightNumber, departure, arrival, date, departureTime, arrivalTime, airlineCode, price);
                    Booking booking = new Booking(bookingId, username, flight, paymentStatus); // Updated to include payment status
                    bookings.add(booking);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return bookings;
    }
    public void updatePaymentStatus(int bookingId, String paymentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_STATUS, paymentStatus);

        int result = db.update(TABLE_BOOKING, values, COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
        db.close();

        Log.d("DBHelper", "Payment status updated for booking ID: " + bookingId + " to " + paymentStatus + " with result: " + result);
    }
    public ArrayList<Flight> getFlightsByDepartureOrArrival(String searchTerm) {
        ArrayList<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_FLIGHT +
                            " WHERE " + COLUMN_DEPARTURE + " LIKE ? OR " + COLUMN_ARRIVAL + " LIKE ?",
                    new String[]{"%" + searchTerm + "%", "%" + searchTerm + "%"});

            if (cursor.moveToFirst()) {
                do {
                    int flightId = cursor.getInt(cursor.getColumnIndex(COLUMN_FLIGHT_ID));
                    String flightNumber = cursor.getString(cursor.getColumnIndex(COLUMN_FLIGHT_NUMBER));
                    String departure = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE));
                    String arrival = cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    String departureTime = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME));
                    String arrivalTime = cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_TIME));
                    String airlineCode = cursor.getString(cursor.getColumnIndex(COLUMN_AIRLINE_CODE));
                    double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                    Flight flight = new Flight(flightId, flightNumber, departure, arrival, date, departureTime, arrivalTime, airlineCode, price);
                    flights.add(flight);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return flights;
    }

}
