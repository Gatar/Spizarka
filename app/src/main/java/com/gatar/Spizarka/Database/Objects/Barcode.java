package com.gatar.Spizarka.Database.Objects;

import java.io.Serializable;

/**
 * Barcode object containing barcode value and bind with it item id.
 */
public class Barcode implements Serializable {

    private String barcode;
    private Long itemId;

    public Barcode() {
    }

    public Barcode(String barcode, Long itemId) {
        this.barcode = barcode;
        this.itemId = itemId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Craete {@link BarcodeDTO} object for saned it to remote database
     * @return Barcode object as BarcodeDTO
     */
    public BarcodeDTO toBarcodeDTO(){
        BarcodeDTO barcodeDTO = new BarcodeDTO();
        barcodeDTO.setBarcodeValue(barcode);
        barcodeDTO.setIdItemAndroid(itemId);
        return barcodeDTO;
    }
}
