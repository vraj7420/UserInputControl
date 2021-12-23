package com.example.bookliabray;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetails extends AppCompatActivity {

    private DataHelper DB = new DataHelper(BookDetails.this);
    private String bookName;
    private String bookAuthorName;
    private String bookType;
    private String bookGenre;
    private String bookLaunchDate;
    private String bookAgePrefer;
    private Cursor c;
    private TextView tvBookName;
    private TextView tvBookAuthorName;
    private TextView tvBookType;
    private TextView tvBookGenre;
    private TextView tvBookLaunchDate;
    private TextView tvAgeGroupOfBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        tvBookAuthorName = findViewById(R.id.tvAuthorName1);
        tvBookName = findViewById(R.id.tvBookName1);
        tvBookType = findViewById(R.id.tvBookType1);
        tvBookGenre = findViewById(R.id.tvBookGenre1);
        tvBookLaunchDate = findViewById(R.id.tvBookLaunchDate1);
        tvAgeGroupOfBook = findViewById(R.id.tvBookAgeGroupPrefer);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("BookDetails");
        }

        Intent i = getIntent();
        bookName = i.getExtras().getString("book");
        bookAuthorName = i.getExtras().getString("author");
        c = DB.getDataBookName(bookName, bookAuthorName);
        if (c.getCount() == 0) {
            Toast.makeText(BookDetails.this, "No Books Right Now", Toast.LENGTH_SHORT).show();

        }
        while (c.moveToNext()) {
            bookGenre = c.getString(2);
            bookType = c.getString(3);
            bookLaunchDate = c.getString(4);
            bookAgePrefer = c.getString(5);
        }
        tvBookName.setText(bookName);
        tvBookAuthorName.setText(bookAuthorName);
        tvBookLaunchDate.setText(bookLaunchDate);
        tvBookType.setText(bookType);
        tvBookGenre.setText(bookGenre);
        tvAgeGroupOfBook.setText(bookAgePrefer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookdetailsmenu, menu);
        return true;
    }

}