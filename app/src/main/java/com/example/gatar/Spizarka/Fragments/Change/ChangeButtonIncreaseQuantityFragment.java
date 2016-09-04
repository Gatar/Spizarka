package com.example.gatar.Spizarka.Fragments.Change;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Activity setting up fragment with buttons for increase quantity.
 */
public class ChangeButtonIncreaseQuantityFragment extends Fragment {

    private ChangeQuantityChangeButtonFragmentListener listener;

    public ChangeButtonIncreaseQuantityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_quality_change_button, container, false);

        Button buttonAdd = (Button)view.findViewById(R.id.buttonChangeQuantityChange);
        buttonAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listener.changeQuantityItem(false);
            }
        });

        Button buttonAddEnd = (Button)view.findViewById(R.id.buttonChangeQuantityChangeEnd);
        buttonAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.changeQuantityItem(true);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeQuantityChangeButtonFragmentListener) {
            listener = (ChangeQuantityChangeButtonFragmentListener) activity;
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


    public interface ChangeQuantityChangeButtonFragmentListener {
        /**
         * Extract data of quantity change from user input view and proceed them in database. Works for both increase/decrease changes.
         * @param isItTheLast true - there won't be next items to add, go to Main Activity; false - there will be next items, go to Barcode Scanner
         */
        void changeQuantityItem(boolean isItTheLast);
    }
}
