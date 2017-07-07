package com.transenigma.mediappb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import layout.RecyclerItemClickListener;

public class DoctorsList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ListproviderDocList> arrayList = new ArrayList<ListproviderDocList>();
    String[] Name, Degree, Hospital, Availability;
    int[] img_id = new int[]{R.drawable.doc_sample, R.drawable.doc_sample, R.drawable.doc_sample,
            R.drawable.doc_sample, R.drawable.doc_sample, R.drawable.doc_sample, R.drawable.doc_sample,
            R.drawable.doc_sample, R.drawable.doc_sample, R.drawable.doc_sample,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Name = getResources().getStringArray(R.array.doctor);
        Degree = getResources().getStringArray(R.array.degree);
        Hospital = getResources().getStringArray(R.array.hospitals);
        Availability=getResources().getStringArray(R.array.availability);

        int i=0;
        for(String NAME : Name){
            ListproviderDocList listProvider = new ListproviderDocList(img_id[i], Name[i], Degree[i], Hospital[i], Availability[i]);
            arrayList.add(listProvider);
            i++;
        }

        adapter = new RecyclerAdapterDocList(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View v, int position) {
                        Intent i = new Intent(v.getContext(), Doctors_Profile.class);
                        i.putExtra("Calling_From",1);
                        i.putExtra("Position",position);
                        startActivity(i);
                    }
                })
        );
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
