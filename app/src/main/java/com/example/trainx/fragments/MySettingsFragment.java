package com.example.trainx.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.trainx.R;
import com.example.trainx.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MySettingsFragment extends PreferenceFragmentCompat {
    private String password = "";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        prepareDarkModeSwitch();
        prepareNotificationSwitch();
        //prepareFeedbackPreference();
        prepareLogOutPreference();
        prepareChangeEmailPreference();
        prepareAuthorWebsitePreference();
        prepareAboutPreference();
    }

    private void prepareDarkModeSwitch() {
        SwitchPreferenceCompat darkModeSwitch = (SwitchPreferenceCompat) findPreference("darkmode");
        darkModeSwitch.setChecked(true);
        darkModeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(darkModeSwitch.isChecked()){
                    Toast.makeText(getActivity(), "Light Mode On", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkModeSwitch.setChecked(false);
                }

                else{
                    Toast.makeText(getActivity(), "Dark Mode On", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkModeSwitch.setChecked(true);
                }

                return false;
            }
        });
    }

    private void prepareNotificationSwitch(){
        SwitchPreferenceCompat notificationSwitch = (SwitchPreferenceCompat) findPreference("notifications");
        notificationSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(notificationSwitch.isChecked()){
                    Toast.makeText(getActivity(), "Notifications are off", Toast.LENGTH_SHORT).show();
                    notificationSwitch.setChecked(false);
                }

                else{
                    Toast.makeText(getActivity(), "Notifications are on", Toast.LENGTH_SHORT).show();
                    notificationSwitch.setChecked(true);
                }
                return false;
            }
        });
    }
/*
    private void prepareFeedbackPreference(){
        Preference feedbackPreference = (Preference) findPreference("feedback");
        feedbackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Feedback clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }*/

    private void prepareLogOutPreference(){
        Preference logOutPreference = (Preference) findPreference("LogOut");
        logOutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return false;
            }
        });
    }

    private void prepareChangeEmailPreference(){
        Preference changeEmailPreference = (Preference) findPreference("Email");
        changeEmailPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                openPasswordDialog(user);
                return false;
            }
        });
    }

    private void prepareAboutPreference() {
        Preference aboutPreference = (Preference) findPreference("appInfo");
        aboutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MigotMateusz/TrainX"));
                startActivity(browserIntent);
                return false;
            }
        });
    }

    private void prepareAuthorWebsitePreference() {
        Preference websitePreference = (Preference) findPreference("Author");
        websitePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mateuszmigot.pl/"));
                startActivity(browserIntent);
                return false;
            }
        });
    }

    private void openPasswordDialog(FirebaseUser user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm email change");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                password = input.getText().toString();
                updateUserEmail(user);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    private void updateUserEmail(FirebaseUser user){
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG", "User re-authenticated.");
                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                        user1.updateEmail("text@gmail.com")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Log.d("TAG", "User email updated");
                                        Toast.makeText(getActivity(), "Changed email to: " + user1.getEmail(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }


}
