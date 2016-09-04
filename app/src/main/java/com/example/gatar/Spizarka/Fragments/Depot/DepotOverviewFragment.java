package com.example.gatar.Spizarka.Fragments.Depot;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.gatar.Spizarka.Activities.ChangeActivity;
import com.example.gatar.Spizarka.Activities.ChangeOptions;
import com.example.gatar.Spizarka.Activities.DepotOptions;
import com.example.gatar.Spizarka.Database.Categories;
import com.example.gatar.Spizarka.Database.Item;
import com.example.gatar.Spizarka.Database.ManagerDAO;
import com.example.gatar.Spizarka.Operations.Depot.DepotCategoryLimit;
import com.example.gatar.Spizarka.Operations.Depot.DepotOverviewAdapter;
import com.example.gatar.Spizarka.Operations.Depot.DepotSort;
import com.example.gatar.Spizarka.Operations.Depot.DepotSortTypes;
import com.example.gatar.Spizarka.Operations.Depot.DepotShoppingListAdapter;
import com.example.gatar.Spizarka.R;

import java.util.ArrayList;

/**
 * Fragment with ListView of all items in database.
 *
 * Could be limited by:
 * <ol>
 *     <li>Category type of item {@link DepotCategoryLimit}</li>
 * </ol>
 *
 * Could be sort by:
 * <ol>
 *     <li>Title {@link DepotSort}</li>
 *     <li>Category {@link DepotSort}</li>
 * </ol>
 */

public class DepotOverviewFragment extends Fragment  {

    public DepotOverviewFragmentActivityListener listener;
    private ArrayList<Item> depotItems;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ArrayAdapter<Item> adapter;
    private DepotOptions options;
    private ManagerDAO managerDAO;
    private DepotSort depotSort;
    private ListView listView;

    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";
    private final static String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_depot_overview, container,false);

        managerDAO = new ManagerDAO(view.getContext());

        setPreferences();
        setChosenView(view);
        loadListAdaptor(view);

        setCategoryLimitButton(view);
        setSortButton(view);
        setListViewItemListener();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DepotOverviewFragmentActivityListener) {
            listener = (DepotOverviewFragmentActivityListener) activity;
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


    public interface DepotOverviewFragmentActivityListener{
        /**
         * Set fragment with detailed view fragment {@link DepotDetailFragment} of item clicked.
         */
        void setDepotDetail();
    }

    private void setChosenView(View view){
        switch(options){
            case DepotView:
                depotItems = managerDAO.getAllItems(true);
                setOverviewListAdaptor(view);
                break;

            case ShoppingListView:
                depotItems = managerDAO.getShoppingList();
                adapter = new DepotShoppingListAdapter(view.getContext(), R.layout.depot_row,depotItems);
                break;

            case AddBarcodeToExistingItemView:
                depotItems = managerDAO.getAllItems(false);
                setOverviewListAdaptor(view);
                break;
        }
    }

    private void loadListAdaptor(View view){
        listView = (ListView) view.findViewById(R.id.listDepot);
        listView.setAdapter(adapter);
    }

    private void setOverviewListAdaptor(View view){
        adapter = new DepotOverviewAdapter(view.getContext(),R.layout.depot_row,depotItems);
    }

    private void setListViewItemListener(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = listView.getItemAtPosition(position);
                Item item = (Item) o;

                if(options == DepotOptions.AddBarcodeToExistingItemView){
                    String barcode = preferences.getString(EXTRA_BARCODE,null);
                    String title = item.getTitle();

                    preferencesEditor.putString(EXTRA_TITLE,item.getTitle());
                    preferencesEditor.putString(CHANGE_ACTIVITY_OPTION,ChangeOptions.IncreaseQuantity.toString());
                    preferencesEditor.commit();
                    managerDAO.addNewBarcode(barcode,title);

                    toChangeActivity();

                }else {
                    preferencesEditor.putString(EXTRA_TITLE,item.getTitle());
                    preferencesEditor.commit();
                    listener.setDepotDetail();
                }
            }
        });
    }
    private void setSortButton(View receivedView) {
        final View view = receivedView;
        depotSort = new DepotSort(depotItems);

        final Button buttonSort = (Button)view.findViewById(R.id.buttonDepotSort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(view.getContext(),buttonSort);
                popup.getMenuInflater().inflate(R.menu.depot_sort, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        depotSort.setDepotItems(depotItems);
                        switch(item.getItemId()){
                            case R.id.sortByName:
                                depotSort.sort(DepotSortTypes.ByName);
                                break;

                            case R.id.sortByCategory:
                                depotSort.sort(DepotSortTypes.ByCategory);
                                break;
                        }
                        loadListAdaptor(view);
                        return true;
                    }
                });
                popup.show();
            }
        });

    }

    private void setCategoryLimitButton(View receivedView){
        final View view = receivedView;
        final DepotCategoryLimit depotCategoryLimit = new DepotCategoryLimit(depotItems);

        final Button buttonCategory = (Button)view.findViewById(R.id.buttonDepotLimit);
        buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popup = new PopupMenu(view.getContext(),buttonCategory);
                Categories [] categories = Categories.values();
                popup.getMenu().add(R.string.category_all);
                for (Categories cat: categories) {
                    popup.getMenu().add(cat.toString());
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals(getResources().getString(R.string.category_all))){
                            depotCategoryLimit.getAllItems();
                        }else {
                            Categories chosenCategory = Categories.getEnumByCategoryName(item.getTitle().toString());
                            depotCategoryLimit.limitByCategory(chosenCategory);
                        }
                        setOverviewListAdaptor(view);
                        loadListAdaptor(view);
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    private void setPreferences(){
        preferences = getActivity().getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        options = DepotOptions.valueOf(preferences.getString(DEPOT_ACTIVITY_OPTION, DepotOptions.DepotView.name()));
    }

    private void toChangeActivity(){
        Intent intent = new Intent(getView().getContext(), ChangeActivity.class);
        startActivity(intent);
    }
}
