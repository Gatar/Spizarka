package com.gatar.Spizarka.ItemFiller.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.View.DepotActivity;
import com.gatar.Spizarka.ItemFiller.ItemFillerMVP;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.ItemFiller.ItemFillerPresenter;
import com.gatar.Spizarka.ItemFiller.View.Buttons.ButtonFactory;
import com.gatar.Spizarka.ItemFiller.View.Buttons.MyButton;
import com.gatar.Spizarka.ItemFiller.View.DataView.DataViewFactory;
import com.gatar.Spizarka.ItemFiller.View.DataView.MyDataView;
import com.gatar.Spizarka.Main.View.MainActivity;


/**
 * Activity for provide input item's data by user (title, quantity etc.) Used for any item data input/modification,
 */
public class ItemFillerActivity extends AppCompatActivity implements
        ItemFillerMVP.RequiredViewOperations,
        ButtonFragment.ButtonFragmentListener{

    private ItemFillerMVP.PresenterOperations mPresenter;

    private MyDataView myDataView;
    private MyButton myButton;

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentDataViewFragment = null;
    private Fragment currentButtonFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_filler);
        this.mPresenter = new ItemFillerPresenter(this);

        setDataViewFragment();
        setButtonFragment();
        fragmentManager.executePendingTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCorrectView();
    }

    @Override
    public void onBackPressed(){
        toMainMenu();
    }


    //-------------------- methods from ButtonFragment interface ------------------------------
    @Override
    public void confirmButtonClick(boolean scanNext) {
        Item item = myDataView.getDataView();
        if(item != null) mPresenter.saveItem(item, scanNext);
    }


    //-------------------- methods from MVP interface ------------------------------
    @Override
    public void setDataView(ItemFillerOptions options, Item item) {
        myDataView = DataViewFactory.createDataView(options,currentDataViewFragment.getView());
        myDataView.setDataView();
        myDataView.fillDataView(item);
    }

    @Override
    public void setButtonView(ItemFillerOptions options) {
        myButton = ButtonFactory.createButton(options, currentButtonFragment.getView());
        myButton.setButtonView();
    }

    @Override
    public void toDepotActivity(){
        Intent intent = new Intent(this,DepotActivity.class);
        startActivity(intent);
    }

    @Override
    public void toMainMenu(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void toBarcodeScanner(){
        Intent intent = new Intent(this,BarcodeScannerActivity.class);
        startActivity(intent);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this.getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }


    private void setDataViewFragment(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentDataViewFragment = new DataViewFragment();
        fragmentTransaction.replace(R.id.change_data_view_container,this.currentDataViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setButtonFragment(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentButtonFragment = new ButtonFragment();
        fragmentTransaction.replace(R.id.change_button_container,this.currentButtonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
