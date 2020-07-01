package com.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class TaskEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

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