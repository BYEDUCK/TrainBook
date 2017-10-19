package com.example.mateusz.trainbook;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExcerciseListMaterialFragment extends Fragment implements MainActivity.IitemRemoved{


    private SQLiteDatabase db;
    private Cursor cursor;
    private RecyclerView recyclerView;
    private CardTrainingAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView=(RecyclerView)inflater.inflate(R.layout.fragment_excercise_list_material, container, false);
        try
        {
            SQLiteOpenHelper helper=new TrainingDataBaseHelper(inflater.getContext());
            db=helper.getReadableDatabase();
            cursor=db.query("EXCERCISES",new String[]{"NAME","DESCRIPTION"},null,null,null,null,null);
            adapter=new CardTrainingAdapter(cursor,R.color.colorExc);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager=new LinearLayoutManager(inflater.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
        catch (SQLiteException e)
        {
            Toast toast=Toast.makeText(inflater.getContext(),getString(R.string.db_error),Toast.LENGTH_SHORT);
            toast.show();
        }


        return  recyclerView;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        db.close();
        cursor.close();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        MainActivity activity=(MainActivity)context;
        activity.setIitemRemovedExc(this);
    }

    @Override
    public void itemRemoved(Cursor cursor) {
        //adapter.notifyItemRemoved(position);
        //adapter.notifyItemRangeChanged(position,range);
        CardTrainingAdapter adapter_new=new CardTrainingAdapter(cursor,R.color.colorExc);
        recyclerView.swapAdapter(adapter_new,false);
        adapter=adapter_new;
    }
}
