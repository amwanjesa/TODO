package com.example.albert.todo;

import java.io.Serializable;

/**
 * Created by albert on 29/11/2016.
 */

public class ToDoItem implements Serializable{
    private String taskName;
    private boolean done;

    public ToDoItem(String taskName){
        this.taskName = taskName;
        this.done = false;
    }

    public boolean isDone(){
        return done;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setDone(boolean status){
        this.done = status;
    }

    public void setTaskName(String newTaskName){
        this.taskName = newTaskName;
    }
}
