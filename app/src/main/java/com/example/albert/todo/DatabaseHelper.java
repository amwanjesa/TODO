package com.example.albert.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by albert on 20/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String PARENT_TABLE_NAME = "TASKS";
    public static final String CHILD_TABLE_NAME = "SUBTASKS";

    public static final String _ID = "_id";
    public static final String MAIN_TASK = "main_task";
    public static final String SUB_TASK = "sub_task";
    public static final String STATUS = "status";
    public static final String COLOR = "color";

    static final String DB_NAME = "TODO_TASKS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_PARENT_TABLE = "create table " + PARENT_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MAIN_TASK + " TEXT NOT NULL);";
    private static final String CREATE_CHILD_TABLE = "create table " + CHILD_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY, " + SUB_TASK + " TEXT NOT NULL, " + STATUS + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PARENT_TABLE);
        db.execSQL(CREATE_CHILD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PARENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHILD_TABLE_NAME);
        onCreate(db);
    }
}
