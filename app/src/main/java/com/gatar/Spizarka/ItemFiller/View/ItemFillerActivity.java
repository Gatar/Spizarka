package com.gatar.Spizarka.ItemFiller.View;

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

import com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.Depot.View.DepotActivity;
import com.gatar.Spizarka.ItemFiller.ItemFillerMVP;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.ItemFiller.View.Buttons.ButtonViewStrategy;
import com.gatar.Spizarka.ItemFiller.View.DataView.DataViewStrategy;
import com.gatar.Spizarka.Main.View.MainActivity;
import com.gatar.Spizarka.Operations.Change.ChangeDataViewGet;
import com.gatar.Spizarka.BarcodeScanner.View.ChangeDialogNewBarcode;
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
 * Operations for data as in {@link ItemFillerOptions}
 */

public class ItemFillerActivity extends AppCompatActivity implements ItemFillerMVP.RequiredViewOperations,
        ChangeButtonAddNewItemFragment.ChangeAddButtonFragmentListener,
        ChangeButtonDecreaseQuantityFragment.ChangeRemoveButtonFragmentListener,
        ChangeButtonIncreaseQuantityFragment.ChangeQuantityChangeButtonFragmentListener,
        ChangeButtonUpdateItemFragment.ChangeButtonUpdateFragmentListener,
        ChangeDataViewFragment.ChangeDataViewFragmentListener{

    //TODO Dokończyć implementacje MVP tutaj

    private ItemFillerMVP.ModelOperations mPresenter;

    private DataViewStrategy dataViewStrategy;
    private ButtonViewStrategy buttonViewStrategy;

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentDataViewFragment = null;
    private Fragment currentButtonFragment = null;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";
    private final String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    private ItemFillerOptions option;
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
     * Get from shared preferences chosen option {@link ItemFillerOptions} of work and set right data view and buttons fragments for it.
     */

    public void proceedChosenOption(){
        option = ItemFillerOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION, "Error"));
        switch(option){
            case AddProduct:
                setDataView();
                setAddButton();
                break;

            case AddBarcodeToProduct:
                preferencesEditor.putString(DEPOT_ACTIVITY_OPTION, DepotOptions.AddBarcodeToExistingItemView.toString());
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
        option = ItemFillerOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION, "Error"));
        barcode = preferences.getString(EXTRA_BARCODE,null);
    }

    /**
     * Check correction of input data and correct settings if there is something wrong.
     * For example if chosen option is "AddProduct" and scanned barcode exists in database, method automatically change option to "Increase Quantity".
     */
    private void checkIsOptionCorrect(){
        if(managerDAO.isContainBarcode(barcode) && option == ItemFillerOptions.AddProduct){
            Toast.makeText(this, R.string.product_found,Toast.LENGTH_LONG).show();
            preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.IncreaseQuantity.toString());
            preferencesEditor.commit();
            option = ItemFillerOptions.IncreaseQuantity;
        }

        if(!managerDAO.isContainBarcode(barcode) && (option == ItemFillerOptions.IncreaseQuantity || option == ItemFillerOptions.DecreaseQuantity)){
            preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.AddProduct.toString());
            preferencesEditor.commit();
            option = ItemFillerOptions.AddProduct;
        }
    }

    /**
     * Check does option is "AddProduct", which means that we have new barcode to handle.
     * @return true - AddProduct option is chosen, false - other option is chosen
     */
    private boolean isBarcodeNew(){
        return option == ItemFillerOptions.AddProduct;
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

    @Override
    public void setDataView(DataViewStrategy dataViewStrategy, Item item) {
        this.dataViewStrategy = dataViewStrategy;
        dataViewStrategy.setDataView();
        dataViewStrategy.fillDataView(item);
    }

    @Override
    public void setButtonView(ButtonViewStrategy buttonStrategy) {
        this.buttonViewStrategy = buttonStrategy;
        buttonViewStrategy.setButtonView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }
}
