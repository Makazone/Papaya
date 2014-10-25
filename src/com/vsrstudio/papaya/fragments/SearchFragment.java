package com.vsrstudio.papaya.fragments;

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
import com.vsrstudio.papaya.adapters.GenresListAdapter;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView searchText;

    public SearchFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        context = rootView.getContext();

        final ListView genresList = (ListView) rootView.findViewById(R.id.genres_list);
        View searchField = inflater.inflate(R.layout.search_field, null);
        setUpSearchField(searchField);
        genresList.addHeaderView(searchField);
        genresList.setAdapter(new GenresListAdapter(context));

        return rootView;
    }

    private void setUpSearchField(View searchField) {
        searchText = (TextView) searchField.findViewById(R.id.search_request);
        searchText.setTypeface(Papaya.robotoLight);

        final ImageButton searchButton = (ImageButton) searchField.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
    }

    private void search(String searchRequest) {
        //TODO
        Toast.makeText(context, "Search for " + searchRequest, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                final String searchRequest = String.valueOf(searchText.getText());
                search(searchRequest);
                break;
        }
    }

}