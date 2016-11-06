package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.view.View;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
/**
 * Data view factory, types described by enum {@link ItemFillerOptions}.
 */
public class DataViewFactory {

    /**
     * Set data view type in {@link com.gatar.Spizarka.ItemFiller.View.DataViewFragment} on option base.
     * @param options {@link ItemFillerOptions} value for data view set
     * @param view {@link View} specified for {@link com.gatar.Spizarka.ItemFiller.View.DataViewFragment}
     * @return instance of {@link MyDataView} object
     */
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
