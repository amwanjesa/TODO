package com.example.albert.todo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by albert on 16/11/2016.
 */

public class TODOActivity extends AppCompatActivity {


    private DBManager dbManager;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> tasks = new ArrayList<String>();

    private List<Integer> taskDone = new ArrayList<Integer>();

    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        // getTasksFromDB();
        readFromDB();
        lv = (ListView) findViewById(R.id.list_view);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(adapter);
        //displayDoneTasks();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                String taskName =(String) (lv.getItemAtPosition(position));
                dbManager.delete(taskName);
                tasks.clear();
                readFromDB();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String task = (String) lv.getItemAtPosition(position);
                Cursor cursor = dbManager.getStatus(task);
                cursor.moveToFirst();

                Log.d("col_names", Arrays.toString(cursor.getColumnNames()));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                if(status.equals("done")){
                    lv.getChildAt(position).setBackgroundColor(Color.WHITE);
                    dbManager.setStatus(task, "pending");
                    dbManager.setColor(task, "white");
                }else{
                    lv.getChildAt(position).setBackgroundColor(Color.GREEN);
                    dbManager.setStatus(task, "done");
                    dbManager.setColor(task, "green");
                }

                tasks.clear();
                readFromDB();
                adapter.notifyDataSetChanged();

            }
        });
    }

    public void addTask(View view){
        EditText newTask = (EditText) findViewById(R.id.new_item);
        dbManager.insert(newTask.getText().toString(), "pending", "white");
        Log.d("ins", "Insert task: " + newTask.getText().toString());
        tasks.clear();
        readFromDB();
        adapter.notifyDataSetChanged();
        //displayDoneTasks();
    }

//    private void displayDoneTasks(){
//        int taskCount = lv.getCount();
//        for(int i = 0; i < taskCount; i++){
//            String task = lv.getItemAtPosition(i).toString();
//            Cursor colorCursor = dbManager.getColor(task);
//            colorCursor.moveToFirst();
//
//            String color = colorCursor.getString(colorCursor.getColumnIndexOrThrow("color"));
//            if(color.equals("green")){
//                lv.getChildAt(i).setBackgroundColor(Color.GREEN);
//            }
//        }
//
//    }



    @Override
    protected void onDestroy() {

        dbManager.close();

        super.onDestroy();
    }

    private void readFromDB() {

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        if(cursor != null){
            if (cursor.moveToFirst()){
                do{
                    String task = cursor.getString(cursor.getColumnIndex("task"));
                    String done = cursor.getString(cursor.getColumnIndex("status"));
                    tasks.add(task);
                    if(done.equals("done")){
                        taskDone.add(tasks.indexOf(task));
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
    }


//    private void getTasksFromDB(){
//        tasks = new ArrayList<String>();
//
//        dbManager = new DBManager(this);
//        dbManager.open();
//        Cursor cursor = dbManager.fetch();
//
//        if(cursor.moveToFirst()){
//            while(cursor.isAfterLast() == false){
//                String task = cursor.getString(0);
//                tasks.add(task);
//                cursor.moveToNext();
//            }
//        }
//
//    }

    // Array of ArrayList voor tijdens app use
}
