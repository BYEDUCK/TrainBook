package com.example.mateusz.trainbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TrainingInfoInsertActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_info_insert);
    }

    public void onClickAcceptTraining(View v)
    {
        EditText trainingNameText=(EditText)findViewById(R.id.input_training_name);
        EditText trainingDescriptionText=(EditText)findViewById(R.id.input_training_description);
        String trainingName=trainingNameText.getText().toString();
        String trainingDescription=trainingDescriptionText.getText().toString();
        try
        {
            TrainingDataBaseHelper helper=new TrainingDataBaseHelper(this);
            db=helper.getWritableDatabase();
            helper.insertTraining(db,trainingName,trainingDescription);
            db.close();
        }
        catch (SQLiteException e)
        {
            Toast toast=Toast.makeText(this,getString(R.string.db_error),Toast.LENGTH_SHORT);
            toast.show();
        }
        Toast toast=Toast.makeText(this,getString(R.string.training_added)+trainingName,Toast.LENGTH_SHORT);
        toast.show();
        trainingNameText.setText("");
        trainingDescriptionText.setText("");
    }
}
