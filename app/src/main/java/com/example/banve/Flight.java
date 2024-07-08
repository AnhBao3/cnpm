package com.example.banve;

import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {
    private int flightID;
    private String flightNumber;
    private String departure;
    private String arrival;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private String airlineCode;
    private double price;

    public Flight(int flightID, String flightNumber, String departure, String arrival, String date, String departureTime, String arrivalTime, String airlineCode, double price) {
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineCode = airlineCode;
        this.price = price;
    }

    protected Flight(Parcel in) {
        flightID = in.readInt();
        flightNumber = in.readString();
        departure = in.readString();
        arrival = in.readString();
        date = in.readString();
        departureTime = in.readString();
        arrivalTime = in.readString();
        airlineCode = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    public int getFlightID() {
        return flightID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(flightID);
        parcel.writeString(flightNumber);
        parcel.writeString(departure);
        parcel.writeString(arrival);
        parcel.writeString(date);
        parcel.writeString(departureTime);
        parcel.writeString(arrivalTime);
        parcel.writeString(airlineCode);
        parcel.writeDouble(price);
    }
    @Override
    public String toString() {
        return
                flightNumber  + " - " +
                departure + " - " +
               arrival;
    }

}
