package com.gatar.Spizarka.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.gatar.Spizarka.Fragments.Depot.DepotDetailFragment;
import com.gatar.Spizarka.Fragments.Depot.DepotOverviewFragment;
import com.example.gatar.Spizarka.R;

/**
 * Activity defining view for products in database.
 *
 * Contains one fragment in portrait view with possibilities:
 * 1. overview list for whole items {@link DepotOverviewFragment}
 * 2. detail information of one item after click on chosen item {@link DepotDetailFragment}
 *
 * Contains both above fragments in landscape view.
 */

public class DepotActivity extends AppCompatActivity implements
        DepotOverviewFragment.DepotOverviewFragmentActivityListener,
        DepotDetailFragment.DepotDetailFragmentActivityListener {

    private boolean isLand = false;
    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment = null;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot);

        setPreferences();
        isLand = getResources().getBoolean(R.bool.isLandscape);
    }


    @Override
    public void setDepotDetail(){
        if(isLand==true) {
            DepotDetailFragment detailFragment = (DepotDetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            this.fragmentManager.executePendingTransactions();
            detailFragment.setDataOnView();
        } else {
            setDepotDetailFragment();
            this.fragmentManager.executePendingTransactions();
            ((DepotDetailFragment)this.currentFragment).setDataOnView();
        }
    }


    @Override
    public void updateItemData(){
        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ChangeOptions.UpdateItem.toString());
        preferencesEditor.commit();

        Intent intent = new Intent(this,ChangeActivityUpdate.class);
        startActivity(intent);
    }


    private void setDepotDetailFragment(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentFragment = new DepotDetailFragment();
        fragmentTransaction.replace(R.id.depot_container,this.currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setPreferences(){
        preferences = getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}
