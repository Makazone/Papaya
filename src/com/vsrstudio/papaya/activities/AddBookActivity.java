package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.adapters.SearchGBooksAdapter;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.GoogleCallback;

import java.util.ArrayList;

public class AddBookActivity extends Activity implements View.OnClickListener {

    private static ArrayList<Book> bookList = null;
    private static int bookPosition = 0;
    private BookInfoFragment bookInfoFragment;

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
        bookPosition = position;
        final ImageButton okButton = (ImageButton) findViewById(R.id.ok);
        okButton.setVisibility(View.VISIBLE);
        okButton.setOnClickListener(this);

        bookInfoFragment = new BookInfoFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, bookInfoFragment).commit();
    }

    public static class BookInfoFragment extends Fragment {

        private EditText title;
        private EditText author;
        private EditText genre;
        private EditText description;

        public String getTitle() {
            return String.valueOf(title.getText());
        }

        public String getAuthor() {
            return String.valueOf(author.getText());
        }

        public String getGenre() {
            return String.valueOf(genre.getText());
        }

        public String getDescription() {
            return String.valueOf(description.getText());
        }

        public BookInfoFragment() {
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_book_info, container, false);

            Book book = bookList.get(bookPosition);

            title = (EditText) rootView.findViewById(R.id.title);
            title.setTypeface(Papaya.robotoLight);
            title.setText(book.getTitle());

            author = (EditText) rootView.findViewById(R.id.authors);
            author.setTypeface(Papaya.robotoLight);
            author.setText(book.getAuthors());

            genre = (EditText) rootView.findViewById(R.id.genre);
            genre.setTypeface(Papaya.robotoLight);
            genre.setText(book.getGenre());

            description = (EditText) rootView.findViewById(R.id.description);
            description.setTypeface(Papaya.robotoLight);
            description.setText(book.getDescription());

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

    private void addNewBook() {
        Book book = new Book();
        book.setTitle(bookInfoFragment.getTitle());
        book.setAuthors(bookInfoFragment.getAuthor());
        book.setGenre(bookInfoFragment.getGenre());
        book.setDescription(bookInfoFragment.getDescription());
        //TODO
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
            case R.id.ok:
                addNewBook();
                break;
        }
    }
}
