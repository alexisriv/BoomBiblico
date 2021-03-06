package com.sixelasavir.www.boombiblico;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;

import java.util.List;

/**
 * Created by alexis on 08/09/17.
 */

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.ViewHolder> {

    private Context context;
    private List<GamerRecord> gamerRecords;

    public AdapterRecord(Context context, List<GamerRecord> gamerRecords) {
        this.context = context;
        this.gamerRecords = gamerRecords;
    }

    @Override
    public AdapterRecord.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new AdapterRecord.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecord.ViewHolder holder, int position) {
        GamerRecord gamerRecord = this.gamerRecords.get(position);

        if (gamerRecord.getPosition() != Integer.valueOf(0)) {
            if (gamerRecord.getPosition() == Integer.valueOf(1)) {
                holder.principalImageView.setImageResource(R.mipmap.ic_medal_one);
            } else if (gamerRecord.getPosition() == Integer.valueOf(2)) {
                holder.principalImageView.setImageResource(R.mipmap.ic_medal_two);
            } else if (gamerRecord.getPosition() == Integer.valueOf(3)) {
                holder.principalImageView.setImageResource(R.mipmap.ic_medal_three);
            }
        } else {
            if (gamerRecord.getType().equals(MenuActivity.TYPE_PLAYER_AVENTURERO))
                holder.principalImageView.setImageResource(R.mipmap.ic_aventureros);
            if (gamerRecord.getType().equals(MenuActivity.TYPE_PLAYER_CONQUISTADOR))
                holder.principalImageView.setImageResource(R.mipmap.ic_conquistadores);
            if (gamerRecord.getType().equals(MenuActivity.TYPE_PLAYER_GUIA_MAYOR))
                holder.principalImageView.setImageResource(R.mipmap.ic_guias_mayores);
        }

        holder.nameTextView.setText(gamerRecord.getNameGamer());
        holder.timerTextView.setText(gamerRecord.getTimerRecordGamer());
        int numberQuestion = 0;
        switch (gamerRecord.getNumber()) {
            case 0:
                numberQuestion = 5;
                break;
            case 1:
                numberQuestion = 10;
                break;
            case 2:
                numberQuestion = 15;
                break;
            default:
                numberQuestion = 20;
                break;
        }
        holder.numberTextView.setText(gamerRecord.getRecordGamer().toString().concat("/").concat(Integer.toString(numberQuestion)));

    }

    @Override
    public int getItemCount() {
        return this.gamerRecords.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView principalImageView;
        public TextView nameTextView;
        public TextView timerTextView;
        public TextView numberTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            principalImageView = (ImageView) itemView.findViewById(R.id.medal_image_view);
            nameTextView = (TextView) itemView.findViewById(R.id.name_record_item);
            timerTextView = (TextView) itemView.findViewById(R.id.timer_record_item);
            numberTextView = (TextView) itemView.findViewById(R.id.number_record_text_view);
        }
    }
}
