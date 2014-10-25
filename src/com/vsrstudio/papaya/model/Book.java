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
    public void createBookOffer(int type, int amount) {

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
            //return 0;
        }

        ArrayList<Book> book = new ArrayList<Book>();
        int iB = 0;
        // Output results.
        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            Book b = new Book();
            book.add(b);
            //Volume.SaleInfo saleInfo = volume.getSaleInfo();
            // Title.
            book.get(iB).setTitle(volumeInfo.getTitle());
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
            book.get(iB).setAuthors(strAuthors);
            // Description (if any).
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                book.get(iB).setDescription(volumeInfo.getDescription());
            }
            // Ratings (if any).
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
                book.get(iB).setRating(volumeInfo.getRatingsCount());
            }
            iB++;
        }

        callback.completedGoogleTask(book);
    }
}

