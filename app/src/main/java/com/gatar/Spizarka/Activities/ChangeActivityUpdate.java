package com.gatar.Spizarka.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Fragments.Change.ChangeButtonUpdateItemFragment;
import com.gatar.Spizarka.Fragments.Change.ChangeButtonAddNewItemFragment;
import com.gatar.Spizarka.Fragments.Change.ChangeButtonDecreaseQuantityFragment;
import com.gatar.Spizarka.Fragments.Change.ChangeButtonIncreaseQuantityFragment;
import com.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment;
import com.gatar.Spizarka.Operations.Change.ChangeDataViewGet;
import com.gatar.Spizarka.Operations.Change.ChangeDialogNewBarcode;
import com.example.gatar.Spizarka.R;

/**
 * Activity defining view for change data about product.
 *
 * Portrait view fixed.
 * Contains two fragments in view:
 * 1. for show/input data for single item {@link ChangeDataViewFragment}
 * 2. button view with operations:
 * {@link ChangeButtonDecreaseQuantityFragment}
 * {@link ChangeButtonIncreaseQuantityFragment}
 * {@link ChangeButtonUpdateItemFragment}
 * {@link ChangeButtonAddNewItemFragment}
 *
 *
 * Operations for data as in {@link ChangeOptions}
 */

public class ChangeActivityUpdate extends AppCompatActivity implements
        ChangeButtonAddNewItemFragment.ChangeAddButtonFragmentListener,
        ChangeButtonDecreaseQuantityFragment.ChangeRemoveButtonFragmentListener,
        ChangeButtonIncreaseQuantityFragment.ChangeQuantityChangeButtonFragmentListener,
        ChangeButtonUpdateItemFragment.ChangeButtonUpdateFragmentListener,
        ChangeDataViewFragment.ChangeDataViewFragmentListener,
        ChangeDialogNewBarcode.ChangeDialogAddTypeListener {

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentDataViewFragment = null;
    private Fragment currentButtonFragment = null;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";
    private final String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    private ChangeOptions option;
    private ManagerDAO managerDAO;
    private ChangeDataViewGet viewOperations;
    private String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        managerDAO = new ManagerDAO(this);

        setPreferences();

        checkIsOptionCorrect();

        if(isBarcodeNew()) showNewBarcodeDialogBox();
            else proceedChosenOption();
    }

    @Override
    public void onBackPressed(){
        toMainMenu();
    }

    /**
     * Get from shared preferences chosen option {@link ChangeOptions} of work and set right data view and buttons fragments for it.
     */
    @Override
    public void proceedChosenOption(){
        option = ChangeOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION, "Error"));
        switch(option){
            case AddProduct:
                setDataView();
                setAddButton();
                break;

            case AddBarcodeToProduct:
                preferencesEditor.putString(DEPOT_ACTIVITY_OPTION,DepotOptions.AddBarcodeToExistingItemView.toString());
                preferencesEditor.commit();
                toDepotActivity();
                break;

            case DecreaseQuantity:
                setDataView();
                setDecreaseQuantityButton();
                break;

            case IncreaseQuantity:
                setDataView();
                setIncreaseQuantityButton();
                break;

            case UpdateItem:
                setDataView();
                //TODO zamienić na update
                //TODO Oddzielić prezentację od logiki
                setUpdateButton();
                break;

        }
    }

    @Override
    public void setDataView(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentDataViewFragment = new ChangeDataViewFragment();
        fragmentTransaction.replace(R.id.change_data_view_container,this.currentDataViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Set buttons with logic to add new item to database.
     */
    private void setAddButton(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentButtonFragment = new ChangeButtonAddNewItemFragment();
        fragmentTransaction.replace(R.id.change_button_container,this.currentButtonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setDecreaseQuantityButton(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentButtonFragment = new ChangeButtonDecreaseQuantityFragment();
        fragmentTransaction.replace(R.id.change_button_container,this.currentButtonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setIncreaseQuantityButton(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentButtonFragment = new ChangeButtonIncreaseQuantityFragment();
        fragmentTransaction.replace(R.id.change_button_container,this.currentButtonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setUpdateButton(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentButtonFragment = new ChangeButtonUpdateItemFragment();
        fragmentTransaction.replace(R.id.change_button_container,this.currentButtonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void addNewItem(boolean isItTheLast){
        View dataView = currentDataViewFragment.getView();
        viewOperations = new ChangeDataViewGet(dataView,option);

        Item item = viewOperations.getDataView();
        if(item != null) {
            managerDAO.addNewItem(item);
            managerDAO.addNewBarcode(barcode, item.getTitle());

            if (isItTheLast == true) toMainMenu();
            else toBarcodeScanner();
        }
    }


    @Override
    public void changeQuantityItem(boolean isItTheLast){
        View dataView = currentDataViewFragment.getView();
        viewOperations = new ChangeDataViewGet(dataView,option);

        Item item = viewOperations.getDataView();
        if(item != null) {
            managerDAO.updateItem(item);

            if (isItTheLast == true) toMainMenu();
            else toBarcodeScanner();
        }
    }


    @Override
    public void updateItem() {
        View dataView = currentDataViewFragment.getView();
        viewOperations = new ChangeDataViewGet(dataView,option);

        Item item = viewOperations.getDataView();
        if(item != null) {
            managerDAO.updateItem(item);

            preferencesEditor.putString(EXTRA_TITLE, item.getTitle());
            preferencesEditor.commit();

            finish();
        }
    }

    private void toDepotActivity(){
        Intent intent = new Intent(this,DepotActivity.class);
        startActivity(intent);
    }

    private void toMainMenu(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void toBarcodeScanner(){
        Intent intent = new Intent(this,BarcodeScannerActivity.class);
        startActivity(intent);
    }

    /**
     * Set shared preferences variables with key.
     * Get option of work and barcode from shared preferences.
     */
    private void setPreferences(){
        preferences = getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        option = ChangeOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION, "Error"));
        barcode = preferences.getString(EXTRA_BARCODE,null);
    }

    /**
     * Check correction of input data and correct settings if there is something wrong.
     * For example if chosen option is "AddProduct" and scanned barcode exists in database, method automatically change option to "Increase Quantity".
     */
    private void checkIsOptionCorrect(){
        if(managerDAO.isContainBarcode(barcode) && option == ChangeOptions.AddProduct){
            Toast.makeText(this, R.string.product_found,Toast.LENGTH_LONG).show();
            preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ChangeOptions.IncreaseQuantity.toString());
            preferencesEditor.commit();
            option = ChangeOptions.IncreaseQuantity;
        }

        if(!managerDAO.isContainBarcode(barcode) && (option == ChangeOptions.IncreaseQuantity || option == ChangeOptions.DecreaseQuantity)){
            preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ChangeOptions.AddProduct.toString());
            preferencesEditor.commit();
            option = ChangeOptions.AddProduct;
        }
    }

    /**
     * Check does option is "AddProduct", which means that we have new barcode to handle.
     * @return true - AddProduct option is chosen, false - other option is chosen
     */
    private boolean isBarcodeNew(){
        return option == ChangeOptions.AddProduct;
    }

    /**
     * Show dialog box with question how handle new barcode:
     * 1. Add it to existing item in database
     * 2. Add new item to database
     * {@link ChangeDialogNewBarcode}
     */
    private void showNewBarcodeDialogBox(){
        ChangeDialogNewBarcode checkAddType = new ChangeDialogNewBarcode();
        checkAddType.show(fragmentManager,"addType");
    }

}
