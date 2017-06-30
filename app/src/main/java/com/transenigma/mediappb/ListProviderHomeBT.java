package com.transenigma.mediappb;

/**
 * Created by Kishore on 6/14/2017.
 */

public class ListProviderHomeBT {
    private int img_id;
    private String title,price;
    public ListProviderHomeBT(int img_id, String title, String price){
        this.setImg_id(img_id);
        this.setPrice(price);
        this.setTitle(title);
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
