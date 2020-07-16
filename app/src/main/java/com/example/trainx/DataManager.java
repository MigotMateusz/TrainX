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
    public boolean checkLogin(String _login, String _password) {
        final String login = _login;
        final String password = _password;
        boolean isOk = false;
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("Count ", ""+snapshot.getChildrenCount());
                for(DataSnapshot postSnaposhot: snapshot.getChildren()){
                    User post = postSnaposhot.getValue(User.class);
                    String checkLogin = post.getUsername();
                    String checkPassword = post.getPassword();
                    if(login.equals(checkLogin) && password.equals(checkPassword))

                    Log.e("Get data", post.makeitString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });
        return false;
    }
}
