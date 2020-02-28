package com.example.trevorbig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditWorkout extends AppCompatActivity {
    AutoCompleteTextView EditWorkout;
    EditText EditDate;
    EditText EditTime;
    EditText EditDuration;
    EditText EditNotes;
    DBHelper mydb;
    Button EditUpdate;
    Button EditDelete;
    Button EditCancel;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        EditWorkout = (AutoCompleteTextView) findViewById(R.id.EditWorkout);
        EditDate = (EditText) findViewById(R.id.EditDate);
        EditTime = (EditText) findViewById(R.id.EditTime);
        EditDuration = (EditText) findViewById(R.id.EditDuration);
        EditNotes = (EditText) findViewById(R.id.EditNotes);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int id = extras.getInt("id");

            if(id > 0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(id);
                id_To_Update = id;
                rs.moveToFirst();

                String emotion = rs.getString(rs.getColumnIndex(DBHelper.col_2));
                String date = rs.getString(rs.getColumnIndex(DBHelper.col_3));
                String time = rs.getString(rs.getColumnIndex(DBHelper.col_4));
                String duration = rs.getString(rs.getColumnIndex(DBHelper.col_5));
                String notes = rs.getString(rs.getColumnIndex(DBHelper.col_6));

                if (!rs.isClosed()) {
                    rs.close();
                }

                //Button b = (Button)findViewById(R.id.button1);
                //b.setVisibility(View.INVISIBLE);

                EditWorkout.setText((CharSequence) emotion);
                EditDate.setText((CharSequence) date);
                EditTime.setText((CharSequence) time);
                EditDuration.setText((CharSequence) duration);
                EditNotes.setText((CharSequence) notes);
            }
        }



        EditUpdate = (Button) findViewById(R.id.EditUpdate);
        EditUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emotion = EditWorkout.getText().toString();
                String date = EditDate.getText().toString();
                String time = EditTime.getText().toString();
                String duration = EditDuration.getText().toString();
                String notes = EditNotes.getText().toString();
                mydb.updateWorkout(id_To_Update, emotion, date, time, duration, notes);
                startActivity(new Intent(EditWorkout.this, MainActivity.class));
            }
        });

        EditDelete = (Button) findViewById(R.id.EditDelete);
        EditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
                builder.setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteWorkout(id_To_Update);
                                Toast.makeText(EditWorkout.this, "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditWorkout.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.show();
            }
        });

        EditCancel = (Button) findViewById(R.id.EditCancel);
        EditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWorkout.this, MainActivity.class));
            }
        });
    }
}
