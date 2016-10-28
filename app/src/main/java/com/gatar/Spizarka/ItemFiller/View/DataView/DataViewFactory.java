package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.view.View;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-28.
 */
public class DataViewFactory {
    public static MyDataView createDataView(ItemFillerOptions options, View view){
        switch(options){
            case AddProduct:
                return new AddNewEdit(view);
            case AddBarcodeToProduct:
            case IncreaseQuantity:
                return new IncreaseQuantityEdit(view);
            case DecreaseQuantity:
                return new DecreaseQuantityEdit(view);
            case UpdateItem:
                return new UpdateEdit(view);
        }
        throw new IllegalArgumentException("Item Filler Option " + options + "is not recognized.");
    }
}
