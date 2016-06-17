package com.discount_ascii_warehouse.app.data.ascii;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariobraga on 6/13/16.
 */
public class Ascii implements Serializable{

    private String type;
    private String id;
    private int size;
    private double price;
    private String face;
    private int stock;
    private List<String> tags = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String getTagsTitle() {

        String tagTitle = "";

        for(int i = 0 ; i < getTags().size(); i++)
        {
            String tag = getTags().get(i);
            tagTitle += tag;
            if(i < getTags().size() - 1 )
            {
                tagTitle += " / ";
            }
        }

        return tagTitle;
    }

    public String convertPriceToDollarText()
    {
        double priceDecimal = price  / 100;

        DecimalFormat df   = new DecimalFormat("0.00");
        String priceString ="$" + df.format(priceDecimal);

        return priceString;

    }
}


