package com.example.trainx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataManager {
    private DatabaseReference database;
    private static final DataManager manager = new DataManager();
    private DataManager() {
        database = FirebaseDatabase.getInstance().getReference("users");
    }
    public static DataManager getInstance(){
        return manager;
    }
    public void writeNewUser(String login, String email, String pass) {
        User newUser = new User(login, email, pass);
        String Key = database.push().getKey();
        database.child(Key).setValue(newUser);
    }
}
