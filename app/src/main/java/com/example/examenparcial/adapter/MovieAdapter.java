package com.example.examenparcial.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.examenparcial.R;
import com.example.examenparcial.models.Movies;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private List<Movies> lista;

    public MovieAdapter(Context context, List<Movies> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Movies movie = lista.get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.persona, null);
        }
        TextView txtNom = (TextView) view.findViewById(R.id.movietitle);
        txtNom.setText(movie.getTitulo());
        TextView txtdata = (TextView) view.findViewById(R.id.movieduration);
        txtdata.setText(" " + movie.getDuracion());

        return view;
    }


}
