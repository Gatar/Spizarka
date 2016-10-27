package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;

/**
 * Created by Gatar on 2016-10-27.
 */
public class AddNewEdit extends TextFieldConnector implements DataViewStrategy {

    public AddNewEdit(View view) {
        super(view);
    }

    @Override
    public void setDataView() {
        titleDescription.setTextColor(Color.RED);
        categoryDescription.setTextColor(Color.RED);
        quantityDescription.setTextColor(Color.BLACK);
        quantityModificationDescription.setTextColor(Color.RED);
        quantityMinimumDescription.setTextColor(Color.RED);
        descriptionDescription.setTextColor(Color.RED);

        titleText.setEnabled(true);
        categoryText.setEnabled(true);
        quantityText.setEnabled(false);
        quantityModificationText.setEnabled(true);
        quantityMinimumText.setEnabled(true);
        descriptionText.setEnabled(true);

        quantityModificationDescription.setText(R.string.add);

        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,Categories.values());
        categoryText.setAdapter(adapter);
    }

    @Override
    public void fillDataView(Item item) {
        super.fillDataView(item);
    }
}
