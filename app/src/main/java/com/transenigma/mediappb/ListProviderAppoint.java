package com.transenigma.mediappb;

/**
 * Created by Kishore on 6/24/2017.
 */

public class ListProviderAppoint {
    private String hosp, dept, city, state;

    public ListProviderAppoint(String Hospital, String Department, String City, String State){
        this.setHosp(Hospital);
        this.setDept(Department);
        this.setCity(City);
        this.setState(State);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
