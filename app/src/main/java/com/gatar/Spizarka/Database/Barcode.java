package com.gatar.Spizarka.Database;

import java.io.Serializable;

/**
 * Created by Gatar on 2016-11-02.
 */
public class Barcode implements Serializable {

    private String barcode;
    private Integer itemId;

    public Barcode() {
    }

    public Barcode(String barcode, Integer itemId) {
        this.barcode = barcode;
        this.itemId = itemId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
