package com.example.myapp2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final List<Booking> bookingList; // List of booking objects to display
    private final OnBookingListener onBookingListener; // Listener for item click events

    public BookingAdapter(List<Booking> bookingList, OnBookingListener onBookingListener) {
        this.bookingList = bookingList;
        this.onBookingListener = onBookingListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_booking layout for each item view in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view, onBookingListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        // Set the text for date, table size, and table time TextViews
        holder.dateTextView.setText("Date: " + booking.getDate());
        holder.tableSizeTextView.setText("Table Size: " + booking.getTableSize());
        holder.tableTimeTextView.setText("Table Time: " + booking.getTableTime());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView tableSizeTextView;
        TextView tableTimeTextView;
        Button deleteButton;
        Button editButton; // Add edit button

        ViewHolder(View itemView, OnBookingListener onBookingListener) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tvDate);
            tableSizeTextView = itemView.findViewById(R.id.tvTableSize);
            tableTimeTextView = itemView.findViewById(R.id.tvTableTime);
            deleteButton = itemView.findViewById(R.id.btnDeleteBooking);
            editButton = itemView.findViewById(R.id.btnEditBooking); // Initialize edit button

            // Set OnClickListener for delete button
            deleteButton.setOnClickListener(view -> {
                if (onBookingListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onBookingListener.onDeleteClick(getAdapterPosition());
                }
            });

            // Set OnClickListener for edit button
            editButton.setOnClickListener(view -> {
                if (onBookingListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onBookingListener.onEditClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnBookingListener {
        void onDeleteClick(int position);
        void onEditClick(int position); // Add onEditClick method
    }
}
