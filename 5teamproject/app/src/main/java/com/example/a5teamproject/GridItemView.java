 package com.example.a5teamproject;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import androidx.appcompat.widget.AppCompatTextView;

public class GridItemView extends AppCompatTextView {

    private GridItem item;

    public GridItemView(Context context){
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setBackgroundColor(Color.WHITE);
    }

    public GridItem getItem(){
        return item;
    }

    public void setItem(GridItem item){
        this.item = item;

        int day = item.getDay();
        if (day != 0) {
            setText(String.valueOf(day));
            setGravity(Gravity.CENTER);
            setTextColor(Color.BLACK);  
            setTextSize(20);
        }else{
            setText("");
        }
    }
}