package com.todolistapp;

import java.io.Serializable;
import java.util.ArrayList;


public class TaskList extends ArrayList<Task> implements Serializable
{

    public void addSort(Task t) throws IllegalArgumentException
    {
        if(t == null)
            throw new IllegalArgumentException("Empty Task");

        add(t);

    }
}
