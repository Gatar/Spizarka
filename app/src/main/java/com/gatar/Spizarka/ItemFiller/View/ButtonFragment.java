package com.gatar.Spizarka.ItemFiller.View;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Fragment with two buttons for manage Data View.
 */
public class ButtonFragment extends Fragment {

    private ButtonFragmentListener listener;

    public ButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_filler_buttons, container, false);

        Button buttonAdd = (Button)view.findViewById(R.id.itemFillerOkButton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listener.confirmButtonClick(true);
            }
        });

        Button buttonAddEnd = (Button)view.findViewById(R.id.itemFillerOkEndButton);
        buttonAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.confirmButtonClick(false);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ButtonFragmentListener) {
            listener = (ButtonFragmentListener) activity;
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


    public interface ButtonFragmentListener {
        void confirmButtonClick(boolean scanNext);
    }
}
