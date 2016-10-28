package com.gatar.Spizarka.Depot.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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
        DepotOverviewFragment.DepotOverviewFragmentActivityListener{

    private boolean isLand = false;
    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot);

        isLand = getResources().getBoolean(R.bool.isLandscape);
    }


    @Override
    public void setDepotDetail(){
        if(isLand) {
            this.fragmentManager.executePendingTransactions();
        } else {
            setDepotDetailFragment();
            this.fragmentManager.executePendingTransactions();
        }
    }


    private void setDepotDetailFragment(){
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        this.currentFragment = new DepotDetailFragment();
        fragmentTransaction.replace(R.id.depot_container,this.currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
