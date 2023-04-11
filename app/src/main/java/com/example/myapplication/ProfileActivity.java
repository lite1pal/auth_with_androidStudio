package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button logOutButton = (Button) findViewById(R.id.logOutBtn);

        // listens on logOutButton click
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // removes sessionTimes from userData, user is logged out now
                SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("sessionTimes", 0);
                editor.apply();

                // redirects to LoginActivity
                Intent switchActivityIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });

    }
}
