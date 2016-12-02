package com.example.albert.todo;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoArrayAdapter extends ArrayAdapter<ToDoItem> {
    private final Context context;
    private final ArrayList<ToDoItem> values;

    public ToDoArrayAdapter(Context context, ArrayList<ToDoItem> values) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        textView.setText(values.get(position).getTaskName());
        // change the icon for Windows and iPhone
        if (values.get(position).isDone()){
            textView.setBackgroundColor(Color.BLUE);
        }else{
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        return rowView;
    }
}

