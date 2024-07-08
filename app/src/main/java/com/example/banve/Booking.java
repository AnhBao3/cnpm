package com.example.banve;

import android.os.Parcel;
import android.os.Parcelable;

public class Booking implements Parcelable {
    private int bookingId;
    private String username;
    private Flight flight;
    private String paymentStatus;

    public Booking(int bookingId, String username, Flight flight, String paymentStatus) {
        this.bookingId = bookingId;
        this.username = username;
        this.flight = flight;
        this.paymentStatus = paymentStatus;
    }

    protected Booking(Parcel in) {
        bookingId = in.readInt();
        username = in.readString();
        flight = in.readParcelable(Flight.class.getClassLoader());
        paymentStatus = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookingId);
        dest.writeString(username);
        dest.writeParcelable(flight, flags);
        dest.writeString(paymentStatus);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", username='" + username + '\'' +
                ", flight=" + flight +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
