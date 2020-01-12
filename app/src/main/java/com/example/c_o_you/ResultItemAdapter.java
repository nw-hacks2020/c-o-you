package com.example.c_o_you;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class ResultItemAdapter extends ArrayAdapter<ResultItem> {

    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewIngredients;
        TextView textViewCo2;
    }

    private List<ResultItem> items;
    private Context context;

    public ResultItemAdapter(@NonNull Context context, List<ResultItem> items) {
        super(context, R.layout.result_card_layout, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //todo check
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.result_card_layout, parent, false);
            viewHolder.textViewTitle = view.findViewById(R.id.dish_title);
            viewHolder.textViewIngredients = view.findViewById(R.id.dish_ingredients);
            viewHolder.textViewCo2 = view.findViewById(R.id.dish_co2);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ResultItem resultItem = items.get(position);
        viewHolder.textViewTitle.setText(resultItem.getTitle());
        viewHolder.textViewIngredients.setText(String.join(", ", resultItem.getIngredients()));
        viewHolder.textViewCo2.setText(String.valueOf(resultItem.getCo2()));
        return view;
    }

}
