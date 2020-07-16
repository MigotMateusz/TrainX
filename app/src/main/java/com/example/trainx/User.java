package com.example.trainx;

public class User {
    public String username;
    public String email;
    public String password;

    public User(){

    }

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public String makeitString(){
        String i = "Username: " + username + ", email: " + email + ", password: " + password;
        return i;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
