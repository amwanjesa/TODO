package com.example.albert.todo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ToDoActivity extends AppCompatActivity {

    private ToDoList todoS;
    private String FILENAME;
    private ArrayList<String> tasks = new ArrayList<String>();
    private ToDoArrayAdapter adapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        todoS = (ToDoList) extras.get("listToDo");
        FILENAME = todoS.getListTitle();
        setContentView(R.layout.activity_to_do);
        setTitle(FILENAME);
        readFromStorage();
        getItemsFromList();
        lv = (ListView) findViewById(R.id.list_view);

        adapter = new ToDoArrayAdapter(this, todoS.getTasksInList());
        lv.setAdapter(adapter);
        //displayDoneTasks();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                todoS.getTasksInList().remove(position);
                //tasks.clear();
                //getItemsFromList();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ToDoItem clickedItem = (ToDoItem) lv.getItemAtPosition(position);
                if (clickedItem.isDone()){
                    lv.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    clickedItem.setDone(false);
                }else{
                    lv.getChildAt(position).setBackgroundColor(Color.BLUE);
                     clickedItem.setDone(true);

                }
            }
        });
    }

    public void getItemsFromList(){
        ArrayList<ToDoItem> toDoItems = todoS.getTasksInList();
        Log.d("size of list", Integer.toString(toDoItems.size()));
        for (ToDoItem toDoItem : toDoItems){
            Log.d("items in list", toDoItem.getTaskName());
            tasks.add(toDoItem.getTaskName());
        }
    }

    public void addToDoItem(View view){
        EditText newItem = (EditText) findViewById(R.id.new_todo_item);
        String newToDoItem = newItem.getText().toString();
        if (newToDoItem.length() > 0){
            ToDoItem newToDoList = new ToDoItem(newToDoItem);
            todoS.addToDOItem(newToDoList);
        }else{
            Toast.makeText(this, R.string.item_zero_len_toast, Toast.LENGTH_SHORT).show();
        }
        tasks.clear();
        newItem.setText("");
        getItemsFromList();
        adapter.notifyDataSetChanged();
        //displayDoneTasks();
    }

    @Override
    protected void onDestroy() {
        saveToStorage();

        super.onDestroy();
    }

    public void saveToStorage(){
        FileOutputStream fos;
        ObjectOutputStream ous=null;
        try {
            fos = getApplicationContext().openFileOutput(FILENAME, Activity.MODE_PRIVATE);
            ous=new ObjectOutputStream(fos);
            ous.writeObject(todoS);
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
            todoS = (ToDoList) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
