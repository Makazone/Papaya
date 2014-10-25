package com.vsrstudio.papaya.model;

import com.parse.LogInCallback;
import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

@ParseClassName("ParseUser")
public class User extends ParseUser {
    public static void createNewUser(String email, String pass, SignUpCallback callback) {
        User newUser = new User();
        newUser.setUsername(email);
        newUser.setPassword(pass);

        newUser.signUpInBackground(callback);
    }

    public static void logInUser(String email, String pass, LogInCallback callback) {
        User.logInInBackground(email, pass, callback);
    }
}

