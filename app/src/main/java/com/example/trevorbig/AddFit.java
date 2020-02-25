package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFit extends AppCompatActivity {
    AutoCompleteTextView Workout;
    EditText Date;
    EditText Time;
    EditText Duration;
    EditText Notes;
    Button submit;
    DBHelper mydb;
    Button cancel;
    ArrayList workoutArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fit);

        //make array list of Workouts in database
        mydb = new DBHelper(this);
        workoutArray = mydb.getAllDistinctWorkouts();

        //autocomplete when filling in Workout
        Workout = findViewById(R.id.Workout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, workoutArray);
        Workout.setAdapter(adapter);

        //auto date and time setter
        EditText autoDate = (EditText)findViewById(R.id.Date);
        EditText autoTime = (EditText)findViewById(R.id.Time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String time = timeFormat.format(Calendar.getInstance().getTime());

        autoDate.setText(date);
        autoTime.setText(time);

        //goes back to main activity screen
        cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFit.this, MainActivity.class));
            }
        });

        //submits data to database and adds to main activity
        Workout = (AutoCompleteTextView) findViewById(R.id.Workout);
        Date = (EditText) findViewById(R.id.Date);
        Time = (EditText) findViewById(R.id.Time);
        Duration = (EditText) findViewById(R.id.Duration);
        Notes = (EditText) findViewById(R.id.Notes);
        submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Workout = Workout.getText().toString();
                String date = Date.getText().toString();
                String time = Time.getText().toString();
                String duration = Duration.getText().toString();
                String notes = Notes.getText().toString();
                if(Workout.length() != 0 || Date.length() != 0 || Time.length() != 0 ) {
                    addData(Workout, date, time, duration, notes);
                    startActivity(new Intent(AddFit.this, MainActivity.class));
                }
                else {
                    Toast.makeText(AddFit.this, "you must put something", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //method for adding text entered in to the database
    public void addData(String Workout, String date, String time, String duration, String notes) {
        boolean insertData = mydb.addWorkout(Workout, date, time, duration, notes);
        if(insertData == true) {
            Toast.makeText(AddFit.this, "entered data", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddFit.this, "something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}
