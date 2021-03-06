package com.vsrstudio.papaya.model;

import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.parse.*;

import java.io.IOException;
import java.util.ArrayList;

@ParseClassName("Book")
public class Book extends ParseObject {
    public Book() {
    }

    public Book(String title, String authors, String genre, String descr, String url, int rating) {
        setRating(rating);
        setTitle(title);
        setDescription(descr);
        setURL(url);
        setAuthors(authors);
        setGenre(genre);
    }

    public static void findBooksByGenre(String genre, final FindCallback<Book> callback) {
        ParseQuery<Book> query = ParseQuery.getQuery("Book");
        query.whereEqualTo("genre", genre);

        query.findInBackground(new FindCallback<Book>() {
            public void done(java.util.List<Book> books, ParseException e) {
                if (e == null) {
                    callback.done(books, e);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void findBooksByString(String query, final GoogleCallback<Book> callback) throws Exception {
        new RetrieveTask().execute(query, callback);
    }

    public static void getBooksForOwner(ParseUser owner, final FindCallback callback) {
        ParseQuery<Book> query = ParseQuery.getQuery("Book");
        query.whereEqualTo("owner", owner);

        final ArrayList<Book> books = new ArrayList<Book>();

        query.findInBackground(new FindCallback<Book>() {
            public void done(java.util.List<Book> scoreList, ParseException e) {
                if (e == null) {
                    callback.done(scoreList, e);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    /**
     * type = 0 money + exchange
     * type = 1 money
     * type = 2 exchange
     * @param type
     */
    public void createBookOffer(int type, int amount){
        setType(type);
        setAmount(amount);
        saveInBackground();
    }

    public ParseUser getOwner() {
        return (ParseUser) getParseObject("owner");
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

    public void setType(int type){
        put("type",type);
    }
    public int getType(int type){
        return getInt("type");
    }

    public void setAmount(int amount){
        put("amount",amount);
    }
    public int getAmount(int amount){
        return getInt("amount");
    }
}

class RetrieveTask extends AsyncTask<Object, Void, Void> {

    private Exception exception;
    private Volumes volumes;
    private GoogleCallback<Book> callback;

    protected Void doInBackground(Object... params) {
        final String query = (String)params[0];
        callback = (GoogleCallback<Book>)params[1];
        try {
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            Books books = new Books.Builder(AndroidHttp.newCompatibleTransport(), jsonFactory, null)
                    .setApplicationName("Papaya")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer("AIzaSyBZfg6IF90p8ZiBB2ejokBv1ETscQkq7jY"))
                    .build();
            // Set query string and filter only Google eBooks.
            System.out.println("Query: [" + query + "]");
            final List volumesList = books.volumes().list("intitle:"+query);
            volumes = volumesList.execute();
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        int N = volumes.getTotalItems();

        Log.d("ALBINA____________________________", N+"");

        if (N == 0 || volumes.getItems() == null) {
            callback.completedGoogleTask(null);
            return;
        }

        ArrayList<Book> book = new ArrayList<Book>();
        int iB = 0;
        // Output results.
        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            Book b = new Book();

            // Author(s).
            java.util.List<String> authors = volumeInfo.getAuthors();
            String strAuthors = "";
            if (authors != null && !authors.isEmpty()) {
                for (int i = 0; i < authors.size(); ++i) {
                    strAuthors += authors.get(i);
                    if (i < authors.size() - 1) {
                        strAuthors += ", ";
                    }
                }
            }

            // Categories
            java.util.List<String> categories = volumeInfo.getCategories();
            String categoryStr = "";
            if (categories != null && !categories.isEmpty()) {
                for (int i = 0; i < categories.size(); ++i) {
                    categoryStr += categories.get(i);
                    if (i < categories.size() - 1) {
                        categoryStr += ", ";
                    }
                }
            }

            b.setGenre(categoryStr);
            b.setAuthors(strAuthors);
            b.setTitle(volumeInfo.getTitle());

            System.out.println(volumeInfo.getImageLinks());
//            String bookImg = volumeInfo.getImageLinks().getSmall();
//            System.out.println("IMAGE - " + bookImg);

            // Description (if any).
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                b.setDescription(volumeInfo.getDescription());
            }
            // Ratings (if any).
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
                b.setRating(volumeInfo.getRatingsCount());
            }

            book.add(b);
            iB++;
        }

        callback.completedGoogleTask(book);
    }
}

