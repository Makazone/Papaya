package com.vsrstudio.papaya.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.vsrstudio.papaya.model.Book;

import java.util.ArrayList;

public class LastListAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Book> books;

    public LastListAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {


        return convertView;
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
