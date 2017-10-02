/*
 * Class Name: EditActivity
 *
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) My Copyright, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.uofa.rdefo.deforest_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Interface for editing counters in CountBook
 */

public class EditActivity extends AppCompatActivity {

    private EditText bodyName;
    private EditText bodyIV;
    private EditText bodyCV;
    private EditText bodyComment;
    private TextView bodyDate;

    /**
     * Takes the given variables from the intent to display the information of the given counter
     * and sets the functionality for the save button to return the edited attributes of the
     * counter to the main interface
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String editName = intent.getStringExtra(CounterActivity.EXTRA_ENAME);
        int editCurValue = intent.getIntExtra(CounterActivity.EXTRA_ECURVALUE, 0);
        int editInitValue = intent.getIntExtra(CounterActivity.EXTRA_EINITVALUE, 0);
        String editComment = intent.getStringExtra(CounterActivity.EXTRA_ECOMMENT);
        String viewDate = intent.getStringExtra(CounterActivity.EXTRA_DATE);
        final int position = intent.getIntExtra(CounterActivity.EXTRA_POSITION, 0);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        bodyName = (EditText) findViewById(R.id.editName);
        bodyIV = (EditText) findViewById(R.id.editIV);
        bodyCV = (EditText) findViewById(R.id.editCV);
        bodyComment = (EditText) findViewById(R.id.editComment);
        bodyDate = (TextView) findViewById(R.id.displayDate);

        bodyName.setText(editName);
        bodyIV.setText(String.valueOf(editInitValue));
        bodyCV.setText(String.valueOf(editCurValue));
        bodyComment.setText(editComment);
        bodyDate.setText(viewDate);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent data = new Intent();
                String name = bodyName.getText().toString();
                String initValue = bodyIV.getText().toString();
                String curValue = bodyCV.getText().toString();
                String comment = bodyComment.getText().toString();

                data.putExtra(CounterActivity.EXTRA_ENAME, name);
                data.putExtra(CounterActivity.EXTRA_EINITVALUE, initValue);
                data.putExtra(CounterActivity.EXTRA_ECURVALUE, curValue);
                data.putExtra(CounterActivity.EXTRA_ECOMMENT, comment);
                data.putExtra(CounterActivity.EXTRA_POSITION, position);

                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
