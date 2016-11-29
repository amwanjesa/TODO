package com.example.albert.todo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by albert on 29/11/2016.
 */

public class ToDoList implements Serializable{

    private ArrayList<ToDoItem> toDOs;
    private String listTitle;

    public ToDoList(String listTitle){
        this.listTitle = listTitle;
        toDOs = new ArrayList<ToDoItem>();
    }

    public ArrayList<ToDoItem> getTasksInList(){
        return toDOs;
    }

    public String getListTitle(){
        return listTitle;
    }

    public void setListTitle(String newListTitle){
        this.listTitle = newListTitle;
    }

    public void addToDOItem(ToDoItem newTask){
        toDOs.add(newTask);
    }

    public ArrayList<ToDoItem>getToDoList(){
        return this.toDOs;
    }

}
