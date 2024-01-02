package com.example.myapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact); // Set the layout for this activity

        TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);

        // Set the phone number text as clickable
        tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to dial the phone number when clicked
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01752911912")); // Replace with your desired phone number
                startActivity(intent); // Start the dialing activity
            }
        });
    }
}
