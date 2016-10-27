package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-27.
 */
abstract public class TextFieldConnector {
    View view;

    TextView titleDescription;
    EditText titleText;

    TextView categoryDescription;
    Spinner categoryText;

    TextView quantityDescription;
    EditText quantityText;

    TextView quantityModificationDescription;
    EditText quantityModificationText;

    TextView quantityMinimumDescription;
    EditText quantityMinimumText;

    TextView descriptionDescription;
    EditText descriptionText;

    public TextFieldConnector(View view){
        this.view = view;

        titleDescription = (TextView)view.findViewById(R.id.textChangeTitleDescription);
        titleText = (EditText) view.findViewById(R.id.textChangeTitle);

        categoryDescription = (TextView)view.findViewById(R.id.textChangeCategoryDescription);
        categoryText = (Spinner) view.findViewById(R.id.textChangeCategory);

        quantityDescription = (TextView)view.findViewById(R.id.textChangeQuantityDescription);
        quantityText = (EditText) view.findViewById(R.id.textChangeQuantity);

        quantityModificationDescription = (TextView)view.findViewById(R.id.textChangeQuantityModificationDescription);
        quantityModificationText = (EditText) view.findViewById(R.id.textChangeQuantityModification);

        quantityMinimumDescription = (TextView)view.findViewById(R.id.textChangeQuantityMinimumDescription);
        quantityMinimumText = (EditText) view.findViewById(R.id.textChangeQuantityMinimum);

        descriptionDescription = (TextView)view.findViewById(R.id.textChangeDescriptionDescription);
        descriptionText = (EditText) view.findViewById(R.id.textChangeDescription);
    }

    public void fillDataView(Item item) {
        titleText.setText(item.getTitle());
        quantityText.setText(String.format("%d", item.getQuantity()));
        quantityModificationText.setText("0");
        quantityMinimumText.setText(String.format("%d", item.getMinimumQuantity()));
        descriptionText.setText(item.getDescription());

        Categories actualCategory = item.getCategory();
        int categoryNumber = Categories.getOrdinalNoByCategoryName(actualCategory.toString());
        categoryText.setSelection(categoryNumber);
    }
}
