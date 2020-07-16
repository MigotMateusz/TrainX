package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button GoLoginScreen = findViewById(R.id.GoBackButton);
        MaterialButton sendButton = findViewById(R.id.SendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText emailInput = findViewById(R.id.ForgotEmailInput);
                String email = emailInput.getText().toString();

                Intent sendEmail = new Intent(Intent.ACTION_SEND);
                sendEmail.setType("message/rfc822");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "New password");
                sendEmail.putExtra(Intent.EXTRA_TEXT, "something something");
                try{
                    startActivity(Intent.createChooser(sendEmail, "Send mail..."));
                } catch(android.content.ActivityNotFoundException ex){
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
        GoLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            }
        });
    }
}