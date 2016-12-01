package com.example.albert.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by albert on 16/11/2016.
 */

public class ListsActivity extends AppCompatActivity {

    private static final String FILENAME = "toDoStorage";
    private DBManager dbManager;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> tasks = new ArrayList<String>();

    private List<Integer> taskDone = new ArrayList<Integer>();

    private ListView lv;

    private ToDoManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        manager = ToDoManager.getInstance();

        // getTasksFromDB();
        readFromStorage();
        if(manager == null){
            manager = ToDoManager.getInstance();
        }
        getListsFromManager();
        lv = (ListView) findViewById(R.id.list_view);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(adapter);
        //displayDoneTasks();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                String listName =(String) (lv.getItemAtPosition(position));
                manager.deleteFromToDoList(tasks.indexOf(listName));
                tasks.clear();
                getListsFromManager();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String listName = (String) lv.getItemAtPosition(position);
                ToDoList nextToDoList = manager.getTodoList(tasks.indexOf(listName));
                Intent toDoListIntent = new Intent(ListsActivity.this, ToDoActivity.class);
                toDoListIntent.putExtra("listToDo", nextToDoList);
                startActivity(toDoListIntent);

            }
        });
    }

    public void addList(View view){
        EditText newList = (EditText) findViewById(R.id.new_list);
        ToDoList newToDoList = new ToDoList(newList.getText().toString());
        manager.addToDoList(newToDoList);
        tasks.clear();
        getListsFromManager();
        adapter.notifyDataSetChanged();
        //displayDoneTasks();
    }

//    public void setString(View view){
//        EditText textToSet = (EditText) findViewById();
//        manager.setSuperUsefulString(textToSet.toString());
//    }

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
        saveToStorage();

        super.onDestroy();
    }

    public void getListsFromManager(){
        ArrayList<ToDoList> toDoLists = manager.getToDoLists();
        for (ToDoList taskList : toDoLists){
            Log.d("getting todos", taskList.getListTitle());
            tasks.add(taskList.getListTitle());
        }
    }



    public void saveToStorage(){
        FileOutputStream fos;
        ObjectOutputStream ous=null;
        try {
            fos = getApplicationContext().openFileOutput(FILENAME, Activity.MODE_PRIVATE);
            ous=new ObjectOutputStream(fos);
            ous.writeObject(manager);
            ous.flush();
            ous.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readFromStorage(){
        FileInputStream fis;
        try {
            fis = getApplicationContext().openFileInput(FILENAME);
            ObjectInputStream oi = new ObjectInputStream(fis);
            manager = (ToDoManager) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private void readFromDB() {
//
//        dbManager = new DBManager(this);
//        dbManager.open();
//        Cursor cursor = dbManager.fetch();
//        if(cursor != null){
//            if (cursor.moveToFirst()){
//                do{
//                    String task = cursor.getString(cursor.getColumnIndex("task"));
//                    String done = cursor.getString(cursor.getColumnIndex("status"));
//                    tasks.add(task);
//                    if(done.equals("done")){
//                        taskDone.add(tasks.indexOf(task));
//                    }
//                }while(cursor.moveToNext());
//            }
//            cursor.close();
//        }
//    }


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
