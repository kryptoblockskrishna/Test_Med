package com.transenigma.mediappb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kishore on 6/13/2017.
 */

public class RecyclerAdapterHomeBnr extends RecyclerView.Adapter<RecyclerAdapterHomeBnr.RecyclerViewHolderHomeBnr> {

    ArrayList<ListProviderHomeBnr> arrayList = new ArrayList<ListProviderHomeBnr>();

    public  RecyclerAdapterHomeBnr(ArrayList<ListProviderHomeBnr> arrayList){
        this.arrayList = arrayList;

    }
    public static class RecyclerViewHolderHomeBnr extends RecyclerView.ViewHolder{
        ImageView imageView;

        public RecyclerViewHolderHomeBnr(View view){
            super(view);
            imageView = (ImageView) itemView.findViewById(R.id.home_bnr);
        }
    }

    @Override
    public RecyclerViewHolderHomeBnr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolderHomeBnr recyclerViewHolderHomeBnr;
        view = LayoutInflater.from((parent.getContext())).inflate(R.layout.layout_home_bnr,parent,false);
        recyclerViewHolderHomeBnr = new RecyclerViewHolderHomeBnr(view);


        return recyclerViewHolderHomeBnr;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderHomeBnr holder, int position) {
        ListProviderHomeBnr listProviderHomeBnr;
        listProviderHomeBnr = arrayList.get(position);
        holder.imageView.setImageResource(listProviderHomeBnr.getHm_bnr_id());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
