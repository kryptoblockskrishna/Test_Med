package com.transenigma.mediappb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kishore on 6/15/2017.
 */

public class RecyclerAdapterHomeDT extends RecyclerView.Adapter<RecyclerAdapterHomeDT.RecyclerViewHolderHomeDT>{

    ArrayList<ListProviderHomeDT> arrayList=new ArrayList<ListProviderHomeDT>();
    public RecyclerAdapterHomeDT(ArrayList<ListProviderHomeDT>arrayList){
        this.arrayList = arrayList;
    }

    public class RecyclerViewHolderHomeDT extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public RecyclerViewHolderHomeDT (View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.home_dt_rcyclr_imageview);
            textView = (TextView)v.findViewById(R.id.home_dt_rcyclr_textview);
        }

    }
    @Override
    public RecyclerViewHolderHomeDT onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolderHomeDT r;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_dygtest,parent,false);
        r= new RecyclerViewHolderHomeDT(view);

        return r;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderHomeDT holder, int position) {
        ListProviderHomeDT l;
        l = arrayList.get(position);
        holder.imageView.setImageResource(l.getImg_id());
        holder.textView.setText(l.getTitle());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
