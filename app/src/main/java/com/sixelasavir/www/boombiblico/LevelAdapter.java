package com.sixelasavir.www.boombiblico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by alexis on 29/08/17.
 */

public class LevelAdapter extends BaseAdapter {

    private Context context;

    public LevelAdapter(Context context, String type) {
        this.context = context;
        switch (type){
            case MenuActivity.TYPE_PLAYER_AVENTURERO:
                number = numberA;
                break;
            case MenuActivity.TYPE_PLAYER_CONQUISTADOR:
                number = numberC;
                break;
            case MenuActivity.TYPE_PLAYER_GUIA_MAYOR:
                number = numberGM;
                break;
        }
    }

    @Override
    public int getCount() {
        return number.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.level_item,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_number);
        imageView.setImageDrawable(context.getResources().getDrawable(number[position]));
        return convertView;
    }


    private int[] number;
    
    private int[] numberA = {
            R.drawable.im_uno,
            R.drawable.im_dos,
            R.drawable.im_tres
    };

    private int[] numberC = {
            R.drawable.im_uno,
            R.drawable.im_dos,
            R.drawable.im_tres
    };

    private int[] numberGM = {
            R.drawable.im_uno,
            R.drawable.im_dos
    };

}
