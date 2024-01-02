package com.example.myapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ManageBookingsActivity extends Activity implements BookingAdapter.OnBookingListener {

    private RecyclerView recyclerViewBookings;
    private List<Booking> bookingList;
    private BookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings); // Set the layout for this activity

        // Initialize RecyclerView for displaying bookings
        recyclerViewBookings = findViewById(R.id.recyclerViewBookings);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(this));

        // Initialize list and adapter for bookings
        bookingList = new ArrayList<>();
        adapter = new BookingAdapter(bookingList, this);
        recyclerViewBookings.setAdapter(adapter);

        // Fetch user-specific bookings from Firebase Firestore
        fetchBookings();
    }

    private void fetchBookings() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("bookings")
                .whereEqualTo("userEmail", userEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookingList.clear();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Booking booking = snapshot.toObject(Booking.class);
                        booking.setId(snapshot.getId());
                        bookingList.add(booking);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle any potential error that may occur during fetching
                });
    }

    @Override
    public void onDeleteClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Booking")
                .setMessage("Are you sure you want to delete this booking?")
                .setPositiveButton("Yes", (dialog, which) -> deleteBooking(position))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void onEditClick(int position) {
        Booking bookingToEdit = bookingList.get(position);
        Intent intent = new Intent(this, EditBookingActivity.class);
        intent.putExtra("bookingId", bookingToEdit.getId());
        intent.putExtra("date", bookingToEdit.getDate());
        intent.putExtra("tableSize", bookingToEdit.getTableSize());
        intent.putExtra("tableTime", bookingToEdit.getTableTime());
        // Add any other details you need to pass to the EditBookingActivity
        startActivity(intent);
    }

    private void deleteBooking(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Booking bookingToDelete = bookingList.get(position);

        db.collection("bookings").document(bookingToDelete.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the deleted booking from the list and update the RecyclerView
                    bookingList.remove(position);
                    adapter.notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> {
                    // Handle any failure that may occur during deletion (e.g., show a toast message)
                });
    }
}
