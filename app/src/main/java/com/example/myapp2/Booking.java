package com.example.myapp2;

public class Booking {
    private String id; // Unique ID for the Firestore document
    private String date; // Date of the booking
    private String tableSize; // Size of the table requested
    private String tableTime; // Time of the booking
    private String userEmail; // User's email address to identify the booking

    // Empty constructor needed for Firestore data retrieval
    public Booking() {}

    // Getter and setter for the ID field
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Getter and setter for the date field
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    // Getter and setter for the tableSize field
    public String getTableSize() { return tableSize; }
    public void setTableSize(String tableSize) { this.tableSize = tableSize; }

    // Getter and setter for the tableTime field
    public String getTableTime() { return tableTime; }
    public void setTableTime(String tableTime) { this.tableTime = tableTime; }

    // Getter and setter for the userEmail field
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
