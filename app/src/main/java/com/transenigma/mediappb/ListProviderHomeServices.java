package com.transenigma.mediappb;

/**
 * Created by Kishore on 6/14/2017.
 */

public class ListProviderHomeServices {
    private int img_id;
    private String string;

    public ListProviderHomeServices(int img_id, String string){
        this.setImg_id(img_id);
        this.setString(string);
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
