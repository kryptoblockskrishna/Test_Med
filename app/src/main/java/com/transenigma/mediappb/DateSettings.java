package com.transenigma.mediappb;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by Kishore on 6/24/2017.
 */

public class DateSettings implements DatePickerDialog.OnDateSetListener{
    Context context;
    TextView textView;
    public DateSettings(Context context){
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
