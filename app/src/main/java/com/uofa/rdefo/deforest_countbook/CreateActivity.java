/*
 * Class Name: CreateActivity
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

/**
 * Interface for creating new counters in CountBook
 */

public class CreateActivity extends AppCompatActivity {

    private EditText bodyName;
    private EditText bodyIV;
    private EditText bodyComment;

    /**
     * Sets the create button functionality to take the given user input for a new counter
     * and passes it back to the main interface for adding the counter to the list
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createButton = (Button) findViewById(R.id.create_btn);
        bodyName = (EditText) findViewById(R.id.editName);
        bodyIV = (EditText) findViewById(R.id.editIV);
        bodyComment = (EditText) findViewById(R.id.editComment);

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent data = new Intent();
                String name = bodyName.getText().toString();
                String initValue = bodyIV.getText().toString();
                String comment = bodyComment.getText().toString();

                data.putExtra(CounterActivity.EXTRA_NAME, name);
                data.putExtra(CounterActivity.EXTRA_INITVALUE, initValue);
                data.putExtra(CounterActivity.EXTRA_COMMENT, comment);

                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
