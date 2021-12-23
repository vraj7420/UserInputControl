package com.example.bookliabray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataHelper extends SQLiteOpenHelper {
    public DataHelper(Context context) {
        super(context,"AddBookNew.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create Table BookListNew(bookName TEXT primary key,bookAuthorName TEXT,genre TEXT,bookType TEXT,launchDate TEXT,agePrefer TEXT )");

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Boolean insertData(String bookName,String bookAuthorName,String genre,String bookType,String launchDate,String agePrefer){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookName",bookName);
        contentValues.put("bookAuthorName",bookAuthorName);
        contentValues.put("genre",genre);
        contentValues.put("bookType",bookType);
        contentValues.put("launchDate",launchDate);
        contentValues.put("agePrefer",agePrefer);
        //contentValues.put("ageGroup",ageGroup);
        long result=DB.insert("BookListNew",null,contentValues);
        return result != -1;
    }
    public Cursor getData()
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        return Db.rawQuery("Select * from BookListNew",null);
    }
    public Cursor getDataBookName(String bookNameTemp,String authorNameTemp)
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        return Db.rawQuery("select * from BookListNew Where bookName=? AND bookAuthorName=?",new String[] {bookNameTemp,authorNameTemp}); }
}

