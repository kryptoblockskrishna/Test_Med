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

public class RecyclerAdapterDocList extends RecyclerView.Adapter<RecyclerAdapterDocList.RecyclerViewHolderDocList>{

    ArrayList<ListproviderDocList> d_list_arraylist = new ArrayList<ListproviderDocList>();

    public RecyclerAdapterDocList(ArrayList<ListproviderDocList> d_list_arraylist){
        this.d_list_arraylist = d_list_arraylist;

    }

    public static class RecyclerViewHolderDocList extends RecyclerView.ViewHolder{
        ImageView d_list_doc_pic;
        TextView d_list_doc_name, d_list_degree, d_list_hospital, d_list_availability;

        public RecyclerViewHolderDocList(View vDocList){
            super(vDocList);

            d_list_doc_pic = (ImageView) itemView.findViewById(R.id.d_list_docpic);
            d_list_doc_name = (TextView) itemView.findViewById(R.id.d_list_dname);
            d_list_degree = (TextView) itemView.findViewById(R.id.d_list_deg);
            d_list_hospital=(TextView) itemView.findViewById(R.id.d_list_hos);
            d_list_availability = (TextView)itemView.findViewById(R.id.d_list_avail);

        }
    }

    @Override
    public RecyclerViewHolderDocList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolderDocList recyclerViewHolderDocList;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        recyclerViewHolderDocList = new RecyclerViewHolderDocList(view);

        return recyclerViewHolderDocList;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderDocList holder, int position) {
        ListproviderDocList listproviderDocList;
        listproviderDocList = d_list_arraylist.get(position);
        holder.d_list_doc_pic.setImageResource(listproviderDocList.getLpdl_doc_pic());
        holder.d_list_doc_name.setText(listproviderDocList.getLpdl_doc_name());
        holder.d_list_degree.setText(listproviderDocList.getLpdl_degree());
        holder.d_list_hospital.setText(listproviderDocList.getLpdl_hospital());
        holder.d_list_availability.setText(listproviderDocList.getLpdl_availability());
    }



    @Override
    public int getItemCount() {
        return d_list_arraylist.size();
    }
}
