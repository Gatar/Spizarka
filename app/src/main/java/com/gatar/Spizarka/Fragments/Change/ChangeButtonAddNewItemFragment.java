package com.gatar.Spizarka.Fragments.Change;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Activity setting up fragment with buttons for add new item to database.
 */
public class ChangeButtonAddNewItemFragment extends Fragment {

    private ChangeAddButtonFragmentListener listener;

    public ChangeButtonAddNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_add_button, container, false);

        Button buttonAdd = (Button)view.findViewById(R.id.buttonChangeAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listener.addNewItem(false);
            }
        });

        Button buttonAddEnd = (Button)view.findViewById(R.id.buttonChangeAddEnd);
        buttonAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addNewItem(true);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeAddButtonFragmentListener) {
            listener = (ChangeAddButtonFragmentListener) activity;
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



    public interface ChangeAddButtonFragmentListener {
        /**
         * Extract data of new item from TextFields in user input view and add new item with barcode to database.
         * @see ChangeButtonAddNewItemFragment#listener
         * @param isItTheLast true - there won't be next items to add, go to Main Activity; false - there will be next items, go to Barcode Scanner
         */
        void addNewItem(boolean isItTheLast);
    }
}
