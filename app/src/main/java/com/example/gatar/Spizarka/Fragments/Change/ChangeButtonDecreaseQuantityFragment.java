package com.example.gatar.Spizarka.Fragments.Change;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Activity setting up fragment with buttons for decrease quantity.
 */
public class ChangeButtonDecreaseQuantityFragment extends Fragment {

    private ChangeRemoveButtonFragmentListener listener;

    public ChangeButtonDecreaseQuantityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_remove_button, container, false);

        setButtons(view);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeRemoveButtonFragmentListener) {
            listener = (ChangeRemoveButtonFragmentListener) activity;
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

    private void setButtons(View view){
        Button buttonAdd = (Button)view.findViewById(R.id.buttonChangeRemove);
        buttonAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listener.changeQuantityItem(false);
            }
        });

        Button buttonAddEnd = (Button)view.findViewById(R.id.buttonChangeRemoveEnd);
        buttonAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.changeQuantityItem(true);
            }
        });
    }

    public interface ChangeRemoveButtonFragmentListener {
        /**
         * Extract data of quantity change from user input view and proceed them in database. Works for both increase/decrease changes.
         * @param isItTheLast true - there won't be next items to add, go to Main Activity; false - there will be next items, go to Barcode Scanner
         */
        void changeQuantityItem(boolean isItTheLast);
    }
}
