package com.example.bookliabray.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataHelper extends SQLiteOpenHelper {
    public static int id=1;
    public DataHelper(Context context) {
        super(context,"AddBookNewList.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create Table BookListNew(bookId NUMBER,bookName TEXT,bookAuthorName TEXT,genre TEXT,bookType TEXT,launchDate TEXT,agePrefer TEXT )");

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Boolean insertData(String bookName,String bookAuthorName,String genre,String bookType,String launchDate,String agePrefer){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookId",id);
        id=id+1;
        contentValues.put("bookName",bookName);
        contentValues.put("bookAuthorName",bookAuthorName);
        contentValues.put("genre",genre);
        contentValues.put("bookType",bookType);
        contentValues.put("launchDate",launchDate);
        contentValues.put("agePrefer",agePrefer);
        long result=DB.insert("BookListNew",null,contentValues);
        return result != -1;
    }
    public Cursor getData()
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        return Db.rawQuery("Select * from BookListNew",null);
    }
    public Cursor getDataBookName(int id)
    {
        SQLiteDatabase Db=this.getWritableDatabase();

        return Db.rawQuery("select * from BookListNew Where bookId=?",new String[]{String.valueOf(id)}); }

    public Boolean updateData(int id,String bookName,String bookAuthorName,String genre,String bookType,String launchDate,String agePrefer){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookName",bookName);
        contentValues.put("bookAuthorName",bookAuthorName);
        contentValues.put("genre",genre);
        contentValues.put("bookType",bookType);
        contentValues.put("launchDate",launchDate);
        contentValues.put("agePrefer",agePrefer);
        long result=DB.update("BookListNew",contentValues, "bookId=?", new String[]{String.valueOf(id)});
        return result != -1;
    }
}

