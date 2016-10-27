package com.gatar.Spizarka.ItemFiller.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Operations.Change.ChangeDataViewSet;
import com.gatar.Spizarka.Operations.Change.ChangeHideKeyboard;

/**
 * Created by Gatar on 2016-10-27.
 */
public class DataViewFragment extends Fragment {

    public DataViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_data_full, container, false);

        closeUnusedKeyboard(view);

        return view;
    }

    private void closeUnusedKeyboard(View view){
        view.setOnClickListener( new ChangeHideKeyboard(getActivity()) );
    }

}
