package com.example.mateusz.trainbook;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
public class HistoryFragment extends Fragment implements MainActivity.IitemRemoved {

    private SQLiteDatabase db;
    private Cursor cursor;
    private RecyclerView history_recycler;
    private CardTrainingAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        history_recycler = (RecyclerView) inflater.inflate(R.layout.fragment_history, container, false);
        try {
            TrainingDataBaseHelper helper = new TrainingDataBaseHelper(inflater.getContext());
            db = helper.getReadableDatabase();
            cursor = db.query("HISTORY", new String[]{"TRAINING", "DATE"}, null, null, null, null, null);
            adapter = new CardTrainingAdapter(cursor,R.color.colorHist);
            history_recycler.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(inflater.getContext());
            history_recycler.setLayoutManager(manager);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(inflater.getContext(),getString(R.string.db_error), Toast.LENGTH_SHORT);
            toast.show();
        }
        return history_recycler;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        MainActivity activity=(MainActivity)context;
        activity.setIitemRemovedHist(this);
    }

    @Override
    public void itemRemoved(Cursor cursor) {
        CardTrainingAdapter adapter_new=new CardTrainingAdapter(cursor,R.color.colorHist);
        history_recycler.swapAdapter(adapter_new,false);
        adapter=adapter_new;
    }
}
