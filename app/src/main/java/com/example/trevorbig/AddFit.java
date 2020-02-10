package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFit extends AppCompatActivity {
    AutoCompleteTextView Workout;
    EditText Date;
    EditText Time;
    EditText Notes;
    Button submit;
    Integer DefaultColor;
    Button cancel;
    Button OpenColorPicker;
    ArrayList emotionArray;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fit);

        EditText autoDate = (EditText)findViewById(R.id.Date);
        EditText autoTime = (EditText)findViewById(R.id.Time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String time = timeFormat.format(Calendar.getInstance().getTime());

        autoDate.setText(date);
        autoTime.setText(time);

        cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFit.this, MainActivity.class));
            }
        });


    }
}
