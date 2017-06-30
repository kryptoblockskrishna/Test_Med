package com.transenigma.mediappb;

/**
 * Created by Kishore on 6/15/2017.
 */

public class ListProviderHomeDT {
    private int img_id;
    private String title;

    public ListProviderHomeDT(int img_id, String title){
        this.setTitle(title);
        this.setImg_id(img_id);
    }
    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
