package com.gatar.Spizarka.Database;

import java.io.Serializable;

/**
 * Created by Gatar on 2016-08-09.
 */
public class Item implements Serializable {


    private String barcode;
    private String title;
    private Categories category;
    private int quantity;
    private int minimumQuantity;
    private String description;

    public Item (){
        this.barcode = "";
        this.title = "";
        this.category = Categories.Other;
        this.quantity = 0;
        this.minimumQuantity = 0;
        this.description = "";
    }

    public Item(String barcode, String nazwa, Categories category, int quantity, int minimumQuantity, String description) {
        this.barcode = barcode;
        this.title = nazwa;
        this.category = category;
        this.quantity = (quantity < 0) ? 0 : quantity;
        this.minimumQuantity = (minimumQuantity < 0)? 0 : minimumQuantity;
        this.description = description;
    }

    public Item(String nazwa, Categories category, int quantity, int minimumQuantity, String description) {
        this.barcode = "";
        this.title = nazwa;
        this.category = category;
        this.quantity = (quantity < 0) ? 0 : quantity;
        this.minimumQuantity = (minimumQuantity < 0)? 0 : minimumQuantity;
        this.description = description;
    }

    /**
     * Relict, always null variable. Thera are no option to get barcodes set to Title.
     * @return null
     */
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Get title of item. Title is specific, key-unique value for each item.
     * @return title of item.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String nazwa) {
        this.title = nazwa;
    }

    /**
     * Get item category based of {@link Categories}
     * @return item category.
     */
    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    /**
     * Get quantity of item which are in depot.
     * @return number of items on stock.
     */
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = (quantity < 0) ? 0 : quantity;
    }

    /**
     * Get minimum quantity of item, which are used to create Shopping List
     * @return number of minimum items which should be on stock
     */
    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = (minimumQuantity < 0)? 0 : minimumQuantity;
    }

    /**
     * Get String with description bind to item.
     * @return item description.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
