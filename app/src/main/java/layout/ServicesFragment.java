package layout;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.transenigma.mediappb.ListProviderHomeBT;
import com.transenigma.mediappb.ListProviderHomeBnr;
import com.transenigma.mediappb.ListProviderHomeDT;
import com.transenigma.mediappb.ListProviderHomeServices;
import com.transenigma.mediappb.R;
import com.transenigma.mediappb.RecyclerAdapterHomeBT;
import com.transenigma.mediappb.RecyclerAdapterHomeBnr;
import com.transenigma.mediappb.RecyclerAdapterHomeDT;
import com.transenigma.mediappb.RecyclerAdapterHomeServices;

import java.util.ArrayList;


public class ServicesFragment extends Fragment {

    RecyclerView recyclerVwHmBT, recyclerVwSrvs, recyclerVwHmDT;
    RecyclerView.Adapter adapterHmBt, adapterSrvs, adapterHmDT;
    RecyclerView.LayoutManager layoutManagerHmBT, layoutManagerSrvs, layoutManagerHmDT;
    ArrayList<ListProviderHomeBnr> arrayList = new ArrayList<ListProviderHomeBnr>();
    ArrayList<ListProviderHomeServices> arrayListSrvs = new ArrayList<ListProviderHomeServices>();
    ArrayList<ListProviderHomeBT> arrayListHmBT = new ArrayList<ListProviderHomeBT>();
    ArrayList<ListProviderHomeDT> arrayListHmDT = new ArrayList<ListProviderHomeDT>();







    public ServicesFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.my_profile_toolbar);


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





        // recycler View for Health packages



        return v;
    }
}
