package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeeklyCalendar extends AppCompatActivity {
    ListView WeeklyListView;
    TextView ModeWorkout;
    TextView ModeWorkoutText;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_calendar);
        mydb = new DBHelper(this);

        WeeklyListView = (ListView) findViewById(R.id.WeeklyListView);
        ArrayList<String> array_list = new ArrayList<String>();
        array_list.add("Monday");
        array_list.add("Tuesday");
        array_list.add("Wednesday");
        array_list.add("Thursday");
        array_list.add("Friday");
        array_list.add("Saturday");
        array_list.add("Sunday");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, array_list);
        WeeklyListView.setAdapter(arrayAdapter);

        WeeklyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Calendar c = Calendar.getInstance();
                int dayOfWeekToday = c.get(Calendar.DAY_OF_WEEK) - 2;
                int daysOfWeekBefore = position - dayOfWeekToday;
                if (daysOfWeekBefore < 0) {
                    String date = getCalculatedDate(daysOfWeekBefore);
                    Intent intent = new Intent(WeeklyCalendar.this, MainActivity.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
                else if (daysOfWeekBefore == 0) {
                    startActivity(new Intent(WeeklyCalendar.this, MainActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "That day hasn't happened yet", Toast.LENGTH_LONG);
                }
            }
        });

        ModeWorkout = (TextView) findViewById(R.id.ModeWorkout);
        ModeWorkoutText = (TextView) findViewById(R.id.ModeWorkoutText);
        String ModeWorkout = mydb.getModeWorkout();
        if (ModeWorkout == null) {
            ModeWorkoutText.setText("You did not have a most common emotion this week");
        }
        else ModeWorkoutText.setText(ModeWorkout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeeklyCalendar.this, AddFit.class));
            }
        });
    }

    public static String getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
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
            case R.id.Monthly:
                startActivity(new Intent(this, MonthlyCalendar.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
