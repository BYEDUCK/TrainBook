package com.example.mateusz.trainbook;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ActiveTrainingActivity extends AppCompatActivity {

    public static final String EXTRA_TRAININGID = "trainingId";
    private SQLiteDatabase db;
    private Cursor cursor;//kursor tymczasowy
    private Cursor cursor_spinner;//kursor do spinnera
    private Cursor cursor_list;//kursor do listy
    private Cursor cursor_list_new;//kursor do listy w przypadku zmiany
    private String trainingName;//nazwa aktualnego treningu
    private String trainingDescription;//opis aktualnego treningu
    private ListView listView;//lista ćwiczeń
    private ActiveTrainingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_training);
        Spinner spinner = (Spinner) findViewById(R.id.choose_excercise);
        int trainingId = (Integer)getIntent().getExtras().get(EXTRA_TRAININGID);//pobranie id treningu z intencji
        try {
            TrainingDataBaseHelper helper = new TrainingDataBaseHelper(this);
            db = helper.getReadableDatabase();//otworzenie bazy danych

            cursor = db.query("TRAININGS", new String[]{"TRAINING_NAME","DESCRIPTION"}, null, null, null, null, null);//pobranie wszystkich treningów
            if(cursor.moveToFirst())
            {
                if(trainingId==0) {//jeśli pierwszy element
                    trainingName = cursor.getString(0);
                    trainingDescription=cursor.getString(1);
                }
                else if(cursor.move(trainingId)) {//jeśli kolejny
                    trainingName = cursor.getString(0);
                    trainingDescription=cursor.getString(1);
                }
            }
            cursor.close();

            cursor_spinner = db.query("EXCERCISES", new String[]{"_id", "NAME"}, null, null, null, null, null);
            CursorAdapter spinnerAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor_spinner,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            spinner.setAdapter(spinnerAdapter);

            cursor_list=db.rawQuery("SELECT EXCERCISES._id, NAME,SERIES FROM EXCERCISES," + trainingName + " WHERE EXCERCISES._id=EXCERCISE_ID", null);
            adapter=new ActiveTrainingAdapter(this,cursor_list,0);
            listView = (ListView) findViewById(R.id.excercise_list);
            listView.setAdapter(adapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,getString(R.string.db_error), Toast.LENGTH_SHORT);
            toast.show();
        }

        setTitle(getString(R.string.active_training)+ trainingName);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = createAlterDialog(id);
                dialog.show();
            }
        });

    }

    public void onClickAddExc(View view) {//po kliknięciu przycisku "dodaj ćwiczenie"
        try {

            Spinner excercises = (Spinner) findViewById(R.id.choose_excercise);
            Spinner series=(Spinner)findViewById(R.id.choose_series);
            int chosenSeries=series.getSelectedItemPosition();
            int numbSeries=0;
            switch (chosenSeries)
            {
                case 0:
                    numbSeries=1;
                    break;
                case 1:
                    numbSeries=2;
                    break;
                case 2:
                    numbSeries=3;
                    break;
                case 3:
                    numbSeries=4;
                    break;
                case 4:
                    numbSeries=5;
                    break;
                default:
                    break;
            }
            int clickedId = excercises.getSelectedItemPosition();
            cursor=db.query("EXCERCISES",new String[]{"_id"},null,null,null,null,null);
            int excId=0;
            if(cursor.moveToFirst())
            {
                if(clickedId==0)
                    excId=cursor.getInt(0);
                else if(cursor.move(clickedId))
                    excId=cursor.getInt(0);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("EXCERCISE_ID", excId);
            contentValues.put("SERIES",numbSeries);
            db.insert(trainingName, null, contentValues);
            cursor.close();

            cursor_list_new = db.rawQuery("SELECT EXCERCISES._id, NAME,SERIES FROM EXCERCISES," + trainingName + " WHERE EXCERCISES._id=EXCERCISE_ID", null);
            CursorAdapter adapter = (CursorAdapter) listView.getAdapter();
            cursor_list = cursor_list_new;
            adapter.changeCursor(cursor_list);
        }
        catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this,getString(R.string.db_error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickStart(View v)//po kliknięciu przycisku "start"
    {
        Intent intent=new Intent(this,newTrainingActivity.class);
        intent.putExtra(TrainingActivity.EXTRA_TRAINING_NAME,trainingName);
        startActivity(intent);
    }


    private Dialog createAlterDialog(long id)//tworzenie okna dialogowego
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] options = new String[]{getString(R.string.delete)};
        final long _id = id;
        dialogBuilder.setTitle(getString(R.string.choose_option));
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case (0):
                        db.delete(trainingName, "EXCERCISE_id=?", new String[]{Long.toString(_id)});
                        cursor_list_new = db.rawQuery("SELECT EXCERCISES._id, NAME,SERIES FROM EXCERCISES," + trainingName + " WHERE EXCERCISES._id=EXCERCISE_ID", null);
                        CursorAdapter adapter = (CursorAdapter) listView.getAdapter();
                        cursor_list = cursor_list_new;
                        adapter.changeCursor(cursor_list);
                        break;
                    default:
                        break;
                }
            }
        });
        return dialogBuilder.create();
    }
}
