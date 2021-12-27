package com.example.bookliabray.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bookliabray.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menuItemAddBook):
                Intent i=new Intent(MainActivity.this, AddBookScreenActivity.class);
                startActivity(i);
                break;
            case (R.id.menuItemBookList):
                Intent j=new Intent(MainActivity.this, BookListActivity.class);
                startActivity(j);
                break;
        }
        return true;
    }
}