package com.example.trainx.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trainx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });
    }
    private void changeActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
    }
    public void createAccount(final String login, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUsertoDatabase(user.getUid(), login);
                            sendEmailVerification();
                            makeSuccessDialog();
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(login)
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Log.d("TAG", "User profile updated.");
                    }
                });*/
    }
    public void makeSuccessDialog(){
        new MaterialAlertDialogBuilder(RegisterActivity.this)
                .setTitle(R.string.SuccessRegiser)
                .setMessage(R.string.ThankyouRegiser)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeActivity();
                    }
                })
                .show();
    }

    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                            Toast.makeText(RegisterActivity.this, "Email verification sent.", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }
    public void addUsertoDatabase(String username, String uid){
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference().child("users");
        ref.child(uid).setValue(username);
    }
}