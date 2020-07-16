package com.example.trainx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DataManager dManager = DataManager.getInstance();
        final MaterialButton loginButton = findViewById(R.id.LoginButton);
        final TextInputEditText loginInput = findViewById(R.id.LoginInput);
        final TextInputEditText passInput = findViewById(R.id.PasswordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginInput.getText().toString();
                String password = passInput.getText().toString();
                signIn(login, password);
                //changeActivitySuccessLogin();
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

    private void changeActivitySuccessLogin(){
        Intent intent = new Intent(LoginActivity.this, SuccessRegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
    }
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        changeActivitySuccessLogin();
    }
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            changeActivitySuccessLogin();
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}