package com.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TaskList taskList = new TaskList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addToDoButton = (Button) findViewById(R.id.AddButton);
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
        TextView tv = (TextView) findViewById(R.id.Testing);
        tv.setText(task.getMessage());
    }
}
