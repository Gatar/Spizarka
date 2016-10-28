package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;

/**
 * Created by Gatar on 2016-10-27.
 */
abstract public class MyDataView {
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

    public MyDataView(View view){
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

    protected boolean isEditTextNotEmpty(){

        if (titleText.getText().toString().matches("")){
            showToast(R.string.missing_item_title);
        }else if(quantityModificationText.getText().toString().matches("")) {
            showToast(R.string.missing_quantity_change);
        }else if(quantityMinimumText.getText().toString().matches("")){
            showToast(R.string.missing_minimum_quantity);
        }else if(quantityText.getText().toString().matches("")){
            showToast(R.string.missing_quantity);
        }else{
            return true;
        }
        return false;
    }

    protected boolean isQuantityNegativeValue(int quantity){
        if(quantity < 0){
            Toast.makeText(view.getContext(), R.string.quantity_under_zero,Toast.LENGTH_LONG).show();
            return true;
        } else return false;
    }

    private void showToast(int stringResource){
        String message = view.getResources().getString(stringResource);
        Toast.makeText(view.getContext(), message,Toast.LENGTH_SHORT).show();
    }

    abstract public void setDataView();

    abstract public Item getDataView();
}
