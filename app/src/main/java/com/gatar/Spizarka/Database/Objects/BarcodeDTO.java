package com.gatar.Spizarka.Database.Objects;
import java.io.Serializable;

/**
 * Data Transfer Object used to receive/send one barcodeValue from/to phone.
 */
public class BarcodeDTO implements Serializable {

        private String barcodeValue;

        /**
         * Item id number assigned in internal database in phone of user.
         */
        private Long idItemAndroid;

        public String getBarcodeValue() {
            return barcodeValue;
        }

        public void setBarcodeValue(String barcodeValue) {
            this.barcodeValue = barcodeValue;
        }

        public Long getIdItemAndroid() {
            return idItemAndroid;
        }

        public void setIdItemAndroid(Long idItemAndroid) {
            this.idItemAndroid = idItemAndroid;
        }

        public BarcodeDTO() {
        }

    /**
     * Get {@link Barcode} object from DTO object
     * @return Barcode object from DTO file
     */
        public Barcode toBarcode(){
            Barcode barcode = new Barcode();
            barcode.setBarcode(this.barcodeValue);
            barcode.setItemId(this.idItemAndroid);
            return barcode;
        }
}
