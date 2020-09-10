package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trainx.R;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SuccessRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);
        Button button = findViewById(R.id.SignOutButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SuccessRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String email = user.getEmail();

            MaterialTextView textView = findViewById(R.id.testUser);
            MaterialTextView textView2 = findViewById(R.id.testUser2);
            textView.setText(email);
        }
    }
}