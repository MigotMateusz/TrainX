package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button SignUpButton = findViewById(R.id.SignUpButton);
        Button ForgetPassButton = findViewById(R.id.ForgetPassButton);
        DataManager dManager = DataManager.getInstance();
        final MaterialButton loginButton = findViewById(R.id.LoginButton);
        final TextInputEditText loginInput = findViewById(R.id.LoginInput);
        final TextInputEditText passInput = findViewById(R.id.PasswordInput);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginInput.getText().toString();
                String password = passInput.getText().toString();
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            }
        });
        ForgetPassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            }
        });

    }
}