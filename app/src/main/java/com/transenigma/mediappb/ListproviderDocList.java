package com.transenigma.mediappb;

/**
 * Created by Kishore on 6/13/2017.
 */

public class ListproviderDocList {
    private int lpdl_doc_pic;
    private String lpdl_doc_name;
    private String lpdl_degree;
    private String lpdl_hospital;
    private String lpdl_availability;

    public ListproviderDocList(int lpdl_doc_pic, String lpdl_doc_name, String lpdl_degree, String lpdl_hospital, String lpdl_availability){
        this.setLpdl_doc_pic(lpdl_doc_pic);
        this.setLpdl_doc_name(lpdl_doc_name);
        this.setLpdl_degree(lpdl_degree);
        this.setLpdl_hospital(lpdl_hospital);
        this.setLpdl_availability(lpdl_availability);
    }

    public int getLpdl_doc_pic() {
        return lpdl_doc_pic;
    }

    public void setLpdl_doc_pic(int lpdl_doc_pic) {
        this.lpdl_doc_pic = lpdl_doc_pic;
    }

    public String getLpdl_doc_name() {
        return lpdl_doc_name;
    }

    public void setLpdl_doc_name(String lpdl_doc_name) {
        this.lpdl_doc_name = lpdl_doc_name;
    }

    public String getLpdl_degree() {
        return lpdl_degree;
    }

    public void setLpdl_degree(String lpdl_degree) {
        this.lpdl_degree = lpdl_degree;
    }

    public String getLpdl_hospital() {
        return lpdl_hospital;
    }

    public void setLpdl_hospital(String lpdl_hospital) {
        this.lpdl_hospital = lpdl_hospital;
    }

    public String getLpdl_availability() {
        return lpdl_availability;
    }

    public void setLpdl_availability(String lpdl_availability) {
        this.lpdl_availability = lpdl_availability;
    }





}
