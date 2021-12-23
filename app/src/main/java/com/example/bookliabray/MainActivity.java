package com.example.bookliabray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                Intent i=new Intent(MainActivity.this,AddBookScreen.class);
                startActivity(i);
                break;
            case (R.id.menuItemBookList):
                Intent j=new Intent(MainActivity.this,BookList.class);
                startActivity(j);
                break;
        }
        return true;
    }
}