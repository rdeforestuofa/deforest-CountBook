/*
 * Class Name: CounterInterface
 *
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) My Copyright, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.uofa.rdefo.deforest_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Custom view and interface for displaying all counters and functionality for each
 */

public class CounterInterface extends BaseAdapter implements ListAdapter {
    private ArrayList<Counter> list = new ArrayList<Counter>();
    private final Context context;

    /**
     * Creates a custom adapter to display the details and functions for the counter in the list
     * @param list list of counters to display
     * @param context activity that created this adapter
     */

    public CounterInterface(ArrayList<Counter> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * Setups the custom view to display the list of counters given by a custom xml view and
     * populates each view with the details of that counter and the button functions as well
     * @param position index of counter in list
     * @param convertView
     * @param parent
     * @return view object to display
     */

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_counter, null);
        }

        //Handle TextView and display attributes from counter
        TextView listItemName = (TextView) view.findViewById(R.id.counter_name);
        TextView listItemDate = (TextView) view.findViewById(R.id.counter_date);
        TextView listItemCount = (TextView) view.findViewById(R.id.count);
        listItemName.setText(list.get(position).getName());
        listItemDate.setText(list.get(position).getDate());
        listItemCount.setText(String.valueOf(list.get(position).getCurValue()));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
        Button editBtn = (Button) view.findViewById(R.id.edit_btn);
        Button resetBtn = (Button) view.findViewById(R.id.reset_btn);
        Button incBtn = (Button) view.findViewById(R.id.increment_btn);
        Button decBtn = (Button) view.findViewById(R.id.decrement_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                ((CounterActivity)context).updateCount();
                ((CounterActivity)context).saveInFile();
                notifyDataSetChanged();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditActivity.class);
                String name = list.get(position).getName();
                int initValue = list.get(position).getInitValue();
                int curValue = list.get(position).getCurValue();
                String comment = list.get(position).getComment();
                String date = list.get(position).getDate();

                intent.putExtra(CounterActivity.EXTRA_ENAME, name);
                intent.putExtra(CounterActivity.EXTRA_EINITVALUE, initValue);
                intent.putExtra(CounterActivity.EXTRA_ECURVALUE, curValue);
                intent.putExtra(CounterActivity.EXTRA_ECOMMENT, comment);
                intent.putExtra(CounterActivity.EXTRA_DATE, date);
                intent.putExtra(CounterActivity.EXTRA_POSITION, position);
                ((CounterActivity)context).saveInFile();
                ((Activity)context).startActivityForResult(intent,
                        CounterActivity.ACTIVITY_EDIT_CONSTANT);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).resCounter();
                ((CounterActivity)context).saveInFile();
                notifyDataSetChanged();
            }
        });

        incBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).incCounter();
                ((CounterActivity)context).saveInFile();
                notifyDataSetChanged();
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).decCounter();
                ((CounterActivity)context).saveInFile();
                notifyDataSetChanged();
            }
        });

        return view;
    }
}