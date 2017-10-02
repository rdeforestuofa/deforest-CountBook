/*
 * Class Name: Counter
 *
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) My Copyright, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.uofa.rdefo.deforest_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a Counter
 *
 * @author Ryan De Forest
 * @version 1.0
 * @since 1.0
 */

public class Counter {
    private String name;
    private String date;
    private int initValue;
    private int curValue;
    private String comment;

    /**
     * Constructs a counter with name, initial value and a comment
     * @param name name
     * @param initValue initial value
     * @param comment comment for the counter
     */

    public Counter(String name, int initValue, String comment) {
        this.name = name;
        setNewDate();
        this.initValue = initValue;
        this.curValue = initValue;
        this.comment = comment;
    }

    /**
     * Constructs a counter with name and initial value
     * @param name name
     * @param initValue initial value
     */

    public Counter(String name, int initValue) {
        this.name = name;
        setNewDate();
        this.initValue = initValue;
        this.curValue = initValue;
        comment = "";
    }

    /**
     * Returns the name of the counter
     * @return counter name
     */

    public String getName(){
        return name;
    }

    /**
     * Returns the date of the counter
     * @return date
     */

    public String getDate(){
        return date;
    }

    /**
     * Returns the initial value of the counter
     * @return initial value
     */

    public int getInitValue(){
        return initValue;
    }

    /**
     * Returns the current value of the counter
     * @return current value
     */

    public int getCurValue(){
        return curValue;
    }

    /**
     * Returns the comment of the counter
     * @return comment
     */

    public String getComment() {
        return comment;
    }

    /**
     * Sets the name of the counter
     * @param name name of counter
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the initial value of the counter
     * @param initValue initial value
     */

    public void setInitValue(int initValue) {
        this.initValue = initValue;
    }

    /**
     * Sets the current value of the counter, if different from old value updates the date
     * @param curValue current value
     */

    public void setCurValue(int curValue) {
        if (curValue != this.curValue) {
            setNewDate();
            this.curValue = curValue;
        }
    }

    /**
     * Sets the comment of the counter
     * @param comment comment of the counter
     */

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the date for the counter in the given format as a string
     */

    public void setNewDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        date = format1.format(new Date());
    }

    /**
     * Increments the counter and updates the date
     */

    public void incCounter(){
        setNewDate();
        curValue++;
    }

    /**
     * Decrements the counter, but prevents negatives values and updates date if value changed
     */

    public void decCounter(){
        if (curValue > 0) {
            setNewDate();
            curValue--;
        }
    }

    /**
     * Resets the counter value to the initial value and updates date if value changed
     */

    public void resCounter(){
        if (curValue != initValue) {
            setNewDate();
            curValue = initValue;
        }
    }
}
