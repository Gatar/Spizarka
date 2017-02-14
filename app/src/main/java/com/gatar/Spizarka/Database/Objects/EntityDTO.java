package com.gatar.Spizarka.Database.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * DTO Object for single item with all barcodes.
 */
public class EntityDTO implements Serializable {

    /**
     * Item id number assigned in internal database in phone of user.
     */
    private Long idItemAndroid;
    private String title;
    private String category;
    private Integer quantity;
    private Integer minimumQuantity;
    private String description;
    private Long databaseVersion;
    private ArrayList<String> barcodes;

    public Long getIdItemAndroid() {
        return idItemAndroid;
    }

    public void setIdItemAndroid(Long idItemAndroid) {
        this.idItemAndroid = idItemAndroid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(Long databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public ArrayList<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(ArrayList<String> barcodes) {
        this.barcodes = barcodes;
    }

    public EntityDTO() {
    }


}
