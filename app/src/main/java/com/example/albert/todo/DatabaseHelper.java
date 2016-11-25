package com.example.albert.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by albert on 20/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "TASKS";

    public static final String _ID = "_id";
    public static final String SUBJECT = "task";
    public static final String STATUS = "status";
    public static final String COLOR = "color";

    static final String DB_NAME = "TODO_TASKS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + STATUS + " TEXT, "+ COLOR +" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
