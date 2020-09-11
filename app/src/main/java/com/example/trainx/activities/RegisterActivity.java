package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.trainx.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = findViewById(R.id.LogMeInButton);
        MaterialButton registerButton = findViewById(R.id.RegisterButton);
        final TextInputEditText loginInput = findViewById(R.id.LoginInputRegister);
        final TextInputEditText passwordInput = findViewById(R.id.PasswordInputRegister);
        final TextInputEditText emailInput = findViewById(R.id.EmailInputRegister);

        registerButton.setOnClickListener(view -> {
            TextInputLayout loginField = findViewById(R.id.LoginFieldRegister);
            TextInputLayout emailFiled = findViewById(R.id.EmailFieldRegister);
            TextInputLayout passwordField = findViewById(R.id.passwordFieldRegister);
            loginField.setError(null);
            emailFiled.setError(null);
            passwordField.setError(null);
            String login = loginInput.getText().toString();
            if(login.matches(""))
                loginField.setError("Text field cannot be empty!");
            String email = emailInput.getText().toString();
            if(email.matches(""))
                emailFiled.setError("Text field cannot be empty!");
            String password = passwordInput.getText().toString();
            if(password.matches(""))
                passwordField.setError("Text field cannot be empty!");
            if(!login.matches("") && !password.matches("") && !email.matches(""))
                createAccount(login, email, password);
        });

        button.setOnClickListener(view -> changeActivity());
    }
    private void changeActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
    }
    public void createAccount(final String login, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        addUserToDatabase(user.getUid(), login);
                        sendEmailVerification();
                        makeSuccessDialog();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void makeSuccessDialog(){
        new MaterialAlertDialogBuilder(RegisterActivity.this)
                .setTitle(R.string.SuccessRegiser)
                .setMessage(R.string.ThankyouRegiser)
                .setPositiveButton("Ok", (dialogInterface, i) -> changeActivity())
                .show();
    }

    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Email verification sent.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addUserToDatabase(String username, String uid){
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference().child("users");
        ref.child(uid).setValue(username);
    }
}