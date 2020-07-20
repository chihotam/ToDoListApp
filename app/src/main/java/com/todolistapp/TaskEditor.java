package com.todolistapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class TaskEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        ImageButton cancelButton = findViewById(R.id.CancelButton);
        TextView dateSelect = findViewById(R.id.DateSelect);
        TextView timeSelect = findViewById(R.id.TimeSelect);
        ImageButton saveButton = findViewById(R.id.SaveButton);
        EditText subjectBox = findViewById(R.id.TaskSubject);
        EditText messageBox = findViewById(R.id.TaskMessage);
        TextView deleteButton = findViewById(R.id.Delete);
        deleteButton.setVisibility(View.GONE);

        if(getIntent().getExtras().get("requestCode").equals(101))
        {
            Task t = (Task)getIntent().getSerializableExtra("Task");
            subjectBox.setText(t.getSubject());
            messageBox.setText(t.getMessage());
            dateSelect.setText(t.getDate());
            timeSelect.setText(t.getTime());
            deleteButton.setVisibility(View.VISIBLE);
        }

        cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
        );

        deleteButton.setOnClickListener(new TextView.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent data = new Intent();
                data.putExtra("listIndex", (Integer) getIntent().getExtras().get("listIndex"));
                data.putExtra("requestCode", 500);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        dateSelect.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View view)
            {
                DialogFragment datePicker = new CalendarFragment();
                datePicker.show(getSupportFragmentManager(), "Date of Task");
            }
        });

        timeSelect.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View view)
            {
                DialogFragment timePicker = new TimeSelectFragment();
                timePicker.show(getSupportFragmentManager(), "Time of Task");
            }
        });

        saveButton.setOnClickListener(new ImageButton.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view)
            {
                String subject = ((EditText) findViewById(R.id.TaskSubject)).getText().toString();
                String message = ((EditText) findViewById(R.id.TaskMessage)).getText().toString();
                String date = ((TextView) findViewById(R.id.DateSelect)).getText().toString();
                String time = ((TextView) findViewById(R.id.TimeSelect)).getText().toString();

                String[] months = {"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"};

                int month = 0;
                String[] arr = date.split(", ");

                for(int i = 0; i < months.length; i++)
                {
                    if(arr[1].contains(months[i]))
                    {
                        month = i + 1;
                        break;
                    }
                }

               String taskDate = date.split(", ")[2] + "/" + month + "/" + arr[1].split(" ")[1] + " " +
                       time;
                Log.d("date" , date);
                Log.d("Taskdate", taskDate);
                LocalDateTime ldt = LocalDateTime.parse(taskDate, DateTimeFormatter.ofPattern("yyyy/M/d H:m"));

                long millis = 0;
                long hourInMillis = 3600;

                if(month < 10)
                    millis = (ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
                else
                    millis = ((ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) / 1000)  - hourInMillis;

                Log.d("unix", String.valueOf(millis));

                Task task = new Task(subject, message, date, time, millis);
                Intent data = new Intent();

                Integer i = (Integer) getIntent().getExtras().get("listIndex");

                if(getIntent().getExtras().get("requestCode").equals(101))
                {
                    data.putExtra("requestCode", 201);
                    data.putExtra("listIndex", (Integer) getIntent().getExtras().get("listIndex"));
                    Log.d("listIndex", String.valueOf(i.intValue()));
                }
                else {
                    data.putExtra("requestCode", 200);
                }

                data.putExtra("NewTask", task);
                setResult(RESULT_OK, data);
                finish();
        }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView dateSelect = (TextView) findViewById(R.id.DateSelect);
        dateSelect.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
    {
        String ampm = "";

        TextView timeSelect = (TextView) findViewById(R.id.TimeSelect);
        timeSelect.setText(hourOfDay + ":" + minute + ampm);

    }
}