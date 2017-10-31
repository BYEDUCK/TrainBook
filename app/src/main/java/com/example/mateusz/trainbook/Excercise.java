package com.example.mateusz.trainbook;

/**
 * Created by Mateusz on 2017-10-31.
 */

public class Excercise {
    private String excerciseName;
    private int series;
    private boolean selectedDone;
    private boolean selected;
    public Excercise(String name,int series){
        this.excerciseName=name;
        this.series=series;
        selectedDone =false;
        selected=false;
    }
    public void selectDone(){
        this.selectedDone =true;
    }
    public void select(){this.selected=true;}
    public void unselect(){this.selected=false;}
    public boolean isSelectedDone(){
        return selectedDone;
    }
    public boolean isSelected(){return this.selected;}
    public String getExcerciseName(){
        return this.excerciseName;
    }
    public int getSeries(){
        return this.series;
    }
}
