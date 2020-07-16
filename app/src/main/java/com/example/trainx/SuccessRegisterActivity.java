package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SuccessRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();

            MaterialTextView textView = findViewById(R.id.testUser);
            textView.setText(email);
        }
    }
}