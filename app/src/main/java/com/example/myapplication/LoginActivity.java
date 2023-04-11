package com.example.myapplication;

// imports essential libraries and classes
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    // declares TAG variable that is used for debugging
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // defines the instance of SharedPreferences class
        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        // if there are remained sessions, the app will redirect to ProfileActivity
        checkSessions(sharedPreferences);

        // grabs elements from activity_login using their ids and declares appropriate variables
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
        CheckBox rememberCheckBox = (CheckBox) findViewById(R.id.remember);
        TextView moveToSignUp = (TextView) findViewById(R.id.moveToSignUp);

        // listens on loginBtn click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if username field is empty, returns void
                if (username.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Username is not provided", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if password field is empty, returns void
                if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Password is not provided", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validates user's credentials
                if (!validateCredentials(username, password, rememberCheckBox, sharedPreferences)) {
                    return;
                }

                // the messages pops up that login went successfully
                Toast.makeText(LoginActivity.this, "LOGIN IS SUCCESSFUL", Toast.LENGTH_SHORT).show();

                // redirects to ProfileActivity using Intent class
                Intent switchActivityIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        // listens on moveToSignUp click
        moveToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // redirects to SignupActivity
                Intent switchActivityIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(switchActivityIntent);
            }
        });


    }

    public void checkSessions(SharedPreferences sharedPreferences) {

        /* obtains all key-value pairs from sharedPreferences
        and saves it in user_data variable with Map<String, ?> object as a type */
        Map<String, ?> user_data =  sharedPreferences.getAll();

        // if user_data object does not contain 'sessionTimes' key, returns void
        if (user_data.get("sessionTimes") == null) {
            return;
        }

        // changes data type of 'sessionTimes' value to int
        int sessionTimes = (int) user_data.get("sessionTimes");

        // checks if there are available sessions in order to move straight to ProfileActivity
        if (sessionTimes <= 0) {
            return;
        }

        // defines editor to make changes to sharedPreferences object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // subtracts 1 from sessionTimes and LoginActivity is already on the step closer :)
        editor.putInt("sessionTimes", sessionTimes - 1);

        // applies changes
        editor.apply();

        // redirects to ProfileActivity
        Intent switchActivityIntent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(switchActivityIntent);
    }

    public boolean validateCredentials(TextView username, TextView password, CheckBox rememberCheckBox, SharedPreferences sharedPreferences) {
        Map<String, ?> user_data =  sharedPreferences.getAll();

        // retrieves correct username and password from user_data object
        String correctUsername = (String) user_data.get("username");
        String correctPassword = (String) user_data.get("password");

        // if there are no correct username or password, then returns void
        if (correctPassword == null || correctUsername == null) {
            return false;
        }

        // if correct username does not equal provided username, returns void
        if (!correctUsername.equals(username.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
            return false;
        }

        // if correct password does not equal provided password, returns void
        if (!correctPassword.equals(password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        }

        // if rememberCheckBox is checked, creates 3 sessions, so there will not be need to login everytime
        if (rememberCheckBox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("sessionTimes", 3);
            editor.apply();
        }

        // returns true after all tests have been passed
        return true;
    }
}

