package com.example.mateusz.trainbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Mateusz on 2017-08-22.
 */

public class TrainingDataBaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME="Training";
    private final static int DB_VERSION=2;

    public TrainingDataBaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,DB_VERSION);
    }

    private void insertExcercise(SQLiteDatabase db,String name,String description)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("DESCRIPTION",description);
        db.insert("EXCERCISES",null,contentValues);
    }

    public void insertTraining(SQLiteDatabase db,String trainingName,String trainingDescription)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("DESCRIPTION",trainingDescription);
        contentValues.put("TRAINING_NAME",trainingName);
        db.insert("TRAININGS",null,contentValues);
        db.execSQL(
                "CREATE TABLE "
                +trainingName
                +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"EXCERCISE_ID INTEGER,"
                +"SERIES INTEGER);"
        );
    }

    private void addExcerciseToTraining(SQLiteDatabase db,int excerciseId,String trainingName,int series)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("EXCERCISE_ID",excerciseId);
        contentValues.put("SERIES",series);
        db.insert(trainingName,null,contentValues);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        if(oldVersion<1)
        {
            db.execSQL(
                    "CREATE TABLE EXCERCISES (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"NAME TEXT,"
                    +"DESCRIPTION TEXT);"
            );

            db.execSQL(
                    "CREATE TABLE TRAININGS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"TRAINING_NAME TEXT,"
                    +"DESCRIPTION TEXT);"
            );


            insertExcercise(db,"Pompki","3x12");
            insertExcercise(db,"Podciąganie","4x10");
            insertTraining(db,"KLATKA","Trening klatki piersiowej");
            addExcerciseToTraining(db,1,"KLATKA",3);
            addExcerciseToTraining(db,2,"KLATKA",4);
        }
        if(oldVersion<2)
        {
            db.execSQL("CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"TRAINING TEXT,"
                    +"DATE TEXT);"
            );
        }
    }
}
