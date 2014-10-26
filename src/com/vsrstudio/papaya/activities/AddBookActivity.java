package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.adapters.SearchGBooksAdapter;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.GoogleCallback;

import java.util.ArrayList;

public class AddBookActivity extends Activity implements View.OnClickListener {

    private static ArrayList<Book> bookList = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        setUpActionBar();

        showSearchGBooks();
    }

    public void showSearchGBooks() {
        getFragmentManager().beginTransaction().replace(R.id.container, new SearchGBooksFragment()).commit();
    }

    public void showBookInfoFragment(int position) {
        getFragmentManager().beginTransaction().replace(R.id.container, new BookInfoFragment()).commit();
    }

    public static class BookInfoFragment extends Fragment {
        public BookInfoFragment() {
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_books_list, container, false);

            //TODO

            return rootView;
        }
    }

    public static class SearchGBooksFragment extends Fragment implements View.OnClickListener {

        private TextView searchText;
        private Context context;
        private ListView resultsList;

        public SearchGBooksFragment() {
            super();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
            context = rootView.getContext();

            resultsList = (ListView) rootView.findViewById(R.id.genres_list);
            View searchField = inflater.inflate(R.layout.search_field, null);
            setUpSearchField(searchField);
            resultsList.addHeaderView(searchField);
            resultsList.setAdapter(new SearchGBooksAdapter(context, null, (AddBookActivity) getActivity()));

            return rootView;
        }

        private void setUpSearchField(View searchField) {
            searchText = (TextView) searchField.findViewById(R.id.search_request);
            searchText.setTypeface(Papaya.robotoLight);

            final ImageButton searchButton = (ImageButton) searchField.findViewById(R.id.search_button);
            searchButton.setOnClickListener(this);
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_button:
                    try {
                        Book.findBooksByString(String.valueOf(searchText.getText()), new GoogleCallback<Book>() {
                            public void completedGoogleTask(ArrayList<Book> books) {
                                Papaya.hideKeyboard(getActivity());
                                bookList = books;
                                resultsList.setAdapter(new SearchGBooksAdapter(context, bookList, (AddBookActivity) getActivity()));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
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
        title.setText(R.string.add_book);

        final ImageButton showMenuButton = (ImageButton) findViewById(R.id.show_menu);
        showMenuButton.setVisibility(View.GONE);

        final ImageButton backButton = (ImageButton) findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
