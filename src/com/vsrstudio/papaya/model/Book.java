package com.vsrstudio.papaya.model;

import android.util.Log;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Book")
public class Book extends ParseObject {
    public Book() { }

    public Book(String title, String authors, String genre,  String descr, String url, int rating) {
        setRating(rating);
        setTitle(title);
        setDescription(descr);
        setURL(url);
        setAuthors(authors);
        setGenre(genre);
    }

    public static Book[] findBooksByString(String query) {
        return null;
    }

    public static void getBooksForOwner(ParseUser owner, final FindCallback callback) {
        ParseQuery<Book> query = ParseQuery.getQuery("Book");
        query.whereEqualTo("owner", owner);

        final ArrayList<Book> books = new ArrayList<Book>();

        query.findInBackground(new FindCallback<Book>() {
            public void done(List<Book> scoreList, ParseException e) {
                if (e == null) {
                    callback.done(scoreList, e);
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                    for (Book book : scoreList) {
//                        books.add((Book)book);
//                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public ParseUser getOwner() {
        return (ParseUser)getParseObject("owner");
    }

    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public int getRating() {
        return getInt("rating");
    }

    public void setRating(double rating) {
        put("rating", rating);
    }
    public String getGenre() {
        return getString("genre");
    }

    public void setGenre(String genre) {
        put("genre", genre);
    }

    public String getURL() {
        return getString("book_url");
    }

    public void setURL(String url) {
        put("book_url", url);
    }

    public String getAuthors() {
        return getString("authors");
    }

    public void setAuthors(String authors) {
        put("authors", authors);
    }

    public void setDescription(String descr) {
        put("descr", descr);
    }

    public String getDescription() {
        return getString("descr");
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }
}
