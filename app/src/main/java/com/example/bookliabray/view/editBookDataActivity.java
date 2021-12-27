package com.example.bookliabray.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookliabray.R;
import com.example.bookliabray.database.DataHelper;
import com.example.bookliabray.model.Books;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class editBookDataActivity extends AppCompatActivity {

    int id;
    Cursor c;
    Books bookInfo;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    private TextInputEditText tetBookNameEdit;
    private TextInputEditText tetBookLaunchDateEdit;
    private TextInputEditText tetBookAuthorNameEdit;
    private Spinner selectGenreEdit;
    private RadioButton rbBookFictionEdit,rbBookNonFiction;
    private CheckBox chbChildEdit, chbYouthEdit, chbAdultEdit, chbSeniorEdit;
    private String typeBookEdit;
    Button btnBookEdit;
    DataHelper DB=new DataHelper(editBookDataActivity.this);
    private final String[] dataOfSpinnerGenreEdit= new String[]{"Thriller", "Horror", "Historical", "Romance", "Western"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_data);
         init();
        Intent i = getIntent();
        id=i.getExtras().getInt("id");
        c = DB.getDataBookName(id);
        if (c.getCount() == 0) {
            Toast.makeText(editBookDataActivity.this, "No Details of this Books ", Toast.LENGTH_SHORT).show();
            finish();

        }
        while (c.moveToNext()) {
            
            bookInfo=new Books(id,c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
        }
        setBookData();
        setListener();
    }
    public  void init(){
        tetBookNameEdit=(TextInputEditText)findViewById(R.id.tetBookNameOne);
        tetBookAuthorNameEdit=(TextInputEditText)findViewById(R.id.tetBookAuthorNameOne);
        tetBookLaunchDateEdit=(TextInputEditText)findViewById(R.id.tetLaunchDateOne);
        selectGenreEdit=(Spinner)findViewById(R.id.spinnerGenreEdit);
        rbBookFictionEdit=findViewById(R.id.rbFunctionalOne);
        rbBookNonFiction=findViewById(R.id.rbNonFunctionalOne);
        chbAdultEdit=findViewById(R.id.chbAdultOne);
        chbChildEdit=findViewById(R.id.chbChildrenOne);
        chbSeniorEdit=findViewById(R.id.chbSeniorOne);
        chbYouthEdit=findViewById(R.id.chbYouthOne);
        btnBookEdit=findViewById(R.id.btnEditBook);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,dataOfSpinnerGenreEdit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGenreEdit.setAdapter(adapter);
        date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tetBookLaunchDateEdit.setText(sdf.format(calendar.getTime()));
        };
        tetBookLaunchDateEdit.setOnClickListener(v -> new DatePickerDialog(editBookDataActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());


    }
    public void setBookData(){
        tetBookNameEdit.setText(bookInfo.getBookNames());
        tetBookAuthorNameEdit.setText(bookInfo.getBookAuthorNames());
        tetBookLaunchDateEdit.setText(bookInfo.getBookLaunchDate());
       int temp= Arrays.asList(dataOfSpinnerGenreEdit).indexOf(bookInfo.getBookGenre());
       selectGenreEdit.setSelection(temp);

        if(bookInfo.getBookType().equals("Fiction")){
            rbBookFictionEdit.setChecked(true);
        }
        else{
            rbBookNonFiction.setChecked(true);

        }
        Toast.makeText(editBookDataActivity.this,bookInfo.getBookType(),Toast.LENGTH_SHORT).show();

        if(bookInfo.getBookAgePrefer().contains("Children"))
        {
            chbChildEdit.setChecked(true);
        }
        if(bookInfo.getBookAgePrefer().contains("Youth")){
            chbYouthEdit.setChecked(true);
        }
        if(bookInfo.getBookAgePrefer().contains("Adult")){
            chbAdultEdit.setChecked(true);
        }
        if(bookInfo.getBookAgePrefer().contains("Senior")){
            chbSeniorEdit.setChecked(true);
        }

    }
    public void setListener(){
        btnBookEdit.setOnClickListener(v -> {
            if(rbBookFictionEdit.isChecked()){
                typeBookEdit="Fiction";
            }else {
                typeBookEdit="Non Fiction";
            }
            String checkBox = "";
            if (chbChildEdit.isChecked()) {
                checkBox += chbChildEdit.getText().toString();
            }
            if (chbYouthEdit.isChecked()) {
                checkBox += chbYouthEdit.getText().toString();
            }
            if (chbAdultEdit.isChecked()) {
                checkBox += chbAdultEdit.getText().toString();
            }
            if (chbSeniorEdit.isChecked()) {
                checkBox += chbSeniorEdit.getText().toString();
            }
          Boolean updateData=DB.updateData(id, Objects.requireNonNull(tetBookNameEdit.getText()).toString(), Objects.requireNonNull(tetBookAuthorNameEdit.getText()).toString(),selectGenreEdit.getSelectedItem().toString(),typeBookEdit, Objects.requireNonNull(tetBookLaunchDateEdit.getText()).toString(),checkBox);
          if(updateData){
              Toast.makeText(editBookDataActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
          }
        });
    }
}