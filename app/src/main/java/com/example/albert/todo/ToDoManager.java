package com.example.albert.todo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by albert on 28/11/2016.
 */
public class ToDoManager implements Serializable{
    protected Context context;
    private static ToDoManager thisInstance = new ToDoManager();
    private static ArrayList<ToDoList> toDoLists;


    public static ToDoManager getInstance() {
        return thisInstance;
    }

    private ToDoManager() {
        toDoLists = new ArrayList<ToDoList>();
    }

    public void addToDoList(ToDoList newList){
        toDoLists.add(newList);
    }

    public void deleteFromToDoList(ToDoList deletedList){
        toDoLists.remove(deletedList);
    }

    public ArrayList<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(ArrayList<ToDoList> newLists){
        toDoLists = newLists;
    }
}
