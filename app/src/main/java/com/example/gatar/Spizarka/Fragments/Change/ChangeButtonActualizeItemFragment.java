package com.example.gatar.Spizarka.Fragments.Change;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Activity setting up fragment with buttons for actualize data of item selected in depot detail view.
 */
public class ChangeButtonActualizeItemFragment extends Fragment {
    private ChangeActualizeButtonFragmentListener listener;

    public ChangeButtonActualizeItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_actualize_button, container, false);

        Button buttonAdd = (Button)view.findViewById(R.id.buttonChangeActualize);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.actualizeItem();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeActualizeButtonFragmentListener) {
            listener = (ChangeActualizeButtonFragmentListener) activity;
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


    public interface ChangeActualizeButtonFragmentListener {

        /**
         * Extract data of existing item from TextFields in user input view and overwrite them for existing item in database.
         * Should be used only if item exist in database.
         */
        void actualizeItem();
    }
}
