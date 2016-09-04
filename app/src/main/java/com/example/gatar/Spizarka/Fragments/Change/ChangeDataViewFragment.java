package com.example.gatar.Spizarka.Fragments.Change;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gatar.Spizarka.Activities.ChangeOptions;
import com.example.gatar.Spizarka.Operations.Change.ChangeDataViewSet;
import com.example.gatar.Spizarka.R;

/**
 * Activity setting up fragment with editable view of data for one item.
 * What fields are permit to edit depends on ChangeOptions variable, which is get from the SharedPreferences via key CHANGE_ACTIVITY_OPTION.
 *
 * Abstract class responsible for view details and permissions: {@link com.example.gatar.Spizarka.Operations.Change.ChangeDataView}
 * Set permissions and fill it with data: {@link ChangeDataViewSet}
 * Get data from fields: {@link com.example.gatar.Spizarka.Operations.Change.ChangeDataViewGet}
 */
public class ChangeDataViewFragment extends Fragment {

    private ChangeDataViewFragmentListener listener;
    private SharedPreferences preferences;
    private ChangeOptions options;
    private ChangeDataViewSet viewOperations;

    private final static String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    public ChangeDataViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_data_full, container, false);

        setPreferences();

        viewOperations = new ChangeDataViewSet(view,options);
        viewOperations.setDataView();

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeDataViewFragmentListener) {
            listener = (ChangeDataViewFragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void setPreferences(){
        preferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        options = ChangeOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION,"Error"));
    }

    public interface ChangeDataViewFragmentListener {
        /**
         * Set data view fragment in container.
         */
        public void setDataView();
    }
}