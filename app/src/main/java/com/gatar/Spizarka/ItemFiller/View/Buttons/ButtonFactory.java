package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-28.
 */
public class ButtonFactory {
    public static MyButton createButton(ItemFillerOptions options, View view){
        switch(options){
            case AddBarcodeToProduct:
            case AddProduct:
                return new AddNewMyButton(view);
            case DecreaseQuantity:
                return new DecreaseQuantityMyButton(view);
            case IncreaseQuantity:
                return new IncreaseQuantityMyButton(view);
            case UpdateItem:
                return new UpdateMyButton(view);
        }
        throw new IllegalArgumentException("Item Filler Option " + options + "is not recognized.");
    }
}
