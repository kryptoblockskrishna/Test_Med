package layout;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.transenigma.mediappb.BloodTestDetails;
import com.transenigma.mediappb.Doctors_Profile;
import com.transenigma.mediappb.ListProviderHomeBT;
import com.transenigma.mediappb.ListProviderHomeBnr;
import com.transenigma.mediappb.ListProviderHomeDT;
import com.transenigma.mediappb.ListProviderHomeServices;
import com.transenigma.mediappb.R;
import com.transenigma.mediappb.RecyclerAdapterHomeBT;
import com.transenigma.mediappb.RecyclerAdapterHomeDT;
import com.transenigma.mediappb.RecyclerAdapterHomeServices;

import java.util.ArrayList;

public class TrackFragment extends Fragment {

    RecyclerView recyclerVwHmBT, recyclerVwSrvs, recyclerVwHmDT;
    RecyclerView.Adapter adapterHmBt, adapterSrvs, adapterHmDT;
    RecyclerView.LayoutManager layoutManagerHmBT, layoutManagerSrvs, layoutManagerHmDT;
    ArrayList<ListProviderHomeBnr> arrayList = new ArrayList<ListProviderHomeBnr>();
    ArrayList<ListProviderHomeServices> arrayListSrvs = new ArrayList<ListProviderHomeServices>();
    ArrayList<ListProviderHomeBT> arrayListHmBT = new ArrayList<ListProviderHomeBT>();
    ArrayList<ListProviderHomeDT> arrayListHmDT = new ArrayList<ListProviderHomeDT>();


    String[] name, srvsTxt, btTitle, btPrice, dtTitle;
    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_track, container, false);;

        // Inflate the layout for this fragment



        recyclerVwSrvs =(RecyclerView)v.findViewById(R.id.home_srvs_rcyclrView);
        layoutManagerSrvs = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerVwSrvs.setLayoutManager(layoutManagerSrvs);
        recyclerVwSrvs.setHasFixedSize(true);

        srvsTxt = getResources().getStringArray(R.array.home_srvs);
        TypedArray arsrv = this.getResources().obtainTypedArray(R.array.health_pkgs);
        int lensrv = arsrv.length();
        int[] img_id_pkgs = new int[lensrv];
        for(int k=0; k<lensrv;k++){
            img_id_pkgs[k] = arsrv.getResourceId(k,0);
        }
        arsrv.recycle();

        int i=0;
        for (String srv :srvsTxt){
            ListProviderHomeServices lst = new ListProviderHomeServices(img_id_pkgs[i], srvsTxt[i]);
            arrayListSrvs.add(lst);
            i++;
        }
        adapterSrvs = new RecyclerAdapterHomeServices(arrayListSrvs);
        recyclerVwSrvs.setAdapter(adapterSrvs);


        // Recycler View for Blood test
        recyclerVwHmBT =(RecyclerView)v.findViewById(R.id.home_bt_rcyclrView);
        layoutManagerHmBT = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerVwHmBT.setLayoutManager(layoutManagerHmBT);
        recyclerVwHmBT.setHasFixedSize(true);
        btTitle = getResources().getStringArray(R.array.home_bt_title);
        btPrice = getResources().getStringArray(R.array.home_bt_price);
        TypedArray arbt = this.getResources().obtainTypedArray(R.array.home_bt_imgids);
        int lenbt = arbt.length();
        int[] img_id_bt = new int[lenbt];
        for(int k=0; k<lenbt;k++){
            img_id_bt[k]= arbt.getResourceId(k,0);
        }
        arbt.recycle();

        int n=0;
        for(String NAME: btTitle){
            ListProviderHomeBT l = new ListProviderHomeBT(img_id_bt[n],btTitle[n],btPrice[n]);
            arrayListHmBT.add(l);
            n++;
        }
        adapterHmBt = new RecyclerAdapterHomeBT(arrayListHmBT);
        recyclerVwHmBT.setAdapter(adapterHmBt);

        recyclerVwHmBT.addOnItemTouchListener(
                new RecyclerItemClickListener(v.getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void OnItemClick(View v, int position) {
                        Intent i = new Intent(v.getContext(), BloodTestDetails.class);
                        i.putExtra("Calling_From",0);
                        i.putExtra("Position",position);
                        startActivity(i);
                    }
                })
        );

        // Recycler View for Dyagnostics Test

        recyclerVwHmDT = (RecyclerView)v.findViewById(R.id.home_dt_rcyclrView);
        layoutManagerHmDT = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerVwHmDT.setLayoutManager(layoutManagerHmDT);
        recyclerVwHmDT.setHasFixedSize(true);

        dtTitle= getResources().getStringArray(R.array.home_dt_title);
        TypedArray ar = this.getResources().obtainTypedArray(R.array.home_dt_imgids);
        int len = ar.length();
        int[] hm_dt_imgids = new int[len];
        for(int k =0; k<len; k++){
            hm_dt_imgids[k]= ar.getResourceId(k,0);
        }
        ar.recycle();

        int m=0;
        for(String TITLE:dtTitle){
            ListProviderHomeDT ldt = new ListProviderHomeDT(hm_dt_imgids[m],dtTitle[m]);
            arrayListHmDT.add(ldt);
            m++;
        }
        adapterHmDT = new RecyclerAdapterHomeDT(arrayListHmDT);
        recyclerVwHmDT.setAdapter(adapterHmDT);
        return v;

    }

}
