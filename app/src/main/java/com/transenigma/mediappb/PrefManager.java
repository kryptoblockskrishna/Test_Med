package com.transenigma.mediappb;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kishore on 6/29/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context c;

    int PRIVATE_MODE = 0;
    private static final String pref_name = "Trans Health Welcome";
    private static final String is_first_time_launch = "IsFirstTimeLaunch";

    public PrefManager(Context context){
        this.c=context;
        pref = c.getSharedPreferences(pref_name,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(is_first_time_launch,isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(is_first_time_launch,true);
    }
}
