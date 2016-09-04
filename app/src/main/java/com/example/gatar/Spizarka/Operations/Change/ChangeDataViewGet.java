package com.example.gatar.Spizarka.Operations.Change;

import android.view.View;
import android.widget.Toast;

import com.example.gatar.Spizarka.Activities.ChangeOptions;
import com.example.gatar.Spizarka.Database.Item;
import com.example.gatar.Spizarka.Database.Categories;

/**
 * Providing access to get data from fields in {@link com.example.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment} layout.
 * Contains only one public method @see getDataView which return Item object.
 *
 */
public class ChangeDataViewGet extends ChangeDataView {
    /**
     * Set up the view from fragment and option of work.
     * @param view View from inflated fragment {@link com.example.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment}
     * @param option Option in which program is
     */
    public ChangeDataViewGet(View view, ChangeOptions option) {
        super(view, option);
    }

    /**
     * Get data from {@link com.example.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment} as Item object.
     * Quantity of item are calculated based on what type of operation ChangeOptions was chosen.
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

                    case Actualize:
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
            Toast.makeText(view.getContext(),"Podaj nazwę produktu",Toast.LENGTH_SHORT).show();
        }else if(quantityModificationText.getText().toString().matches("")) {
            Toast.makeText(view.getContext(), "Podaj zmianę ilości produktów", Toast.LENGTH_SHORT).show();
        }else if(quantityMinimumText.getText().toString().matches("")){
            Toast.makeText(view.getContext(),"Podaj stan minimalny",Toast.LENGTH_SHORT).show();
        }else if(quantityText.getText().toString().matches("")){
            Toast.makeText(view.getContext(),"Podaj ilość produktów",Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

    private boolean isQuantityNegativeValue(int quantity){
        if(quantity < 0){
            Toast.makeText(view.getContext(),"Podana ilość spowoduje spadek \n stanu towaru poniżej 0!",Toast.LENGTH_LONG).show();
            return true;
        } else return false;
    }

}
