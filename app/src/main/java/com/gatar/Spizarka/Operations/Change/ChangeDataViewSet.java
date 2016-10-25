package com.gatar.Spizarka.Operations.Change;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gatar.Spizarka.Activities.ChangeOptions;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Database.Categories;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment;

/**
 * Providing access to set data and modify view of fields in {@link ChangeDataViewFragment} layout.
 * Contains only one public method @see setDataView which set everything on base of ChangeOption received in constructor.
 * Data are taken directly from database, based on title and barcode from SharedPreferences.
 */
public class ChangeDataViewSet extends ChangeDataView {
    private ManagerDAO managerDAO;
    private String barcode;
    private String title;

    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";
    private final String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    /**
     * Set up the view from fragment and option of work.
     * @param view View from inflated fragment {@link ChangeDataViewFragment}
     * @param option Option in which program is
     */
    public ChangeDataViewSet(View view, ChangeOptions option) {
        super(view, option);
        managerDAO = new ManagerDAO(view.getContext());
    }

    /**
     * Set up permission to edit or not each EditText field in view and if its necessary fill it with data taken from database.
     * Working mode is set on base of {@link ChangeOptions} set in class constructor.
     */
    public void setDataView(){
        barcode = preferences.getString(EXTRA_BARCODE,null);
        title = preferences.getString(EXTRA_TITLE,null);

        switch(option){
            case AddProduct:
                setViewEditFull();
                break;
            case DecreaseQuantity:
                setViewEditQuantity();
                setItemData(managerDAO.getSingleItem(managerDAO.getTitle(barcode)));
                break;
            case IncreaseQuantity:
                setViewEditQuantity();
                setItemData(managerDAO.getSingleItem(managerDAO.getTitle(barcode)));
                break;
            case UpdateItem:
                setViewUpdate();
                setItemData(managerDAO.getSingleItem(title));
                break;
        }
    }

    private void setViewEditFull(){
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

    private void setViewUpdate(){
        titleDescription.setTextColor(Color.BLACK);
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

    private void setViewEditQuantity(){
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

        if(option == ChangeOptions.IncreaseQuantity){
            quantityModificationDescription.setText(R.string.add);
        } else {
            quantityModificationDescription.setText(R.string.subtract);
        }

        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,Categories.values());
        categoryText.setAdapter(adapter);
    }

    private void setItemData(Item item) {
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
