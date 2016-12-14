package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Objects.Item;

/**
 * Set data view parameters for increase quantity.
 */
public class IncreaseQuantityEdit extends MyDataView {
    public IncreaseQuantityEdit(View view) {
        super(view);
    }

    @Override
    public void setDataView() {
        titleDescription.setTextColor(Color.BLACK);
        categoryDescription.setTextColor(Color.BLACK);
        quantityDescription.setTextColor(Color.BLACK);
        quantityModificationDescription.setTextColor(Color.RED);
        quantityMinimumDescription.setTextColor(Color.BLACK);
        descriptionDescription.setTextColor(Color.BLACK);

        titleText.setEnabled(false);
        categoryText.setEnabled(false);
        quantityText.setEnabled(false);
        quantityModificationText.setEnabled(true);
        quantityMinimumText.setEnabled(false);
        descriptionText.setEnabled(false);

        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,Categories.values());
        categoryText.setAdapter(adapter);
    }

    /**
     * {@inheritDoc}
     * @return Item with all data or NULL when there was any incorrectness ex. new quantity below zero.
     */
    public Item getDataView(){
        Item item = new Item();
        if(isEditTextNotEmpty()) {
            int newQuantity = Integer.parseInt(quantityText.getText().toString()) +
                    Integer.parseInt(quantityModificationText.getText().toString());

            if(isQuantityNegativeValue(newQuantity)) return null;

            item.setTitle(titleText.getText().toString());
            item.setCategory(Categories.getEnumByCategoryName(categoryText.getSelectedItem().toString()));
            item.setQuantity(newQuantity);
            item.setMinimumQuantity(Integer.parseInt(quantityMinimumText.getText().toString()));
            item.setDescription(descriptionText.getText().toString());

            return item;

        } else {
            return null;
        }
    }
}

