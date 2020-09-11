package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.trainx.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        prepareUIElements();
    }

    public void sendPasswordResetEmail(String emailAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                        Toast.makeText(ForgotPasswordActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                });
    }

    private void changeToLoginActivity() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
    }

    private void prepareUIElements(){
        MaterialButton sendButton = findViewById(R.id.SendButton);
        sendButton.setOnClickListener(view -> {
            TextInputEditText emailInput = findViewById(R.id.ForgotEmailInput);
            String email = Objects.requireNonNull(emailInput.getText()).toString();
            sendPasswordResetEmail(email);
        });

        Button GoLoginScreen = findViewById(R.id.GoBackButton);
        GoLoginScreen.setOnClickListener(view -> changeToLoginActivity());
    }

}