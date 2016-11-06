package com.gatar.Spizarka.Main.View;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.gatar.Spizarka.R;

/**
 * Main Activity, used for show MainMenu as fragment {@link MainMenuFragment}
 */
public class MainActivity extends Activity  {

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}

