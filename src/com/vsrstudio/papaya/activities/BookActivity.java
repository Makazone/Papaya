package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.model.Book;

public class BookActivity extends Activity implements View.OnClickListener {

    private final Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setUpActionBar();

        Intent intent = getIntent();
        String objectId = intent.getStringExtra("object_id");

        ParseQuery<Book> query = ParseQuery.getQuery("Book");
        query.include("owner");
        query.getInBackground(objectId, new GetCallback<Book>() {
            public void done(Book book, ParseException e) {
                if (e == null) {
                    renderInfo(book);
                } else {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void renderInfo(Book book) {
        final TextView title = (TextView) findViewById(R.id.book_title);
        title.setTypeface(Papaya.robotoLight);
        title.setText(book.getTitle());

        final TextView authors = (TextView) findViewById(R.id.authors);
        authors.setTypeface(Papaya.robotoLight);
        authors.setText(book.getAuthors());

        final TextView genre = (TextView) findViewById(R.id.genre);
        genre.setTypeface(Papaya.robotoLight);
        genre.setText(book.getGenre());

        final TextView description = (TextView) findViewById(R.id.description);
        description.setTypeface(Papaya.robotoLight);
        description.setText(book.getDescription());

        final Button openInGoogleBook = (Button) findViewById(R.id.open_in_gbooks_button);
        openInGoogleBook.setTypeface(Papaya.robotoLight);
        if (book.getURL() == null) {
            openInGoogleBook.setVisibility(View.GONE);
        } else {
            openInGoogleBook.setContentDescription(book.getURL());
            openInGoogleBook.setOnClickListener(this);
        }

        final Button exchange = (Button) findViewById(R.id.exchange_button);
        exchange.setTypeface(Papaya.robotoLight);
        exchange.setContentDescription(book.getOwner().getObjectId());
        exchange.setOnClickListener(this);
    }

    private void setUpActionBar() {
        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar, null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        final TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(Papaya.robotoSlabRegular);
        title.setText(R.string.book);

        final ImageButton showMenuButton = (ImageButton) findViewById(R.id.show_menu);
        showMenuButton.setVisibility(View.GONE);

        final ImageButton backButton = (ImageButton) findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_in_gbooks_button:
                String url = String.valueOf(view.getContentDescription());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.exchange_button:
                Toast.makeText(context, "Exchange " + view.getContentDescription(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
