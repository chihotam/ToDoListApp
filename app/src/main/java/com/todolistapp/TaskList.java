package com.todolistapp;

import java.io.Serializable;
import java.util.ArrayList;


public class TaskList extends ArrayList<Task> implements Serializable
{

    public void addSort(Task t) throws IllegalArgumentException
    {
        if(t == null)
            throw new IllegalArgumentException("Empty Task");

        if(this.isEmpty())
        {
            add(t);
            return;
        }

        for(int i = 0; i < this.size(); i++)
        {
            if(this.get(i).getUnixTime() > t.getUnixTime())
            {
                add(i, t);
                return;
            }
        }
        add(t);
    }
}
