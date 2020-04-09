package com.example.corona;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private ArrayList<DetailItem> detailItems;

    public static class DetailViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_pro, tv_pos, tv_sem, tv_men;

        public DetailViewHolder(@NonNull View itemView){
            super(itemView);

        tv_pro = itemView.findViewById(R.id.tv_provinsid);
        tv_pos = itemView.findViewById(R.id.tv_positifd);
        tv_sem = itemView.findViewById(R.id.tv_sembuhd);
        tv_men = itemView.findViewById(R.id.tv_meninggald);
    }
}

public DetailAdapter(ArrayList<DetailItem> detailItems1){
    detailItems = detailItems1;
}

@NonNull
@Override
public DetailAdapter.DetailViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
    DetailViewHolder pvh = new DetailViewHolder(v);

    return pvh;
}

@Override
public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, int position){
    DetailItem curItem = detailItems.get(position);

    holder.tv_pro.setText(curItem.getTv_provinsid());
    holder.tv_pos.setText(curItem.getTv_positifd());
    holder.tv_sem.setText(curItem.getTv_sembuhd());
    holder.tv_men.setText(curItem.getTv_meninggald());
}

@Override
public int getItemCount(){
    return detailItems.size();
}
}