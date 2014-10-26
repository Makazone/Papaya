package com.vsrstudio.papaya.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.activities.AddBookActivity;
import com.vsrstudio.papaya.adapters.BooksListAdapter;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.User;

import java.util.List;

public class MyBooksFragment extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    private Context context;
    private FloatingActionButton floatingActionButton;
    private ListView booksList;

    public MyBooksFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_books_list, container, false);
        context = rootView.getContext();

        booksList = (ListView) rootView.findViewById(R.id.books_list);
        booksList.setOnScrollListener(this);
        ParseQuery<Book> query = ParseQuery.getQuery("Book");
        query.whereEqualTo("owner", User.currentUser.getParseUser());
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Book>() {
            public void done(List<Book> books, ParseException e) {
                if (e == null) {
                    rootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    booksList.setAdapter(new BooksListAdapter(context, books));
                } else {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.add_book_button);
        floatingActionButton.setColorNormal(context.getResources().getColor(R.color.accent_orange_600));
        floatingActionButton.setColorPressed(context.getResources().getColor(R.color.accent_orange_400));
        floatingActionButton.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View view) {
        startAddBookActivity();
    }

    private void startAddBookActivity() {
        context.startActivity(new Intent(context, AddBookActivity.class));
    }

    private int mLastFirstVisibleItem;

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final ListView listView = booksList;

        if (view.getId() == listView.getId()) {
            final int currentFirstVisibleItem = listView.getFirstVisiblePosition();

            if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                floatingActionButton.hide();
            } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                floatingActionButton.show();
            }

            mLastFirstVisibleItem = currentFirstVisibleItem;
        }
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

}