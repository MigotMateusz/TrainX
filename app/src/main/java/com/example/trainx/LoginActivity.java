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
        final MaterialButton loginButton = findViewById(R.id.LoginButton);
        final TextInputEditText loginInput = findViewById(R.id.LoginInput);
        final TextInputEditText passInput = findViewById(R.id.PasswordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginInput.getText().toString();
                String password = passInput.getText().toString();
                signIn(login, password);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeActivity(RegisterActivity.class);
            }
        });
        ForgetPassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeActivity(ForgotPasswordActivity.class);
            }
        });

    }

    private void changeActivity(Class<?> cls){
        Intent intent = new Intent(LoginActivity.this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        changeActivity(SuccessRegisterActivity.class);
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            changeActivity(SuccessRegisterActivity.class);
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}