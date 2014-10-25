package com.vsrstudio.papaya.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;

import java.util.Arrays;
import java.util.List;

public class GenresListAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context context;
    private final List<String> genres;

    public GenresListAdapter(Context context) {
        this.context = context;
        genres = Arrays.asList(context.getResources().getStringArray(R.array.genres));
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_genre, null);

        final TextView genreName = (TextView) convertView.findViewById(R.id.genre_name);
        genreName.setTypeface(Papaya.robotoLight);
        genreName.setText(genres.get(i));

        convertView.setOnClickListener(this);

        return convertView;
    }

    public int getCount() {
        return genres.size();
    }

    public Object getItem(int i) {
        return genres.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public void onClick(View view) {
        //TODO
        Toast.makeText(context, ((TextView) view.findViewById(R.id.genre_name)).getText(), Toast.LENGTH_SHORT).show();
    }
}
