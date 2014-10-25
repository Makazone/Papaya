package com.vsrstudio.papaya.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.model.Book;

import java.util.List;

public class BooksListAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context context;
    private final List<Book> books;

    public BooksListAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_book, null);

        final TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setTypeface(Papaya.robotoLight);
        title.setText(books.get(i).getString("title"));

        final TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setTypeface(Papaya.robotoLight);
        author.setText(books.get(i).getString("authors"));

        final TextView genre = (TextView) convertView.findViewById(R.id.genre);
        genre.setTypeface(Papaya.robotoLight);
        genre.setText(books.get(i).getString("genre"));

        final RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
        ratingBar.setRating(books.get(i).getInt("rating"));

        convertView.setContentDescription(books.get(i).getObjectId());
        convertView.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View view) {
        Toast.makeText(context, view.getContentDescription(), Toast.LENGTH_SHORT).show();
    }

    public int getCount() {
        return books.size();
    }

    public Object getItem(int i) {
        return books.get(i);
    }

    public long getItemId(int i) {
        return i;
    }
}
