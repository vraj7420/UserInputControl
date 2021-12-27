package com.example.bookliabray.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookliabray.R;
import com.example.bookliabray.database.DataHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class AddBookScreenActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    private final String[] dataOfSpinnerGenre = new String[]{"Thriller", "Horror", "Historical", "Romance", "Western"};
    Calendar calendar = Calendar.getInstance();
    private TextInputEditText tetBookName;
    private TextInputEditText tetBookLaunchDate;
    private TextInputEditText tetBookAuthorName;
    private Spinner selectGenre;
    private RadioGroup rgTypeOfBook;
    private RadioButton rbTypeofBook;
    private CheckBox chbChild, chbYouth, chbAdult, chbSenior;
    Button btnGoBookList;
    DataHelper DB;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);
        init();
        setLister();
    }

   public void init(){
       tetBookLaunchDate = findViewById(R.id.tetLaunchDate);
       selectGenre = findViewById(R.id.spinnerGenre);
       btnGoBookList = findViewById(R.id.btnAddBook);
       tetBookName = findViewById(R.id.tetBookName);
       tetBookAuthorName = findViewById(R.id.tetBookAuthorName);
       rgTypeOfBook = findViewById(R.id.rgFunctionOrNonFunctional);

       DB = new DataHelper(AddBookScreenActivity.this);
       chbChild = findViewById(R.id.chbChildren);
       chbYouth = findViewById(R.id.chbYouth);
       chbAdult = findViewById(R.id.chbAdult);
       chbSenior = findViewById(R.id.chbSenior);

       if (getSupportActionBar() != null) {
           getSupportActionBar().setTitle(getString(R.string.add_book));

       }
       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataOfSpinnerGenre);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       selectGenre.setAdapter(adapter);
        date = (view, year, monthOfYear, dayOfMonth) -> {
           calendar.set(Calendar.YEAR, year);
           calendar.set(Calendar.MONTH, monthOfYear);
           calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
           tetBookLaunchDate.setText(sdf.format(calendar.getTime()));
       };

   }
   public void setLister(){
       tetBookLaunchDate.setOnClickListener(v -> new DatePickerDialog(AddBookScreenActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
       btnGoBookList.setOnClickListener(v -> {
           String checkBox = "";
           if (chbChild.isChecked()) {
               checkBox += chbChild.getText().toString();
           }
           if (chbYouth.isChecked()) {
               checkBox += chbYouth.getText().toString();
           }
           if (chbAdult.isChecked()) {
               checkBox += chbAdult.getText().toString();
           }
           if (chbSenior.isChecked()) {
               checkBox += chbSenior.getText().toString();
           }
           rbTypeofBook = findViewById(rgTypeOfBook.getCheckedRadioButtonId());
           String typeBook = rbTypeofBook.getText().toString();
           Log.d(TAG, selectGenre.getSelectedItem().toString());
           Boolean checkDataAdded = DB.insertData(Objects.requireNonNull(tetBookName.getText()).toString(), Objects.requireNonNull(tetBookAuthorName.getText()).toString(), selectGenre.getSelectedItem().toString(), typeBook, Objects.requireNonNull(tetBookLaunchDate.getText()).toString(), checkBox);
           if (checkDataAdded) {
               Toast.makeText(AddBookScreenActivity.this, "Book added", Toast.LENGTH_SHORT).show();
           }

           Intent i = new Intent(AddBookScreenActivity.this, BookListActivity.class);
           startActivity(i);
       });

   }


}