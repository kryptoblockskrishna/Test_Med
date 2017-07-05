package com.transenigma.mediappb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kishore on 6/24/2017.
 *
 */

public class AppointRecyclerAdapter extends RecyclerView.Adapter<AppointRecyclerAdapter.ViewHolderAppoint> {

    ArrayList<ListProviderAppoint> appoints = new ArrayList<ListProviderAppoint>();
    public AppointRecyclerAdapter(ArrayList<ListProviderAppoint> arrayList){
        this.appoints = arrayList;
    }
    public static class ViewHolderAppoint extends RecyclerView.ViewHolder {
        TextView hosp,dept,name;
        ImageView pic;
        private ViewHolderAppoint(View view){
            super(view);

            hosp =(TextView)view.findViewById(R.id.rcy_pin_doc_hosp);
            dept =(TextView)view.findViewById(R.id.rcy_pin_doc_dept);
            name =(TextView)view.findViewById(R.id.rcy_pin_doc_name);
            pic =(ImageView) view.findViewById(R.id.rcy_pin_doc_iv1);
        }


    }

    @Override
    public ViewHolderAppoint onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolderAppoint appoint;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appoint_recycler,parent,false);
        appoint = new ViewHolderAppoint(view);
        return appoint;
    }

    @Override
    public void onBindViewHolder(ViewHolderAppoint holder, int position) {
        ListProviderAppoint l;
        l = appoints.get(position);
        holder.pic.setImageResource(l.getPic_id());
        holder.name.setText(l.getName());
        holder.dept.setText(l.getDept());
        holder.hosp.setText(l.getHosp());
    }




    @Override
    public int getItemCount() {
        return appoints.size();
    }
}
