package com.transenigma.mediappb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class dt_list extends AppCompatActivity {

    String[] s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_list);

        s1 = getResources().getStringArray(R.array.dt_list);
        DT_GridAdapter ad = new DT_GridAdapter(dt_list.this,s1);

        GridView gridview = (GridView) findViewById(R.id.dt_list_grid);
        gridview.setAdapter(ad);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(dt_list.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
