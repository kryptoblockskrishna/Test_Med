package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.transenigma.mediappb.AppointRecyclerAdapter;
import com.transenigma.mediappb.BaseActivity;
import com.transenigma.mediappb.DoctorsList;
import com.transenigma.mediappb.ListProviderAppoint;
import com.transenigma.mediappb.R;

import java.util.ArrayList;

import static com.transenigma.mediappb.R.id.parent;
import static com.transenigma.mediappb.R.id.start;
import static com.transenigma.mediappb.R.id.tabMode;

public class AppointmentFragment extends Fragment {

    /*FloatingActionButton fabplus;
    public AppointmentFragment() {
        // Required empty public constructor
    }*/

    Spinner hosp;
    Spinner Dept;
    View v;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ListProviderAppoint> appoints = new ArrayList<ListProviderAppoint>();
    String[] city,state,hospital,department;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_appointment, container, false);
        hosp = (Spinner) v.findViewById(R.id.select_hospital);
        Dept = (Spinner) v.findViewById(R.id.select_department);

        recyclerView = (RecyclerView)v.findViewById(R.id.appoint_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        city = getResources().getStringArray(R.array.recent_search_city);
        state = getResources().getStringArray(R.array.recent_search_state);
        hospital = getResources().getStringArray(R.array.recent_search_hosp);
        department = getResources().getStringArray(R.array.recent_search_dept);


        for(int c =0;c<5;c++){
            ListProviderAppoint appoint = new ListProviderAppoint(department[c],hospital[c],city[c],state[c]);
            appoints.add(appoint);

        }
        adapter = new AppointRecyclerAdapter(appoints);
        recyclerView.setAdapter(adapter);



        /*hosp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        Toast.makeText(v.getContext() , "Position 0 Selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(v.getContext() , "Position 1 Selected",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(v.getContext() , "Position 2 Selected",Toast.LENGTH_SHORT).show();
                        break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_appoint);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;

    }

}
