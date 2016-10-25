package com.gatar.Spizarka.Fragments.Depot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Activities.ChangeActivityUpdate;

/**
 * Fragment providing view with all data information of item chosen in {@link DepotOverviewFragment}
 * Contains button starting {@link ChangeActivityUpdate} with view for actualize data of item
 */
public class DepotDetailFragment extends Fragment {

    private DepotDetailFragmentActivityListener listener;
    private Item item;
    private SharedPreferences preferences;
    private ManagerDAO managerDAO;

    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";

    public DepotDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferences();
        managerDAO = new ManagerDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_depot_detail, container, false);

        Button button = (Button)view.findViewById(R.id.buttonDepotDetailUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.updateItemData();
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        setDataOnView();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DepotDetailFragmentActivityListener) {
            listener = (DepotDetailFragmentActivityListener) activity;
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

    /**
     * Set item data in fragment view. Data are extracted from database by item title.
     */
    public void setDataOnView(){
        item = managerDAO.getSingleItem(preferences.getString(EXTRA_TITLE,null));

        TextView textTitle = (TextView) getView().findViewById(R.id.textDepotTitle);
        TextView textQuantity = (TextView) getView().findViewById(R.id.textDepotQuantity);
        TextView textCategory = (TextView) getView().findViewById(R.id.textDepotCategory);
        TextView textMiniumQuantity = (TextView) getView().findViewById(R.id.textDepotMin);
        TextView textDescription = (TextView) getView().findViewById(R.id.textDepotFullDescription);

        textTitle.setText(item.getTitle());
        textQuantity.setText(String.format("%d", item.getQuantity()));
        textCategory.setText(item.getCategory().toString());
        textMiniumQuantity.setText(String.format("%d", item.getMinimumQuantity()));
        textDescription.setText(item.getDescription());

    }

    private void setPreferences(){
        preferences = getActivity().getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
    }

    public interface DepotDetailFragmentActivityListener {
        /**
         * Start intent of actualize current item data.
         */
        void updateItemData();
    }

}
