package com.transenigma.mediappb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kishore on 6/14/2017.
 */

public class RecyclerAdapterHomeBT extends RecyclerView.Adapter<RecyclerAdapterHomeBT.RecyclerViewHolderHomeBT> {

    ArrayList<ListProviderHomeBT> arrayList = new ArrayList<ListProviderHomeBT>();

    public RecyclerAdapterHomeBT(ArrayList<ListProviderHomeBT> arrayList){
        this.arrayList=arrayList;
    }
    public static class RecyclerViewHolderHomeBT extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,price;
        public RecyclerViewHolderHomeBT(View view){
            super(view);

            imageView =(ImageView) view.findViewById(R.id.home_bt_image);
            title =(TextView)view.findViewById(R.id.home_bt_title);
            price = (TextView) view.findViewById(R.id.home_bt_price);

        }
    }

    @Override
    public RecyclerViewHolderHomeBT onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolderHomeBT r;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_bloodtest,parent,false);
        r = new RecyclerViewHolderHomeBT(view);
        return r;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderHomeBT holder, int position) {
        ListProviderHomeBT l;
        l=arrayList.get(position);
        holder.imageView.setImageResource(l.getImg_id());
        holder.title.setText(l.getTitle());
        holder.price.setText(l.getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
