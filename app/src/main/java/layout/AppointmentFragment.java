package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import static com.transenigma.mediappb.R.id.wrap;
import static com.transenigma.mediappb.R.id.wrap_content;

public class AppointmentFragment extends Fragment {

    /*FloatingActionButton fabplus;
    public AppointmentFragment() {
        // Required empty public constructor
    }*/
    CardView c1,c2;
    TextView t1;
    LinearLayout l1;
    ViewGroup.LayoutParams lp1;
    ViewGroup.LayoutParams lp2;
    int i1=0 ,i2 = 0;
    int img_ids[] = {R.drawable.ic_person,R.drawable.ic_person,R.drawable.ic_person,R.drawable.ic_person,R.drawable.ic_person};
    Spinner hosp;
    Spinner Dept;
    View v;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ListProviderAppoint> appoints = new ArrayList<ListProviderAppoint>();
    String[] name,hospital,department;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_appointment, container, false);
        hosp = (Spinner) v.findViewById(R.id.select_hospital);
        Dept = (Spinner) v.findViewById(R.id.select_department);

        c1= (CardView)v.findViewById(R.id.pinned_doctors_card);
        c2=(CardView)v.findViewById(R.id.search_doc_card);
        t1=(TextView)v.findViewById(R.id.pinned_doc_text);
        l1=(LinearLayout) v.findViewById(R.id.search_doc_ll);
        lp1=  c1.getLayoutParams();
        lp2=  c2.getLayoutParams();

        lp1.height=220*5-200;
        c1.setLayoutParams(lp1);

        lp2.height=0;
        c2.setLayoutParams(lp2);

        //c1.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20));
        //c2.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20));

        recyclerView = (RecyclerView)v.findViewById(R.id.appoint_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        name = getResources().getStringArray(R.array.rcy_pin_doc_name);
        hospital = getResources().getStringArray(R.array.rcy_pin_hosp);
        department = getResources().getStringArray(R.array.rcy_pin_dept);


        for(int c =0;c<5;c++){
            ListProviderAppoint appoint = new ListProviderAppoint(img_ids[c],name[c],hospital[c],department[c]);
            appoints.add(appoint);

        }
        adapter = new AppointRecyclerAdapter(appoints);
        recyclerView.setAdapter(adapter);

        t1.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(i1==0){
                    lp1.height=220*5-220;
                    lp2.height=0;
                    c1.setLayoutParams(lp1);
                    c2.setLayoutParams(lp2);
                    i2=0;
                    i1=1;
                }
                else {
                    lp1.height=220;
                    c1.setLayoutParams(lp1);
                    i1=0;
                }
            }
        });

        l1.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(i2==0){
                    lp2.height=770;
                    c2.setLayoutParams(lp2);
                    i2=1;
                    lp1.height=220;
                    c1.setLayoutParams(lp1);
                    i1=0;
                }
                else {
                    lp2.height=0;
                    c2.setLayoutParams(lp2);
                    i2=0;
                }
            }
        });


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

        return v;

    }


}
