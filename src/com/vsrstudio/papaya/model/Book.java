package com.vsrstudio.papaya.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

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
