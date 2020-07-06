package com.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TaskList taskList = new TaskList();
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.TaskListView);

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

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(arrayAdapter);
    }
}
