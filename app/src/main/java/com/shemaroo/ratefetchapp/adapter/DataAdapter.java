package com.shemaroo.ratefetchapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shemaroo.ratefetchapp.R;
import com.shemaroo.ratefetchapp.model.RecentList;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    Activity activity;

    List<RecentList> healer;

    public DataAdapter(List<RecentList> healer, Context context) {
        this.healer = healer;
        this.context = context;
        this.activity = activity;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RecentList heal=healer.get(position);




        //DisplayImage function from ImageLoader Class

        holder.tDate.setText(heal.getTradeDate());
        holder.usd.setText(heal.getUsd());
        holder.gbp.setText(heal.getGbp());
        holder.euro.setText(heal.getEuro());
        holder.yen.setText(heal.getYen());






        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return healer.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tDate,usd,euro,gbp,yen;
        Vibrator vibe= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        public ViewHolder(final View itemView) {
            super(itemView);

            tDate         = (TextView) itemView.findViewById(R.id.tv);
            usd           = (TextView) itemView.findViewById(R.id.tv2);
            euro          = (TextView) itemView.findViewById(R.id.tv4) ;
            gbp           = (TextView) itemView.findViewById(R.id.tv3);
            yen           = (TextView) itemView.findViewById(R.id.tv5);



            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
//
                }
            });
        }
    }
}
