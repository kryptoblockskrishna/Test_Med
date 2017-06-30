package com.transenigma.mediappb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public static class ViewHolderAppoint extends RecyclerView.ViewHolder{
        TextView hosp,dept,city,state;

        public ViewHolderAppoint(View view){
            super(view);

            hosp =(TextView)view.findViewById(R.id.appoint_recycler_hosp);
            dept =(TextView)view.findViewById(R.id.appoint_recycler_dept);
            city =(TextView)view.findViewById(R.id.appoint_recycler_city);
            state =(TextView)view.findViewById(R.id.appoint_recycler_state);
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
        holder.state.setText(l.getState());
        holder.dept.setText(l.getDept());
        holder.hosp.setText(l.getHosp());
        holder.city.setText(l.getCity());
    }

    @Override
    public int getItemCount() {
        return appoints.size();
    }
}
