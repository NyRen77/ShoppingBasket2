package com.example.android.shoppingbasket;

import java.io.Serializable;

/**
 * Created by Renato on 2017. 10. 08..
 */

public class Item implements Serializable {
    private String name;
    private String barcode;
    private String category;
    private float price;
    private String shop;


    public void Item(String name, String barcode, String category, float price, String shop){
        this.name=name;
        this.barcode=barcode;
        this.category=category;
        this.price=price;
        this.shop=shop;
    }

    public void Item(String name){
        this.name=name;
        this.barcode="";
        this.category="";
        this.price=0;
        this.shop="";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

}
