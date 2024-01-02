package com.example.myapp2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class BookingHistoryActivity extends Activity {

    private ListView lvBookingHistory; // ListView to display booking history
    private TextView tvNoBookings; // TextView to display a message when there are no bookings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history); // Set the layout for this activity

        lvBookingHistory = findViewById(R.id.lvBookingHistory); // Initialize the ListView
        tvNoBookings = findViewById(R.id.tvNoBookings); // Initialize the TextView

        // TODO: Load booking history into ListView
        // If no bookings, display tvNoBookings
    }

    // TODO: Implement methods to handle booking history loading and interaction
}
