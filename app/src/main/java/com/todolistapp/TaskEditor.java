package com.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;

import static java.sql.DriverManager.println;

public class TaskEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private TaskList taskList = new TaskList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view)
            {
                finish();
            }
        }
        );

        TextView dateSelect = (TextView) findViewById(R.id.DateSelect);
        dateSelect.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View view)
            {
                DialogFragment datePicker = new CalendarFragment();
                datePicker.show(getSupportFragmentManager(), "Date of Task");

            }
        });

        TextView timeSelect = (TextView) findViewById(R.id.TimeSelect);
        timeSelect.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View view)
            {
                DialogFragment timePicker = new TimeSelectFragment();
                timePicker.show(getSupportFragmentManager(), "Time of Task");
            }
        });

        ImageButton saveButton = (ImageButton) findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new ImageButton.OnClickListener() {
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

                Timestamp timestamp = new Timestamp(Integer.parseInt(arr[2]) - 1900,
                        month,
                        Integer.parseInt(arr[1].split(" ")[1]),
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[0]), 0 ,0);

                Task task = new Task(subject, message, date, time, timestamp.getTime());

                Log.d("Task Created", subject + message + date + time);
                Intent data = new Intent();
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