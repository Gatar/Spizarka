package com.gatar.Spizarka.Operations.Change;

import android.view.View;
import android.widget.Toast;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.Categories;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.ItemFiller.View.ChangeDataViewFragment;

/**
 * Providing access to get data from fields in {@link ChangeDataViewFragment} layout.
 * Contains only one public method @see getDataView which return Item object.
 *
 */
public class ChangeDataViewGet extends ChangeDataView {
    /**
     * Set up the view from fragment and option of work.
     * @param view View from inflated fragment {@link ChangeDataViewFragment}
     * @param option Option in which program is
     */
    public ChangeDataViewGet(View view, ItemFillerOptions option) {
        super(view, option);
    }

    /**
     * Get data from {@link ChangeDataViewFragment} as Item object.
     * Quantity of item are calculated based on what type of operation ItemFillerOptions was chosen.
     * When data are incorrect shows Toast with information (by @see isDataViewCorrect)
     *
     * @return Item object filled with data from view (EditText fields) is data are correct, NULL when input data are incorrect.
     */
    public Item getDataView(){
        Item item;
            if(isEditTextNotEmpty()) {
                int newQuantity = 0;

                switch (option) {
                    case AddProduct:
                        newQuantity = Integer.parseInt(quantityModificationText.getText().toString());
                        break;

                    case DecreaseQuantity:
                        newQuantity = Integer.parseInt(quantityText.getText().toString()) -
                                Integer.parseInt(quantityModificationText.getText().toString());
                        break;

                    case IncreaseQuantity:
                        newQuantity = Integer.parseInt(quantityText.getText().toString()) +
                                Integer.parseInt(quantityModificationText.getText().toString());
                        break;

                    case UpdateItem:
                        newQuantity = Integer.parseInt(quantityText.getText().toString());
                        break;
                }

                if(isQuantityNegativeValue(newQuantity)) return null;

                item = new Item(
                        titleText.getText().toString(),
                        Categories.getEnumByCategoryName(categoryText.getSelectedItem().toString()),
                        newQuantity,
                        Integer.parseInt(quantityMinimumText.getText().toString()),
                        descriptionText.getText().toString()
                );
                return item;

            } else {
                return null;
            }
    }

    private boolean isEditTextNotEmpty(){

        if (titleText.getText().toString().matches("")){
            Toast.makeText(view.getContext(), R.string.missing_item_title,Toast.LENGTH_SHORT).show();
        }else if(quantityModificationText.getText().toString().matches("")) {
            Toast.makeText(view.getContext(), R.string.missing_quantity_change, Toast.LENGTH_SHORT).show();
        }else if(quantityMinimumText.getText().toString().matches("")){
            Toast.makeText(view.getContext(), R.string.missing_minimum_quantity,Toast.LENGTH_SHORT).show();
        }else if(quantityText.getText().toString().matches("")){
            Toast.makeText(view.getContext(), R.string.missing_quantity,Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

    private boolean isQuantityNegativeValue(int quantity){
        if(quantity < 0){
            Toast.makeText(view.getContext(), R.string.quantity_under_zero,Toast.LENGTH_LONG).show();
            return true;
        } else return false;
    }

}
