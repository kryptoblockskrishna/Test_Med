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

public class RecyclerAdapterHomeServices extends RecyclerView.Adapter<RecyclerAdapterHomeServices.RecyclerViewHolderHomeServices> {

    ArrayList<ListProviderHomeServices> arrayList = new ArrayList<ListProviderHomeServices>();
            public RecyclerAdapterHomeServices(ArrayList<ListProviderHomeServices> arrayList){
                this.arrayList=arrayList;
            }

    public static class RecyclerViewHolderHomeServices extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public RecyclerViewHolderHomeServices(View view){
            super(view);

            imageView = (ImageView) itemView.findViewById(R.id.home_srv_rcyclr_imageview);
            textView = (TextView) itemView.findViewById(R.id.home_srv_rcyclr_textview);
        }
    }

    @Override
    public RecyclerViewHolderHomeServices onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolderHomeServices r;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_services,parent,false);
        r = new RecyclerViewHolderHomeServices(view);


        return r;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderHomeServices holder, int position) {
        ListProviderHomeServices l;

        l= arrayList.get(position);
        holder.imageView.setImageResource(l.getImg_id());
        holder.textView.setText(l.getString());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
