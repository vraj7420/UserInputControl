package com.example.bookliabray.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookliabray.database.DataHelper;
import com.example.bookliabray.R;
import com.example.bookliabray.model.Books;

public class BookDetailsActivity extends AppCompatActivity {

    int id;
    private final DataHelper DB = new DataHelper(BookDetailsActivity.this);
    Books bookDetail;
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
            getSupportActionBar().setTitle("BookDetailsActivity");
        }

        Intent i = getIntent();
        id=i.getExtras().getInt("id");
        c = DB.getDataBookName(id);
        if (c.getCount() == 0) {
            Toast.makeText(BookDetailsActivity.this, "No Books Right Now", Toast.LENGTH_SHORT).show();

        }
        while (c.moveToNext()) {
            bookDetail=new Books(id,c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
        }
        tvBookName.setText(bookDetail.getBookNames());
        tvBookAuthorName.setText(bookDetail.getBookAuthorNames());
        tvBookLaunchDate.setText(bookDetail.getBookLaunchDate());
        tvBookType.setText(bookDetail.getBookType());
        tvBookGenre.setText(bookDetail.getBookGenre());
        tvAgeGroupOfBook.setText(bookDetail.getBookAgePrefer());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookdetailsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnEdit) {
            Intent i = new Intent(BookDetailsActivity.this, editBookDataActivity.class);
            i.putExtra("id",id);
            startActivity(i);
        }
        return true;
    }
}