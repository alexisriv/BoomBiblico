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


    public LevelAdapter(Context context) {
        this.context = context;
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
        imageView.setImageResource(number[position]);
        return convertView;
    }

    private int[] number = {
            R.drawable.im_uno,
            R.drawable.im_dos,
            R.drawable.im_tres,
            R.drawable.im_cuatro
    };

}
