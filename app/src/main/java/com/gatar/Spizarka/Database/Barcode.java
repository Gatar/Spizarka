package com.gatar.Spizarka.Database;

import java.io.Serializable;

/**
 * Barcode object containing barcode value and bind with it item id.
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
