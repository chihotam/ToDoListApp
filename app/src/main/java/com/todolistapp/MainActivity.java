package com.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private TaskList taskList;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.TaskListView);

        try
        {
            FileInputStream file = openFileInput("TaskList.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            taskList = (TaskList) fin.readObject();
            fin.close();

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
            taskListView.setAdapter(arrayAdapter);
        }
        catch (IOException | ClassNotFoundException e)
        {
            taskList = new TaskList();
        }

        Button addToDoButton = findViewById(R.id.AddButton);
        addToDoButton.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View view)
            {
                openEditor();
            }
        });

    }

    public void openEditor()
    {
        Intent intent = new Intent(this, TaskEditor.class);
        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Task task = (Task)data.getSerializableExtra("NewTask");

        taskList.addSort(task);
        try
        {
            FileOutputStream file = new FileOutputStream(new File(getFilesDir(), "TaskList.obj"));
            ObjectOutputStream fout = new ObjectOutputStream(file);
            fout.writeObject(taskList);
            fout.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR");
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(arrayAdapter);
    }


}
