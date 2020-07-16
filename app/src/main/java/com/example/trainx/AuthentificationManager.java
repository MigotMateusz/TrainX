package com.example.trainx;

import com.google.firebase.auth.FirebaseAuth;

public class AuthentificationManager {
    private FirebaseAuth mAuth;

    public AuthentificationManager() {
        mAuth = FirebaseAuth.getInstance();
    }


}
