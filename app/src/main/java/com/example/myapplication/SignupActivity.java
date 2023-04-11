package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

// this is the boilerplate of every android studio's project
// beginning of the boilerplate
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // end

        // declares elements from activity_signup
        TextView username = (TextView) findViewById(R.id.usernameSignup);
        TextView password = (TextView) findViewById(R.id.passwordSignup);
        MaterialButton signupButton = (MaterialButton) findViewById(R.id.signupBtn);
        TextView moveToLogin = (TextView) findViewById(R.id.movetoLogin);

        // listens on signupButton's click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks if username field is not empty
                if (username.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Username is not provided", Toast.LENGTH_SHORT).show();
                    return;
                }

                // checks if password field is not empty
                if (password.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Password is not provided", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validates the provided password
                if (!isPasswordValid(password.toString())) {
                    return;
                }

                // adds user's credentials to the SharedPreferences object
                signUpUser(username, password);

                // the message pops up saying that signing up went successfully
                Toast.makeText(SignupActivity.this, "USER IS SIGNED UP", Toast.LENGTH_SHORT).show();

                // redirects to LoginActivity after successful signing up
                Intent switchActivityIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        // listens on moveToLogin click
        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

//    public boolean isPasswordValid(TextView passw) {
//        return passw.length() >= 8;
//    }

    public static boolean isPasswordValid(String password) {
        // validates if there are lowercase and uppercase letters
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
        }

        // validates if password is not less than 6 chars
        boolean isMin6Length = password.length() >= 6;

        // validates if there is at least one digit
        boolean hasDigit = password.matches(".*\\d.*");

        // validates if there is at least one symbol
        boolean hasSymbol = password.matches(".*[+\\-*/].*");

        // if all tests have been passed, returns true
        return hasLowercase && hasUppercase && hasDigit && hasSymbol && isMin6Length;
    }

    public void signUpUser(TextView username, TextView password) {
        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();
    }
}
