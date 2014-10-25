package com.vsrstudio.papaya.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

@ParseClassName("ParseUser")
public class User extends ParseUser {
    public static void createNewUser(String email, String pass) {
        User newUser = new User();
        newUser.setUsername(email);
        newUser.setPassword(pass);

        // other fields can be set just like with ParseObject

        newUser.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
