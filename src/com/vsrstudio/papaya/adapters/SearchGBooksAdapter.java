package com.vsrstudio.papaya.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.activities.AddBookActivity;
import com.vsrstudio.papaya.model.Book;

import java.util.ArrayList;

public class SearchGBooksAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context context;
    private final ArrayList<Book> books;
    private final AddBookActivity activity;

    public SearchGBooksAdapter(Context context, ArrayList<Book> books, AddBookActivity activity) {
        this.context = context;
        this.books = books;
        this.activity = activity;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_book, null);

        final TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setTypeface(Papaya.robotoLight);
        title.setText(books.get(i).getTitle());

        final TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setTypeface(Papaya.robotoLight);
        author.setText(books.get(i).getAuthors());

        final TextView genre = (TextView) convertView.findViewById(R.id.genre);
        genre.setTypeface(Papaya.robotoLight);
        genre.setText(books.get(i).getGenre());

        convertView.setContentDescription(i + "");
        convertView.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View view) {
        activity.showBookInfoFragment(Integer.parseInt(String.valueOf(view.getContentDescription())));
    }

    public int getCount() {
        return (books == null) ? 0 : books.size();
    }

    public Object getItem(int i) {
        return books.get(i);
    }

    public long getItemId(int i) {
        return i;
    }
}
