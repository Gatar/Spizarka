package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Button factory, types described by enum {@link ItemFillerOptions}.
 */
public class ButtonFactory {

    /**
     * Set button type in {@link com.gatar.Spizarka.ItemFiller.View.ButtonFragment} on option base.
     * @param options {@link ItemFillerOptions} value for button set
     * @param view {@link View} specified for {@link com.gatar.Spizarka.ItemFiller.View.ButtonFragment}
     * @return instance of {@link MyButton} object
     */
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
