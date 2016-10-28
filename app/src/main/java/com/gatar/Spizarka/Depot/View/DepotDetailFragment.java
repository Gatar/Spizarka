package com.gatar.Spizarka.Depot.View;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gatar.Spizarka.Database.Item;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.ItemFiller.View.ItemFillerActivity;
import com.gatar.Spizarka.Depot.Presenter.DepotDetailPresenter;
import com.gatar.Spizarka.Depot.DepotMVP;

/**
 * Fragment providing view with all data information of item chosen in {@link DepotOverviewFragment}
 * Contains button starting {@link ItemFillerActivity} with view for actualize data of item
 */
public class DepotDetailFragment extends Fragment implements DepotMVP.RequiredViewOperations.Detail {

    private DepotMVP.PresenterOperationsDetail mPresenter;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DepotDetailPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.depot_detail_fragment, container, false);
        this.view = view;
        mPresenter.fillViewWithData();

        Button button = (Button)view.findViewById(R.id.buttonDepotDetailUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateItem();
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.fillViewWithData();
    }



    /**
     * Set item data in fragment view. Data are extracted from database by item title.
     */
    @Override
    public void setDataOnView(Item item) {
        TextView textTitle = (TextView) view.findViewById(R.id.textDepotTitle);
        TextView textQuantity = (TextView) view.findViewById(R.id.textDepotQuantity);
        TextView textCategory = (TextView) view.findViewById(R.id.textDepotCategory);
        TextView textMiniumQuantity = (TextView) view.findViewById(R.id.textDepotMin);
        TextView textDescription = (TextView) view.findViewById(R.id.textDepotFullDescription);

        textTitle.setText(item.getTitle());
        textQuantity.setText(String.format("%d", item.getQuantity()));
        textCategory.setText(item.getCategory().toString());
        textMiniumQuantity.setText(String.format("%d", item.getMinimumQuantity()));
        textDescription.setText(item.getDescription());
    }


    @Override
    public void toUpdateItemDataActivity() {
        Intent intent = new Intent(getActivity(),ItemFillerActivity.class);
        startActivity(intent);
    }
}
