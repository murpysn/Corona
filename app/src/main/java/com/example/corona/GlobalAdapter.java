package com.example.corona;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.GlobalViewHolder>  {
    private ArrayList<DetailItem> itemGlobal;

    public static class GlobalViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_cou, tv_conf, tv_rec, tv_dea;

        public GlobalViewHolder(@NonNull View itemView){
            super(itemView);

            tv_cou = itemView.findViewById(R.id.tv_provinsid);
            tv_conf = itemView.findViewById(R.id.tv_positifd);
            tv_rec = itemView.findViewById(R.id.tv_sembuhd);
            tv_dea = itemView.findViewById(R.id.tv_meninggald);
        }
    }

    public GlobalAdapter(ArrayList<DetailItem> itemGlobals){
        itemGlobal = itemGlobals;
    }

    @NonNull
    @Override
    public GlobalAdapter.GlobalViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
        GlobalAdapter.GlobalViewHolder pvh = new GlobalAdapter.GlobalViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalAdapter.GlobalViewHolder holder, int position){
        DetailItem temItem = itemGlobal.get(position);

        holder.tv_cou.setText(temItem.getTv_provinsid());
        holder.tv_conf.setText(temItem.getTv_positifd());
        holder.tv_rec.setText(temItem.getTv_sembuhd());
        holder.tv_dea.setText(temItem.getTv_meninggald());
    }

    @Override
    public int getItemCount(){
        return itemGlobal.size();
    }
}
