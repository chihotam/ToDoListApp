package com.todolistapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskEditor.class);
                intent.putExtra("Task", taskList.get(i));
                intent.putExtra("listIndex", i);
                intent.putExtra("requestCode", 101);
                Log.d("SENDING TASK" , taskList.get(i).getSubject());
                Log.d("index", String.valueOf(i));
                startActivityForResult(intent, 101);
            }
        });

        createNotificationChannel();

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
        intent.putExtra("requestCode", 100);
        startActivityForResult(intent, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED)
            return;


        Task task = (Task)data.getSerializableExtra("NewTask");

        if(data.getExtras().get("requestCode").equals(201))
        {
            taskList.set((Integer) data.getExtras().get("listIndex"), task);
            //Log.d("index position", (String) data.getExtras().get("listIndex"));
        }
        else
        {
            //Log.d("index position", (String) data.getExtras().get("listIndex"));
            taskList.addSort(task);
            try
            {
                FileOutputStream file = new FileOutputStream(new File(getFilesDir(), "TaskList.obj"));
                ObjectOutputStream fout = new ObjectOutputStream(file);
                fout.writeObject(taskList);
                fout.close();

                Log.d("a", String.valueOf(task.getUnixTime()));

                Intent intent = new Intent(MainActivity.this, TaskBroadcast.class);
                intent.setAction(task.getTime() + "&&" + task.getMessage());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, task.getUnixTime() * 1000, pendingIntent);

            }
            catch(IOException e)
            {
                System.out.println("ERROR");
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(arrayAdapter);
    }

    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "ReminderChannel";
            String description = "Channel for reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("taskChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
