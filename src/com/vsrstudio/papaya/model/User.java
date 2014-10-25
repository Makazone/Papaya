package com.vsrstudio.papaya.model;

import com.parse.*;

import java.util.ArrayList;

public class User {
    public static User currentUser;
    private ParseUser parseUser;

    public static void createNewUser(String email, String pass, SignUpCallback callback) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(email);
        newUser.setPassword(pass);

        newUser.signUpInBackground(callback);
    }

    public static void logInUser(String email, String pass, final LogInCallback callback) {
        ParseUser.logInInBackground(email, pass, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                currentUser = new User();
                currentUser.parseUser = parseUser;
                callback.done(parseUser, e);
            }
        });
    }

    public void logOut() {
        parseUser.logOut();
        parseUser = null;
    }

    public void findUserBooks(FindCallback callback) {
        Book.getBooksForOwner(currentUser.parseUser, callback);
    }

    public ParseUser getParseUser() { return parseUser; }
}

