package com.example.mateusz.trainbook;

import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Mateusz on 2017-10-30.
 */

public class InTrainingListAdapter extends ArrayAdapter<Excercise> {

    private LayoutInflater inflater;
    private ArrayList<Excercise> excercises;

    public InTrainingListAdapter(@NonNull Context context, int resource, ArrayList<Excercise> objects) {
        super(context, resource);
        this.excercises=objects;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position){
        Excercise excercise=getItem(position);
        boolean selectedDone=excercise.isSelectedDone();
        boolean selected=excercise.isSelected();
        if(selected)
            return 2;
        else if(selectedDone)
            return 1;
        else
            return 0;
    }
    @Override
    public int getViewTypeCount(){
        return 3;
    }
    @Override
    public Excercise getItem(int position){
        return this.excercises.get(position);
    }

    @Override
    public int getCount(){
        return this.excercises.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row=convertView;
        Excercise now=excercises.get(position);
        switch (getItemViewType(position))
        {
            case(1):
                row=inflater.inflate(R.layout.in_training_list_selected_done,parent,false);
                break;
            case (2):
                row=inflater.inflate(R.layout.in_training_list_selected,parent,false);
                break;
            default:
                row=inflater.inflate(R.layout.in_training_list,parent,false);
                break;
        }
        String name=now.getExcerciseName();
        TextView nameText=(TextView)row.findViewById(R.id.exc_name_inTraining);
        nameText.setText(name);

        return row;
    }
}

