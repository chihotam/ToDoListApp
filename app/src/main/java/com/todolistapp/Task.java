package com.todolistapp;

import java.io.Serializable;

public class Task implements Serializable
{
    private String subject;
    private String message;
    private String date;
    private String time;

    public Task(String subject, String message, String date, String time)
    {
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
