/*
 * Class Name: CounterActivity
 *
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) My Copyright, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.uofa.rdefo.deforest_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Main interface for the CountBook app
 */
public class CounterActivity extends AppCompatActivity {

    // strings used to put/get data from intents
    public static final String EXTRA_NAME = "com.defo.countbook.MESSAGE";
    public static final String EXTRA_INITVALUE = "com.defo.countbook.INITVALUE";
    public static final String EXTRA_COMMENT = "com.defo.countbook.COMMENT";
    public static final String EXTRA_POSITION = "com.defo.countbook.POSITION";
    public static final String EXTRA_ENAME = "com.defo.countbook.EMESSAGE";
    public static final String EXTRA_EINITVALUE = "com.defo.countbook.EINITVALUE";
    public static final String EXTRA_ECURVALUE = "com.defo.countbook.ECURVALUE";
    public static final String EXTRA_ECOMMENT = "com.defo.countbook.ECOMMENT";
    public static final String EXTRA_DATE = "com.defo.countbook.DATE";
    private static final String FILENAME = "file.sav"; // name of file to save/load data from
    // values used to verify which activity result came back
    private static final int ACTIVITY_CREATE_CONSTANT = 1;
    public static final int ACTIVITY_EDIT_CONSTANT = 2;

    ArrayList<Counter> list;
    CounterInterface adapter;

    /**
     * Called when the activity is first created, will load and display any counters saved
     * from before as well as set the functionality for the create button
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Button createButton = (Button) findViewById(R.id.create_btn);
        loadFromFile();
        updateList();
        updateCount();
        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                saveInFile();
                Intent intent = new Intent(CounterActivity.this, CreateActivity.class);
                startActivityForResult(intent, ACTIVITY_CREATE_CONSTANT);
            }
        });
    }

    /**
     * Updates the display for total number of counters
     */
    public void updateCount() {
        TextView totalCounters = (TextView) findViewById(R.id.totalCount);
        totalCounters.setText(getResources().getString(R.string.count_total,
                list.size()));
    }

    /**
     * Sets the custom adapter to the listview object to display the custom view for counters
     */
    private void updateList() {
        ListView lView = (ListView) findViewById(R.id.counterView);
        adapter = new CounterInterface(list, this);
        lView.setAdapter(adapter);
    }

    /**
     * Uses gson to deserialize the counter list from a saved file
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            list = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            list = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Uses gson to serialize the counter list and writes to a file
     */
    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(list,writer);

            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Takes the results from the Edit or Create activity and either modifies or creates a
     * new counter in the list
     * @param requestCode id from which activity returned
     * @param resultCode id if activity returned ok
     * @param data returned variables
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_CREATE_CONSTANT) {
            if (data.hasExtra(EXTRA_NAME)) {
                String name;
                String initValue;
                String comment;
                int iValue;

                name = data.getExtras().getString(EXTRA_NAME);
                initValue = data.getExtras().getString(EXTRA_INITVALUE);
                comment = data.getExtras().getString(EXTRA_COMMENT);
                iValue = Integer.parseInt(initValue);

                loadFromFile();

                if (comment.trim().length() == 0) {
                   list.add(new Counter(name,iValue));
                }
                else {
                    list.add(new Counter(name,iValue,comment));
                }
                updateCount();
                updateList();
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        }

        else  if (resultCode == RESULT_OK && requestCode == ACTIVITY_EDIT_CONSTANT) {
            if (data.hasExtra(EXTRA_ENAME)) {
                String name;
                String initValue;
                String curValue;
                String comment;
                int iValue;
                int cValue;
                int position;

                name = data.getExtras().getString(EXTRA_ENAME);
                initValue = data.getExtras().getString(EXTRA_EINITVALUE);
                curValue = data.getExtras().getString(EXTRA_ECURVALUE);
                comment = data.getExtras().getString(EXTRA_ECOMMENT);
                position = data.getExtras().getInt(EXTRA_POSITION);

                iValue = Integer.parseInt(initValue);
                cValue = Integer.parseInt(curValue);

                loadFromFile();
                list.get(position).setName(name);
                list.get(position).setInitValue(iValue);
                list.get(position).setCurValue(cValue);
                list.get(position).setComment(comment);

                updateList();
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
