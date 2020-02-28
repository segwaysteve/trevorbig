package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonthlyCalendar extends AppCompatActivity {
    CalendarView calendarView;
    TextView ModeWorkout;
    TextView ModeWorkoutText;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_calendar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mydb = new DBHelper(this);

        ModeWorkout = (TextView) findViewById(R.id.ModeWorkout);
        ModeWorkoutText = (TextView) findViewById(R.id.ModeWorkoutText);
        String ModeWorkout = mydb.getModeWorkout();
        if (ModeWorkout == null) {
            ModeWorkoutText.setText("You did not have a most common emotion this month");
        }
        else {
            ModeWorkoutText.setText(ModeWorkout);
        }

        calendarView = (CalendarView) findViewById(R.id.MonthlyCalendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String DayOfMonth = dayOfMonth + "";
                if (String.valueOf(dayOfMonth).length() == 1) {
                    DayOfMonth = "0" + dayOfMonth;
                }
                String Month = (month + 1) + "";
                if (String.valueOf(month).length() == 1) {
                    Month = "0" + (month + 1);
                }
                String date = Month + "/" + DayOfMonth + "/" + year;
                Intent intent = new Intent(MonthlyCalendar.this, MainActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MonthlyCalendar.this, AddWorkout.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.Daily:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.Weekly:
                startActivity(new Intent(this, WeeklyCalendar.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
