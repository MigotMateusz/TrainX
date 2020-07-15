package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final DataManager dManager = DataManager.getInstance();
        Button button = findViewById(R.id.LogMeInButton);
        MaterialButton registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText loginInput = findViewById(R.id.LoginInputRegister);
                TextInputEditText passwordInput = findViewById(R.id.PasswordInputRegister);
                TextInputEditText emailInput = findViewById(R.id.EmailInputRegister);
                String login = loginInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                dManager.writeNewUser(login,email,password);
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            }
        });
    }
}