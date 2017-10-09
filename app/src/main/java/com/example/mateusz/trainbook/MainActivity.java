package com.example.mateusz.trainbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements CardTrainingAdapter.iButtonClicked,CardTrainingAdapter.icardListener {

    private TabLayout tabLayout;
    private int currentTab;
    private SQLiteDatabase db;
    private Cursor cursor_new;
    private TrainingDataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager());
        ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);//utworzenie elementu ViewPager
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);//połączenie go z elementem TabLayout
        try
        {
            helper = new TrainingDataBaseHelper(this);
            db = helper.getReadableDatabase();
        }
        catch (SQLiteException e)
        {
            Toast toast=Toast.makeText(this,getString(R.string.db_error),Toast.LENGTH_SHORT);
            toast.show();
        }
        if(savedInstanceState!=null) //w przypadku gdy urządzenie zostanie np obrócone, przywróc otwartą poprzednio strone
            viewPager.setCurrentItem(savedInstanceState.getInt("position"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)//zapamietaj otwarty fragment przy obrocie urządzenia
    {
        savedInstanceState.putInt("position",tabLayout.getSelectedTabPosition());
    }


    public void onClickAdd(View v)//po kilknieciu przycisku
    {
        Intent intent;
        currentTab=tabLayout.getSelectedTabPosition();//pobranie numeru aktualnej karty
        switch(currentTab)
        {
            case(0)://jesli zostal klikniety przycisk przy wyswietlonym fragmencie ExcerciseListFragment
                intent=new Intent(this,ExcersiseInfoInsertActivity.class);
                startActivity(intent);
                break;
            case(1)://jesli zostal klikniety przycisk przy wyswietlonym fragmencie TrainingListFragment
                intent=new Intent(this,TrainingInfoInsertActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    public void buttonClicked(int position) //po kliknieciu przycisku na elemencie CardView
    {                                       //dodac akutalizacje widoki po kliknieciu!!
        try
        {
            db = helper.getReadableDatabase();
            currentTab = tabLayout.getSelectedTabPosition();
            switch (currentTab) {
                case (0):
                    cursor_new = db.query("EXCERCISES", new String[]{"NAME", "DESCRIPTION"}, null, null, null, null, null);
                    String exc_name = "";
                    if (cursor_new.move(position + 1))
                        exc_name = cursor_new.getString(0);//pobranie nazwy wybranego cwiczenia
                    db.delete("EXCERCISES", "NAME=?", new String[]{exc_name});//usuniecie wybranego cwiczenia z bazy
                    break;
                case (1):
                    String trainingName = "";
                    cursor_new = db.query("TRAININGS", new String[]{"TRAINING_NAME"}, null, null, null, null, null);
                    if (cursor_new.move(position + 1))
                        trainingName = cursor_new.getString(0);
                    db.execSQL("DROP TABLE " + trainingName + ";");
                    db.delete("TRAININGS", "TRAINING_NAME=?", new String[]{trainingName});
                    break;
                case (2):
                    db.delete("HISTORY",null,null);
                    break;
                default:
                    break;
            }
        }
        catch (SQLiteException e)
        {
            Toast toast=Toast.makeText(this,getString(R.string.db_error),Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        /*if(cursor_new!=null && db!=null)//nothing's happening -_-
        {
            cursor_new.close();
            db.close();
        }*/
    }

    @Override
    public void cardClicked(int position) //po klikneciu karty
    {
        currentTab=tabLayout.getSelectedTabPosition();
        switch (currentTab) {
            case (0)://jesli cwiczenie
                break;
            case (1)://jesli trening
                Intent intent = new Intent(this, ActiveTrainingActivity.class);
                intent.putExtra(ActiveTrainingActivity.EXTRA_TRAININGID, position);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
