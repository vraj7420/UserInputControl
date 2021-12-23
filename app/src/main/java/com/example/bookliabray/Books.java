package com.example.bookliabray;


import android.util.Log;

import static android.content.ContentValues.TAG;

public class Books {
    String  bookNames;
    String bookAuthorName;
    String bookLaunchDate;
    String bookType;
    String bookGenre;
    String bookAgePrefer;

    public Books(String bookNames, String bookAuthorName, String bookLaunchDate, String bookType, String bookGenre, String bookAgePrefer) {
        this.bookNames = bookNames;
        this.bookAuthorName = bookAuthorName;
        this.bookLaunchDate = bookLaunchDate;
        this.bookType = bookType;
        this.bookGenre = bookGenre;
        this.bookAgePrefer = bookAgePrefer;
    }

    public String getBookNames() {
        return bookNames;
    }

    public String getBookAuthorNames() {
        return bookAuthorName;
    }

    public String getBookLaunchDate() {
        return bookLaunchDate;
    }

    public String getBookType() {
        return bookType;
    }

    public String getBookGenre() {
        Log.i(TAG, bookGenre);
        return bookGenre;
    }

    public String getBookAgePrefer() {
        return bookAgePrefer;
    }

}