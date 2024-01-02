package com.example.myapp2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    // Initialize the switches
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchEmail, switchSMS, switchPushNotifications;
    Spinner spinnerPreferredTable;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences); // Set the layout for this activity

        // Initialize the spinner for preferred table
        spinnerPreferredTable = findViewById(R.id.spinnerPreferredTable);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.table_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPreferredTable.setAdapter(adapter);

        // Initialize switches
        switchEmail = findViewById(R.id.switchEmail);
        switchSMS = findViewById(R.id.switchSMS);
        switchPushNotifications = findViewById(R.id.switchPushNotifications);

        // Load preferences state and set the switch positions accordingly
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        switchEmail.setChecked(preferences.getBoolean("EmailSwitch", false));
        switchSMS.setChecked(preferences.getBoolean("SMSSwitch", false));
        switchPushNotifications.setChecked(preferences.getBoolean("PushNotificationsSwitch", false));

        // Set listeners to update the preferences when switches are toggled
        switchEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state of the Email switch in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("EmailSwitch", isChecked);
                editor.apply();
            }
        });

        switchSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state of the SMS switch in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("SMSSwitch", isChecked);
                editor.apply();
            }
        });

        switchPushNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state of the Push Notifications switch in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("PushNotificationsSwitch", isChecked);
                editor.apply();
            }
        });
    }

    // TODO: Save preferences state when changed
}
