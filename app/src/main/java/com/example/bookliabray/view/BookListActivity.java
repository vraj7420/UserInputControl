package com.example.bookliabray.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookliabray.R;
import com.example.bookliabray.adapter.RecyclerViewAdapter;
import com.example.bookliabray.database.DataHelper;
import com.example.bookliabray.model.Books;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class BookListActivity extends AppCompatActivity {
    private DataHelper db;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private final ArrayList<Books> book = new ArrayList<>();
    private final String[] dataOfSpinnerFilterGenre = new String[]{"All", "Thriller", "Horror", "Historical", "Romance", "Western"};
    private final String[] dataSpinnerOfFilterFunctionalType = new String[]{"All", "Fiction", "Non Fiction"};
    private Spinner spinnerGenreFilter, spinnerFunctionFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        init();
        createSpinnerListener();
        createBookDataList();
        setAdapterRecyclerView();
        adapter.sortDataByBookNameAndDate();

    }

    public void init() {
        db = new DataHelper(BookListActivity.this);
        spinnerGenreFilter = findViewById(R.id.spinnerFilterGenre);
        spinnerFunctionFilter = findViewById(R.id.spinnerFilterFunctional);
        recyclerView = findViewById(R.id.recyclerView);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.book_list));
        }
        ArrayAdapter<String> adapterSpinnerGenreFilter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataOfSpinnerFilterGenre);
        adapterSpinnerGenreFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenreFilter.setAdapter(adapterSpinnerGenreFilter);
        ArrayAdapter<String> adapterSpinnerFunctionalFilter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSpinnerOfFilterFunctionalType);
        adapterSpinnerFunctionalFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFunctionFilter.setAdapter(adapterSpinnerFunctionalFilter);
    }

    public void createSpinnerListener() {

        spinnerGenreFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, spinnerGenreFilter.getSelectedItem().toString());
                adapter.spinnerFilterGenre.filter(spinnerGenreFilter.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerFunctionFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.spinnerFilterFunctional.filter(spinnerFunctionFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void createBookDataList() {
        Cursor getData = db.getData();
        if (getData.getCount() == 0) {
            Toast.makeText(BookListActivity.this, "No Books Right Now", Toast.LENGTH_SHORT).show();

        }
        while (getData.moveToNext()) {
            book.add(new Books(getData.getInt(0), getData.getString(1), getData.getString(2), getData.getString(3), getData.getString(4), getData.getString(5), getData.getString(6)));
        }
    }

    public void setAdapterRecyclerView() {

        adapter = new RecyclerViewAdapter(book, BookListActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(BookListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(BookListActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recyclerviewmenu, menu);
        MenuItem item = menu.findItem(R.id.menuSearchAction);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}