package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    TextView userName;
    Button logout, contactButton, aboutButton, createBookingButton, manageBookingsButton, preferencesButton, reviewsButton, bookingHistoryButton;
    GoogleSignInClient gClient; // Google Sign In client

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize views
        userName = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);
        contactButton = findViewById(R.id.btnContact);
        aboutButton = findViewById(R.id.btnAbout);
        createBookingButton = findViewById(R.id.btnDinnerBooking);
        manageBookingsButton = findViewById(R.id.btnManageBookings);
        preferencesButton = findViewById(R.id.btnPreferences);
        reviewsButton = findViewById(R.id.btnReviews);
        bookingHistoryButton = findViewById(R.id.btnBookingHistory);

        // Initialize Google Sign In client
        GoogleSignInOptions gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        // Retrieve the last signed-in account and display the user's name
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null) {
            userName.setText(gAccount.getDisplayName());
        }

        // Set onClickListeners for buttons
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        logout.setOnClickListener(v -> signOut()); // Log out when the "Logout" button is clicked
        contactButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ContactActivity.class))); // Open the ContactActivity
        aboutButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class))); // Open the AboutActivity
        createBookingButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateBookingActivity.class))); // Open the CreateBookingActivity
        manageBookingsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ManageBookingsActivity.class))); // Open the ManageBookingsActivity
        preferencesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PreferencesActivity.class))); // Open the PreferencesActivity
        reviewsButton.setOnClickListener(v -> openReviewsLink()); // Open a web link (e.g., restaurant reviews)
        bookingHistoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BookingHistoryActivity.class))); // Open the BookingHistoryActivity
    }

    private void signOut() {
        gClient.signOut().addOnCompleteListener(this, task -> {
            // Redirect to LoginActivity after signing out
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Close the MainActivity
        });
    }

    private void openReviewsLink() {
        String reviewsUrl = "https://www.tripadvisor.co.uk/Restaurant_Review-g186258-d806430-Reviews-Barbican_Kitchen-Plymouth_Devon_England.html"; // Replace with the actual URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewsUrl));
        startActivity(intent);
    }
}
