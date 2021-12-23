package com.example.bookliabray;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class AddBookScreen extends AppCompatActivity {
     private TextInputEditText tetBookLaunchDate;
     private Button btnGoBookList;
     private final SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy");
     private Spinner selectGenre;
     private String[] dataOfSpinner=new String[]{"Thriller","Horror","Historical","Romance","Western"};
     Calendar calendar=Calendar.getInstance();
     private TextInputEditText tetBookName;
     private TextInputEditText tetBookAuthorName;
     private RadioGroup rgTypeOfBook;
     private RadioButton rbTypeofBook;
     private CheckBox chbChild,chbYouth,chbAdult,chbSenior;
     DataHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);
        tetBookLaunchDate= findViewById(R.id.tetLaunchDate);
        selectGenre=findViewById(R.id.spinnerGenre);
        btnGoBookList= findViewById(R.id.btnAddBook);
        tetBookName= findViewById(R.id.tetBookName);
        tetBookAuthorName=findViewById(R.id.tetBookAuthorName);
        rgTypeOfBook= findViewById(R.id.rgFunctionOrNonFunctional);

        DB=new DataHelper(AddBookScreen.this);
        chbChild= findViewById(R.id.chbChildren);
        chbYouth= findViewById(R.id.chbYouth);
        chbAdult= findViewById(R.id.chbAdult);
        chbSenior= findViewById(R.id.chbSenior);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_book  ));

        }
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,dataOfSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGenre.setAdapter(adapter);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tetBookLaunchDate .setText(sdf.format(calendar.getTime()));
            }

        };

        tetBookLaunchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddBookScreen.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
       btnGoBookList.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String checkBox = "";
               if (chbChild.isChecked()) {
                   checkBox += chbChild.getText().toString();
               }
               if (chbYouth.isChecked()) {
                   checkBox += chbYouth.getText().toString();
               }
               if (chbAdult.isChecked()){
                   checkBox+=chbAdult.getText().toString();
               }
               if(chbSenior.isChecked()){
                   checkBox+=chbSenior.getText().toString();
               }
               rbTypeofBook= findViewById(rgTypeOfBook.getCheckedRadioButtonId());
               String typeBook=rbTypeofBook.getText().toString();
               Log.d(TAG,selectGenre.getSelectedItem().toString());
               Boolean checkDataAdded=DB.insertData(tetBookName.getText().toString(),tetBookAuthorName.getText().toString(),selectGenre.getSelectedItem().toString(),typeBook,tetBookLaunchDate.getText().toString(),checkBox);
               if(checkDataAdded)
               {
                   Toast.makeText(AddBookScreen.this,"Book added",Toast.LENGTH_SHORT).show();
               }

               Intent i=new Intent(AddBookScreen.this,BookList.class);
               startActivity(i);
           }
       });
    }


}