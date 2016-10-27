package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-27.
 */
public class QuantityEdit extends TextFieldConnector implements DataViewStrategy{
    public QuantityEdit(View view) {
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

    @Override
    public void fillDataView(Item item) {
        super.fillDataView(item);
    }
}

