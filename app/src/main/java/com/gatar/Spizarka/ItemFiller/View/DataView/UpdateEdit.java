package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;

/**
 * Set data view parameters for update item value
 */
public class UpdateEdit extends MyDataView {
    public UpdateEdit(View view) {
        super(view);
    }

    @Override
    public void setDataView() {
        titleDescription.setTextColor(Color.RED);
        categoryDescription.setTextColor(Color.RED);
        quantityDescription.setTextColor(Color.RED);
        quantityModificationDescription.setTextColor(Color.BLACK);
        quantityMinimumDescription.setTextColor(Color.RED);
        descriptionDescription.setTextColor(Color.RED);

        titleText.setEnabled(false);
        categoryText.setEnabled(true);
        quantityText.setEnabled(true);
        quantityModificationText.setEnabled(false);
        quantityMinimumText.setEnabled(true);
        descriptionText.setEnabled(true);

        quantityModificationDescription.setText(R.string.add);

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
            int newQuantity = Integer.parseInt(quantityText.getText().toString());

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
