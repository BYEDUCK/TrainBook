package com.example.mateusz.trainbook;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mateusz on 2017-08-23.
 */

public class CardTrainingAdapter extends RecyclerView.Adapter<CardTrainingAdapter.ViewHolder> {


    private Cursor cursor;
    private String up;
    private String down;
    static interface iButtonClicked//przy kliknięciu "kosza" na karcie
    {
        void buttonClicked(int position);
    }
    static interface icardListener//przy kliknięciu karty
    {
        void cardClicked(int position);
    }
    private iButtonClicked listener;
    private icardListener c_listener;
    public CardTrainingAdapter(Cursor cursor)
    {
        this.cursor=cursor;
    }//konstruktor

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_training,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        CardView cardView=holder.cardView;
        TextView textView_name=(TextView)cardView.findViewById(R.id.textView_name);
        TextView textView_desc=(TextView)cardView.findViewById(R.id.textView_desc);
        if(holder.getAdapterPosition()==0)
        {
            if (cursor.moveToFirst())//jeśli pierwszy element
            {
                up = cursor.getString(0);
                down =cursor.getString(1);
            }
        }
        else if(cursor.moveToPosition(position))//jeśli kolejny
        {
            up = cursor.getString(0);
            down =cursor.getString(1);
        }

        textView_name.setText(up);//przypisanie tekstów
        textView_desc.setText(down);
        Context context=cardView.getContext();
        this.listener=(iButtonClicked)context;//przypisanie nasłuchiwaczy
        this.c_listener=(icardListener)context;
        Button button=(Button)cardView.findViewById(R.id.button_delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.buttonClicked(position);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c_listener!=null)
                    c_listener.cardClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView;
        public ViewHolder(CardView v)
        {
            super(v);
            cardView=v;
        }
    }



}
