package com.example.mateusz.trainbook;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainingActivity extends AppCompatActivity {

    public static final String EXTRA_TRAINING_NAME="extraTrainingName";
    public static final String EXTRA_TRAINING_DESCRIPTION="exctraTrainingDescription";
    private String trainingName;
    private String trainingDescription;
    private TextView timer;
    private int seconds;
    private int minutes;
    private int secs;
    private int mSecs;
    private String time;
    private boolean running=false;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String actExcName;
    private int actExcSeries;
    private boolean thersnonext=false;
    private RadioButton RB1,RB2,RB3,RB4,RB5;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//ekran ciągle włączony
        //pobranie referencji do radiobuttonów
        RB1=(RadioButton)findViewById(R.id.radioButton1);
        RB2=(RadioButton)findViewById(R.id.radioButton2);
        RB3=(RadioButton)findViewById(R.id.radioButton3);
        RB4=(RadioButton)findViewById(R.id.radioButton4);
        RB5=(RadioButton)findViewById(R.id.radioButton5);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");//pobranie aktualnej daty
        date=dateFormat.format(new Date());
        trainingName=getIntent().getStringExtra(EXTRA_TRAINING_NAME);
        trainingDescription=getIntent().getStringExtra(EXTRA_TRAINING_DESCRIPTION);
        timer=(TextView)findViewById(R.id.timer_text);
        TextView textNameExc=(TextView)findViewById(R.id.textNameExcercise);
        TextView textDescriptionExc=(TextView)findViewById(R.id.textDescriptionExcercise);
        textNameExc.setText(trainingName);
        textDescriptionExc.setText(trainingDescription);
        seconds=30;//domyśla długość przerwy
        mSecs=seconds;
        try
        {
            TrainingDataBaseHelper helper = new TrainingDataBaseHelper(this);
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("SELECT NAME,SERIES FROM EXCERCISES," + trainingName + " WHERE EXCERCISES._id=EXCERCISE_ID", null);
            if (cursor.moveToFirst())
            {
                actExcName = cursor.getString(0);
                actExcSeries = cursor.getInt(1);
            }

        }
        catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, getString(R.string.db_error), Toast.LENGTH_SHORT);
            toast.show();
        }
        setAllRButtonsChecked();
        updateExc();
    }

    private void setAllRButtonsChecked()//zaznaczenie wszystkich przycisków
    {
        RB1.setChecked(true);
        RB2.setChecked(true);
        RB3.setChecked(true);
        RB4.setChecked(true);
        RB5.setChecked(true);
    }

    private boolean areAllChecked()//sprawdzenie czy wszystkie radiobuttony są zaznaczone
    {
        if(RB1.isChecked()&&RB2.isChecked()&&RB3.isChecked()&&RB4.isChecked()&&RB5.isChecked())
            return true;
        else
            return false;
    }


    public void onClickOkBreak(View view)//zatwierdzenie długości przerwy
    {
        running=false;
        Spinner chooseBreak=(Spinner)findViewById(R.id.spinner);
        int breakClicked=chooseBreak.getSelectedItemPosition();
        switch(breakClicked)
        {
            case 0:
                seconds=30;
                break;
            case 1:
                seconds=60;
                break;
            case 2:
                seconds=120;
                break;
            case 3:
                seconds=180;
                break;
            default:
                break;
        }
        mSecs=seconds;
        minutes=seconds/60;
        secs=seconds%60;
        time=String.format("%d:%02d",minutes,secs);
        timer.setText(time);
    }

    //po klknięciu radiobuttonów
    public void onClickRButton1(View view)
    {
        if(!running) {
            running=true;
            runTimer();
        }
        else {
            seconds=mSecs;
        }
        if(thersnonext && areAllChecked())//jeśli ostatnie ćwiczenie i wszystkie serie
        {
            Dialog dialog=createAlterDialog();
            dialog.show();//wyświetl okno dialogowe
        }
    }
    public void onClickRButton2(View view)
    {
        if(!running) {
            running=true;
            runTimer();
        }
        else {
            seconds=mSecs;
        }
        if(thersnonext && areAllChecked())//jeśli ostatnie ćwiczenie i wszystkie serie
        {
            Dialog dialog=createAlterDialog();
            dialog.show();//wyświetl okno dialogowe
        }
    }
    public void onClickRButton3(View view)
    {
        if(!running) {
            running=true;
            runTimer();
        }
        else {
            seconds=mSecs;
        }
        if(thersnonext && areAllChecked())//jeśli ostatnie ćwiczenie i wszystkie serie
        {
            Dialog dialog=createAlterDialog();
            dialog.show();//wyświetl okno dialogowe
        }
    }
    public void onClickRButton4(View view)
    {
        if(!running) {
            running=true;
            runTimer();
        }
        else {
            seconds=mSecs;
        }
        if(thersnonext && areAllChecked())//jeśli ostatnie ćwiczenie i wszystkie serie
        {
            Dialog dialog=createAlterDialog();
            dialog.show();//wyświetl okno dialogowe
        }
    }
    public void onClickRButton5(View view)
    {
        if(!running) {
            running=true;
            runTimer();
        }
        else {
            seconds=mSecs;
        }
        if(thersnonext && areAllChecked())//jeśli ostatnie ćwiczenie i wszystkie serie
        {
            Dialog dialog=createAlterDialog();
            dialog.show();//wyświetl okno dialogowe
        }
    }
    private void runTimer()
    {
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds==0)//gdy skończy się odliczanie
                {
                    running=false;//zastopuj timer
                    seconds=mSecs;//zresetuj sekundy
                    MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.alarm);
                    mp.start();//odtwórz dźwięk
                }
                minutes=seconds/60;
                secs=seconds%60;
                time=String.format("%d:%02d",minutes,secs);
                timer.setText(time);
                if(running)
                {
                    seconds--;
                    handler.postDelayed(this,1000);
                }
            }
        });
    }
    private void updateExc()
    {
        TextView excName=(TextView)findViewById(R.id.active_exc_name);
        TextView nextExcNameText=(TextView)findViewById(R.id.next_exc_name);
        if(cursor.moveToNext())
        {
            nextExcNameText.setText(getString(R.string.next)+cursor.getString(0));
            cursor.moveToPrevious();
        }
        else
        {
            nextExcNameText.setText(getString(R.string.next)+getString(R.string.lack));
            thersnonext=true;
        }
        excName.setText(actExcName);
        switch (actExcSeries)//ustawienie odpowiedniej ilości radiobuttonów (w zależności od serii)
        {
            case 1://np. jeśli jedna seria: jeden przycisk jest widoczny, reszta niewidoczna - zazanczona w celu rozpoznania kiedy koniec
                RB3.setVisibility(View.VISIBLE);
                RB1.setVisibility(View.INVISIBLE);
                RB2.setVisibility(View.INVISIBLE);
                RB4.setVisibility(View.INVISIBLE);
                RB5.setVisibility(View.INVISIBLE);

                RB1.setChecked(true);
                RB2.setChecked(true);
                RB3.setChecked(false);
                RB4.setChecked(true);
                RB5.setChecked(true);
                break;
            case 2:
                RB2.setVisibility(View.VISIBLE);
                RB4.setVisibility(View.VISIBLE);
                RB1.setVisibility(View.INVISIBLE);
                RB3.setVisibility(View.INVISIBLE);
                RB5.setVisibility(View.INVISIBLE);

                RB1.setChecked(true);
                RB2.setChecked(false);
                RB3.setChecked(true);
                RB4.setChecked(false);
                RB5.setChecked(true);
                break;
            case 3:
                RB2.setVisibility(View.VISIBLE);
                RB3.setVisibility(View.VISIBLE);
                RB4.setVisibility(View.VISIBLE);
                RB1.setVisibility(View.INVISIBLE);
                RB5.setVisibility(View.INVISIBLE);

                RB1.setChecked(true);
                RB2.setChecked(false);
                RB3.setChecked(false);
                RB4.setChecked(false);
                RB5.setChecked(true);
                break;
            case 4:
                RB1.setVisibility(View.VISIBLE);
                RB2.setVisibility(View.VISIBLE);
                RB4.setVisibility(View.VISIBLE);
                RB5.setVisibility(View.VISIBLE);
                RB3.setVisibility(View.INVISIBLE);

                RB1.setChecked(false);
                RB2.setChecked(false);
                RB3.setChecked(true);
                RB4.setChecked(false);
                RB5.setChecked(false);
                break;
            case 5:
                RB1.setVisibility(View.VISIBLE);
                RB2.setVisibility(View.VISIBLE);
                RB4.setVisibility(View.VISIBLE);
                RB5.setVisibility(View.VISIBLE);
                RB3.setVisibility(View.VISIBLE);

                RB1.setChecked(false);
                RB2.setChecked(false);
                RB3.setChecked(false);
                RB4.setChecked(false);
                RB5.setChecked(false);
                break;
            default:
                break;
        }
    }
    public void onClickNextExc(View view)//po kliknięciu przycisku "następne"
    {
        if(cursor.moveToNext())
        {
            actExcName = cursor.getString(0);
            actExcSeries = cursor.getInt(1);
            updateExc();
        }
        else
        {
            Toast toast = Toast.makeText(this, getString(R.string.lack_of_excercises), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private Dialog createAlterDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] options = new String[]{getString(R.string.yes),getString(R.string.no)};
        dialogBuilder.setTitle(getString(R.string.rem_training));
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        try
                        {
                            TrainingDataBaseHelper helper=new TrainingDataBaseHelper(getApplicationContext());
                            SQLiteDatabase db_wr=helper.getWritableDatabase();
                            ContentValues contentValues=new ContentValues();
                            contentValues.put("TRAINING",trainingName);
                            contentValues.put("DATE",date);
                            db_wr.insert("HISTORY",null,contentValues);
                            db_wr.close();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        catch (SQLiteException e)
                        {
                            Toast toast=Toast.makeText(getApplicationContext(),getString(R.string.db_error),Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    case 1:
                        break;
                }
            }
        });
        return dialogBuilder.create();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
