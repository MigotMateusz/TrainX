package com.example.trainx.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainx.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        onStart();

        Button SignUpButton = findViewById(R.id.SignUpButton);
        Button ForgetPassButton = findViewById(R.id.ForgetPassButton);
        final MaterialButton loginButton = findViewById(R.id.LoginButton);
        final TextInputEditText loginInput = findViewById(R.id.LoginInput);
        final TextInputEditText passInput = findViewById(R.id.PasswordInput);

        loginButton.setOnClickListener(view -> {
            TextInputLayout loginField = findViewById(R.id.LoginField);
            TextInputLayout passwordField = findViewById(R.id.passwordField);
            loginField.setError(null);
            passwordField.setError(null);
            String login = loginInput.getText().toString();
            if(login.matches("")){
                loginField.setError("This field cannot be empty!");
            }
            String password = passInput.getText().toString();
            if(password.matches("")){
                passwordField.setError("This field cannot be empty!");
            }
            if(!password.matches("") && !login.matches(""))
                signIn(login, password);
        });

        SignUpButton.setOnClickListener(view -> changeActivity(RegisterActivity.class));
        ForgetPassButton.setOnClickListener(view -> changeActivity(ForgotPasswordActivity.class));

    }

    private void changeActivity(Class<?> cls){
        Intent intent = new Intent(LoginActivity.this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            changeActivity(MainActivity.class);
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        changeActivity(MainActivity.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}