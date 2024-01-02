package com.example.myapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateBookingActivity extends Activity {

    private CalendarView calendarView; // Calendar view to select a date
    private Spinner spinnerTableSize, spinnerTableTime; // Spinners to select table size and time
    private Calendar selectedDate = Calendar.getInstance(); // Selected date for the booking
    private String selectedTableSize, selectedTableTime; // Selected table size and time
    private Button buttonBook; // Button to create a booking
    private FirebaseFirestore db; // Firestore database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking); // Set the layout for this activity

        db = FirebaseFirestore.getInstance(); // Initialize Firestore database reference

        initializeCalendarView();
        initializeSpinnerTableSize();
        initializeSpinnerTableTime();

        buttonBook = findViewById(R.id.buttonBook); // Initialize the booking button
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTable();
            }
        });
    }

    private void initializeCalendarView() {
        calendarView = findViewById(R.id.calendarView); // Initialize the calendar view
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        });
    }

    private void initializeSpinnerTableSize() {
        spinnerTableSize = findViewById(R.id.spinnerTableSize); // Initialize the table size spinner
        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(this, R.array.table_sizes, android.R.layout.simple_spinner_item);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableSize.setAdapter(adapterSize);
        spinnerTableSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTableSize = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeSpinnerTableTime() {
        spinnerTableTime = findViewById(R.id.spinnerTableTime); // Initialize the table time spinner
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this, R.array.table_times, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableTime.setAdapter(adapterTime);
        spinnerTableTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTableTime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void bookTable() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();

            // Format the date to dd/MM/yyyy
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate.getTime());

            Map<String, Object> booking = new HashMap<>();
            booking.put("date", formattedDate); // Store formatted date
            booking.put("tableSize", selectedTableSize);
            booking.put("tableTime", selectedTableTime);
            booking.put("userEmail", userEmail);

            db.collection("bookings").add(booking)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(CreateBookingActivity.this, "Table booked successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateBookingActivity.this, MainActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(CreateBookingActivity.this, "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(CreateBookingActivity.this, "You must be logged in to book a table.", Toast.LENGTH_SHORT).show();
        }
    }
}
