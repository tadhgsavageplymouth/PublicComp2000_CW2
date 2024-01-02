package com.example.myapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.util.Log; // Import statement for Log
import java.text.SimpleDateFormat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditBookingActivity extends Activity {

    private CalendarView calendarView; // Calendar view to select a new date
    private Spinner spinnerTableSize, spinnerTableTime; // Spinners to select new table size and time
    private String bookingId; // To hold the booking ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking); // Set the layout for this activity

        Intent intent = getIntent();
        bookingId = intent.getStringExtra("bookingId");
        Log.d("EditBookingActivity", "Received bookingId: " + bookingId);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        spinnerTableSize = findViewById(R.id.spinnerTableSize);
        spinnerTableTime = findViewById(R.id.spinnerTableTime);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Retrieve additional booking details passed from ManageBookingsActivity
        // No need to redeclare 'intent' here, as it's already defined above
        String date = intent.getStringExtra("date");
        String tableSize = intent.getStringExtra("tableSize");
        String tableTime = intent.getStringExtra("tableTime");

        // Set up the calendar with the date from the booking
        // Assuming the date string is in the format "dd/MM/yyyy"
        Calendar cal = Calendar.getInstance();
        String[] dateParts = date.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Month is 0-based
        int year = Integer.parseInt(dateParts[2]);
        cal.set(year, month, day);
        calendarView.setDate(cal.getTimeInMillis());

        // Set up spinners
        setupSpinner(spinnerTableSize, R.array.table_sizes, tableSize);
        setupSpinner(spinnerTableTime, R.array.table_times, tableTime);

        // Setup button click listener to save changes
        buttonSave.setOnClickListener(v -> saveChanges());
    }

    private void setupSpinner(Spinner spinner, int arrayResId, String currentValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (currentValue != null) {
            int spinnerPosition = adapter.getPosition(currentValue);
            spinner.setSelection(spinnerPosition);
        }
    }

    private void saveChanges() {
        // Extract the updated values from the UI
        long dateMillis = calendarView.getDate();
        String tableSize = spinnerTableSize.getSelectedItem().toString();
        String tableTime = spinnerTableTime.getSelectedItem().toString();

        // Convert dateMillis to a formatted date string ("dd/MM/yyyy")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String updatedDate = dateFormat.format(new Date(dateMillis));

        // Update the booking in Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<Void> voidTask = db.collection("bookings").document(bookingId)
                .update("date", updatedDate, "tableSize", tableSize, "tableTime", tableTime)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditBookingActivity.this,
                            "Booking updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after successful update
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditBookingActivity.this,
                            "Failed to update booking: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
