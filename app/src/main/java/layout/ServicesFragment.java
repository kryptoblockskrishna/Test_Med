package layout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transenigma.mediappb.Login;
import com.transenigma.mediappb.MyProfile;
import com.transenigma.mediappb.PrefManager;
import com.transenigma.mediappb.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ServicesFragment extends Fragment {

    public ServicesFragment() {
        // Required empty public constructor
    }

    SharedPreferences usrDetails ;

    String info_SP, email, contact;
    String pin_SP;

    RelativeLayout usrDetailNoti;

    TextView FillDetails, notifEndLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.my_profile_toolbar);

        FillDetails = (TextView) v.findViewById(R.id.udn_content);
        usrDetailNoti = (RelativeLayout) v.findViewById(R.id.user_details_notification);
        notifEndLine = (TextView) v.findViewById(R.id.user_details_noti_endline);

        // ======================================== Shared Preferences =========================================

        //usrDetails = this.getActivity().getSharedPreferences("USER", MODE_PRIVATE) ;
        usrDetails = PreferenceManager.getDefaultSharedPreferences(v.getContext());

        if ( usrDetails.contains(getString(R.string.CONTACT_TAG)) && usrDetails.contains(getString(R.string.EMAIL_TAG)) ){
            email = usrDetails.getString("EMAIL", "NULL");
            contact = usrDetails.getString("CONTACT", "NULL");
            Toast.makeText(v.getContext() ,"Toast From ServiceFragment " + email + " " + contact,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(v.getContext() ,"Keys Dosen't Exist",Toast.LENGTH_LONG).show();
        }

        info_SP = usrDetails.getString("FIRST_NAME" , "NOT_INITIALIZED");
        pin_SP = usrDetails.getString("PIN" , "NOT_INITIALIZED");

        // -----------------------------------------------------------------------------------------------------

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if( info_SP.equals("") || info_SP.equals("NOT_INITIALIZED" ) || info_SP.equals("NULL" ) ){
            FillDetails.setText("Please Fill Your Personal Information");

            usrDetailNoti.setVisibility(v.VISIBLE);
            notifEndLine.setVisibility(v.VISIBLE);

            FillDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toMyProfile = new Intent(getActivity(), MyProfile.class);
                    startActivity(toMyProfile);
                }
            });
        }
        else if( pin_SP.equals("") || pin_SP.equals("NOT_INITIALIZED") || pin_SP.equals("NULL" )){
            FillDetails.setText("Please Fill Your Current Address");

            usrDetailNoti.setVisibility(v.VISIBLE);
            notifEndLine.setVisibility(v.VISIBLE);

            FillDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toMyProfile = new Intent(getActivity(), MyProfile.class);
                    startActivity(toMyProfile);
                }
            });
        }else{
            usrDetailNoti.setVisibility(v.GONE);
            notifEndLine.setVisibility(v.GONE);
        }

        return v;
    }
}
