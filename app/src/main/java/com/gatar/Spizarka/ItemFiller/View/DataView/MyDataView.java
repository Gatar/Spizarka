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
 * Extendable class for all data view set objects. Contains references for text views, edit texts and spinner in {@link com.gatar.Spizarka.ItemFiller.View.DataViewFragment}
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

    /**
     * Fill all edit text fields in {@link com.gatar.Spizarka.ItemFiller.View.DataViewFragment} with item values.
     * @param item item for set on view
     */
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

    /**
     * Check does all edit text fields contains value. Shows Toast messages when not.
     * @return true if everything is correct, false when there are some problems.
     */
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

    /**
     * Check does quantity value is over zero. Show Toast message if not.
     * @param quantity value of quantity
     * @return true - value below zero, false - OK
     */
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

    /**
     * Set text view, edit text and spinner fields with correct to inherited type of object parameters:
     * <ol>
     *     <li>enable</li>
     *     <li>color</li>
     *     <li>Spinner adapter</li>
     * </ol>
     */
    abstract public void setDataView();

    /**
     * Get all data from edit text fields and parse to the Item object.
     * @return item with input values.
     */
    abstract public Item getDataView();
}
