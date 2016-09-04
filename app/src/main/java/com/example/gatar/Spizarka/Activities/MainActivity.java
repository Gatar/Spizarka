package com.example.gatar.Spizarka.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gatar.Spizarka.Fragments.Main.MainMenuFragment;
import com.example.gatar.Spizarka.Fragments.Main.MainDialogDatabaseDelete;
import com.example.gatar.Spizarka.R;

public class MainActivity extends Activity implements
        MainMenuFragment.MenuFragmentActivityListener {

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment;

    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPreferences();
        setMenuFragment();

    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_main);
        setMenuFragment();
    }

    /**
     * Set main menu fragment in MainActivity.
     * {@link MainMenuFragment}
     */
    public void setMenuFragment(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentFragment = new MainMenuFragment();
        fragmentTransaction.replace(R.id.main_container, this.currentFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void toAdd(){
        Intent intent = new Intent(this,BarcodeScannerActivity.class);
        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION,ChangeOptions.AddProduct.toString());
        preferencesEditor.commit();
        startActivity(intent);
    }


    @Override
    public void toDecreaseQuantity(){
        Intent intent = new Intent(this,BarcodeScannerActivity.class);
        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION,ChangeOptions.DecreaseQuantity.toString());
        preferencesEditor.commit();
        startActivity(intent);
    }


    @Override
    public void toDepot(){
        Intent intent = new Intent(this,DepotActivity.class);
        preferencesEditor.putString(DEPOT_ACTIVITY_OPTION,DepotOptions.DepotView.toString());
        preferencesEditor.commit();
        startActivity(intent);
    }


    @Override
    public void toShoppingList(){
        Intent intent = new Intent(this,DepotActivity.class);
        preferencesEditor.putString(DEPOT_ACTIVITY_OPTION,DepotOptions.ShoppingListView.toString());
        preferencesEditor.commit();
        startActivity(intent);
    }


    @Override
    public void toDatabaseDelete(){
        //TODO Make settings menu. Temporary here are delete of database.
        MainDialogDatabaseDelete databaseDelete = new MainDialogDatabaseDelete();
        databaseDelete.show(fragmentManager,"databaseDelete");
    }

    private void setPreferences(){
        preferences = getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}

