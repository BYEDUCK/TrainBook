package com.example.mateusz.trainbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ExcersiseInfoInsertActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersise_info_insert);
    }

    public void onClickAcceptExcercise(View v)
    {
        EditText excerciseNameText=(EditText)findViewById(R.id.input_excercise_name);
        EditText excerciseDescriptionText=(EditText)findViewById(R.id.input_excercise_description);
        String excerciseName=excerciseNameText.getText().toString();
        String excerciseDescription=excerciseDescriptionText.getText().toString();
        try
        {
            SQLiteOpenHelper helper = new TrainingDataBaseHelper(this);
            db = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", excerciseName);
            contentValues.put("DESCRIPTION", excerciseDescription);
            db.insert("EXCERCISES", null, contentValues);
            db.close();
        }
        catch (SQLiteException e)
        {
            Toast toast=Toast.makeText(this,getString(R.string.db_error),Toast.LENGTH_SHORT);
            toast.show();
        }
        Toast toast=Toast.makeText(this,getString(R.string.excercise_added)+excerciseName,Toast.LENGTH_SHORT);
        toast.show();
        excerciseNameText.setText("");
        excerciseDescriptionText.setText("");
    }
}
