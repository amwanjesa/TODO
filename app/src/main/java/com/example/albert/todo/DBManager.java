package com.example.albert.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by albert on 20/11/2016.
 */

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insert(String taskName, String notFinished){
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.SUBJECT, taskName);
        contentValue.put(dbHelper.STATUS, notFinished);
        database.insert(dbHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch(){
        String[] columns  = new String[]{dbHelper._ID, dbHelper.SUBJECT, dbHelper.STATUS,};
        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null,
                null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }else{
            return null;
        }

        return cursor;
    }

    public Cursor getAll(){
        Cursor cursor = database.rawQuery("SELECT "+dbHelper.SUBJECT+" FROM " + dbHelper.TABLE_NAME, null);
        return cursor;
    }

    public Cursor getEntryID(String taskName){
        Cursor cursor = database.rawQuery("SELECT _id  FROM  TASKS WHERE "+dbHelper.SUBJECT+"= '"+taskName+"'", null);
        return cursor;
    }

    public Cursor getStatus(String taskName){
        Cursor cursor = database.rawQuery("SELECT "+ dbHelper.STATUS +" FROM  TASKS WHERE "+dbHelper.SUBJECT+"= '"+taskName+"'", null);
        return cursor;
    }
    public void setStatus(String taskName, String status){
        database.execSQL("UPDATE " + dbHelper.TABLE_NAME + " SET status='"+ status +"' WHERE task='"+ taskName +"'");
    }


    public void delete(String taskName){
        database.delete(dbHelper.TABLE_NAME, "task = ?", new String[]{taskName});
    }

    public void deleteAll(){
        database.delete(dbHelper.TABLE_NAME, null, null);
    }
}
