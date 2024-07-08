package com.example.banve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "flight_ticket_app.db";

    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 3; // Updated version

    // Tên bảng và các cột trong bảng User
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";

    // Tên bảng và các cột trong bảng Flight
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

    // Tên bảng và các cột trong bảng Booking
    public static final String TABLE_BOOKING = "Booking";
    public static final String COLUMN_BOOKING_ID = "BookingID";
    public static final String COLUMN_BOOKING_USER_ID = "UserID";
    public static final String COLUMN_BOOKING_FLIGHT_ID = "FlightID";
    public static final String COLUMN_BOOKING_DATE = "BookingDate";
    public static final String COLUMN_PAYMENT_STATUS = "PaymentStatus"; // New column

    // Câu lệnh SQL để tạo bảng User
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT NOT NULL UNIQUE," +
                    COLUMN_PASSWORD + " TEXT NOT NULL)";

    // Câu lệnh SQL để tạo bảng Flight
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

    // Câu lệnh SQL để tạo bảng Booking
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

    // Singleton pattern để đảm bảo chỉ có một instance của DBHelper tồn tại
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
        // Tạo các bảng
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_FLIGHT);
        db.execSQL(SQL_CREATE_BOOKING);

        // Chèn dữ liệu mẫu
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COLUMN_PAYMENT_STATUS + " TEXT NOT NULL DEFAULT 'Unpaid'");
        } else {
            // Xóa bảng cũ nếu tồn tại và tạo lại
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }
    }

    // Phương thức để chèn dữ liệu mẫu vào các bảng
    private void insertSampleData(SQLiteDatabase db) {
        // Chèn dữ liệu vào bảng User
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

        // Chèn dữ liệu vào bảng Flight
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
    }

    // Phương thức để đóng kết nối cơ sở dữ liệu
    public void closeDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // Phương thức để lấy userId từ username
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

    // Phương thức để thêm booking vào bảng Booking
    public void addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_USER_ID, getUserId(booking.getUsername())); // Lấy userId từ username
        values.put(COLUMN_BOOKING_FLIGHT_ID, booking.getFlight().getFlightID()); // Lấy flightId từ flight
        values.put(COLUMN_BOOKING_DATE, booking.getFlight().getDate()); // Lấy ngày từ flight
        values.put(COLUMN_PAYMENT_STATUS, booking.getPaymentStatus()); // Set default payment status to "Unpaid"

        long result = db.insert(TABLE_BOOKING, null, values);
        db.close(); // Đóng kết nối

        Log.d("DBHelper", "Booking inserted with ID: " + result);
    }

    // Phương thức để lấy danh sách các booking dựa trên username
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

    // Phương thức để cập nhật trạng thái thanh toán của booking
    public void updatePaymentStatus(int bookingId, String paymentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_STATUS, paymentStatus);

        int result = db.update(TABLE_BOOKING, values, COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
        db.close(); // Đóng kết nối

        Log.d("DBHelper", "Payment status updated for booking ID: " + bookingId + " to " + paymentStatus + " with result: " + result);
    }
    public ArrayList<Flight> getFlightsByDepartureOrArrival(String searchTerm) {
        ArrayList<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query flights where departure or arrival matches searchTerm
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
