package com.gatar.Spizarka.Depot.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.ItemFiller.View.ItemFillerActivity;
import com.gatar.Spizarka.Depot.DepotMVP;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.Presenter.DepotOverviewPresenter;
import com.gatar.Spizarka.Depot.Operations.DepotCategoryLimiter;
import com.gatar.Spizarka.Depot.Operations.DepotOverviewAdapter;
import com.gatar.Spizarka.Depot.Operations.DepotSort;
import com.gatar.Spizarka.Depot.Operations.DepotSortTypes;
import com.example.gatar.Spizarka.R;

import java.util.ArrayList;

/**
 * Fragment with ListView of all items in database.
 *
 * Could be limited by:
 * <ol>
 *     <li>Category type of item {@link DepotCategoryLimiter}</li>
 * </ol>
 *
 * Could be sort by:
 * <ol>
 *     <li>Title {@link DepotSort}</li>
 *     <li>Category {@link DepotSort}</li>
 * </ol>
 */

public class DepotOverviewFragment extends Fragment implements DepotMVP.RequiredViewOperations.Overview {

    private DepotMVP.PresenterOperationsOverview mPresenter;

    public DepotOverviewFragmentActivityListener activityListener;
    private ArrayList<Item> depotItems;
    private ArrayAdapter<Item> adapter;
    private ListView listView;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPresenter = new DepotOverviewPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.depot_overview_fragment, container,false);

        mPresenter.setListView();

        setCategoryLimitButton(view);
        setSortButton(view);
        setListViewItemListener();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DepotOverviewFragmentActivityListener) {
            activityListener = (DepotOverviewFragmentActivityListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        activityListener = null;
    }


    @Override
    public void toChangeActivity(){
        Intent intent = new Intent(getActivity(), ItemFillerActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(view.getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillListByItems(ArrayList<Item> depotItems, DepotOptions options) {
        this.depotItems = depotItems;
        setOverviewListAdaptor(view, options);
        loadListAdaptor(view);
    }

    @Override
    public void setDetailFragment() {
        activityListener.setDepotDetail();
    }


    private void loadListAdaptor(View view){
        listView = (ListView) view.findViewById(R.id.listDepot);
        listView.setAdapter(adapter);
    }

    private void setOverviewListAdaptor(View view, DepotOptions depotOptions){
        adapter = new DepotOverviewAdapter(view.getContext(),R.layout.depot_row,depotItems, depotOptions);
    }

    private void setListViewItemListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Item item = (Item) listView.getItemAtPosition(position);
                mPresenter.setItemForDetailFragment(item);
            }
        });
    }

    private void setSortButton(View receivedView) {
        final View view = receivedView;

        final Button buttonSort = (Button)view.findViewById(R.id.buttonDepotSort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(view.getContext(),buttonSort);
                popup.getMenuInflater().inflate(R.menu.depot_sort, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.sortByName:
                                mPresenter.sortListView(depotItems,DepotSortTypes.ByName);
                                break;

                            case R.id.sortByCategory:
                                mPresenter.sortListView(depotItems,DepotSortTypes.ByCategory);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

    }


    private void setCategoryLimitButton(View receivedView){
        final View view = receivedView;

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
                            mPresenter.setListView();
                        }else {
                            Categories chosenCategory = Categories.getEnumByCategoryName(item.getTitle().toString());
                            mPresenter.limitByCategoryListView(depotItems,chosenCategory);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }


    public interface DepotOverviewFragmentActivityListener{
        /**
         * Set fragment with detailed view fragment {@link DepotDetailFragment} of item clicked.
         */
        void setDepotDetail();
    }
}
