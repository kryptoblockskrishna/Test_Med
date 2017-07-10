package com.transenigma.mediappb;

import android.widget.ImageView;

/**
 * Created by Kishore on 6/24/2017.
 */

public class ListProviderAppoint {
    private String hosp, dept, name;
    private int pic_id;

    public ListProviderAppoint(int pic_id,String Name, String Hospital, String Department){
        this.setHosp(Hospital);
        this.setDept(Department);
        this.setName(Name);
        this.setPic_id(pic_id);
    }

    public String getHosp() {
        return hosp;
    }

    public void setHosp(String hosp) {
        this.hosp = hosp;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }
}
