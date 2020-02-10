package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WorkoutDisplay extends AppCompatActivity {
    DBHelper mydb;
    ListView DistinctWorkoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_display);

        DistinctWorkoutList = (ListView) findViewById(R.id.DistinctWorkoutList);
        final ArrayList array_list = mydb.getAllDistinctEmotions();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        DistinctWorkoutList.setAdapter(arrayAdapter);
    }
}
