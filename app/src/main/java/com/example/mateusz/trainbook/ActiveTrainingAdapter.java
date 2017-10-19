package com.example.mateusz.trainbook;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Mateusz on 2017-10-12.
 */

public class ActiveTrainingAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public ActiveTrainingAdapter(Context context,Cursor cursor,int flags)
    {
        super(context,cursor,flags);
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return inflater.inflate(R.layout.active_training_list,parent,false);

    }

    @Override
    public void bindView(View v, Context context, Cursor cursor) {
        TextView at_name=(TextView)v.findViewById(R.id.at_name);
        TextView at_series=(TextView)v.findViewById(R.id.at_series);
        String name=cursor.getString(1);
        String series=cursor.getString(2);
        at_name.setText(name);
        at_series.setText(series);
    }
}
