package com.gatar.Spizarka.Main.View;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Account.AccountActivity;
import com.gatar.Spizarka.Application.MyApp;

import javax.inject.Inject;

/**
 * Main Activity, used for show MainMenu as fragment {@link MainMenuFragment}
 */
public class MainActivity extends Activity  {

    @Inject
    SharedPreferences preferences;

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment;

    private final String USERNAME_PREFERENCES = "com.gatar.Spizarka.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMenuFragment();
        MyApp.getAppComponent().inject(this);

        //Check for presence of Account in Preferences. If there are not data, goes to Account manager.
        checkAccountData();
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

    private void setLoginActivity(){
        Intent intent = new Intent(this,AccountActivity.class);
        startActivity(intent);
    }

    private void checkAccountData(){
        String login = preferences.getString(USERNAME_PREFERENCES,"");
        if(login.length() < 4) setLoginActivity();
    }

}

