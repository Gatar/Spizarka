package com.example.gatar.Spizarka.Fragments.Main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatar.Spizarka.Activities.BarcodeScannerActivity;
import com.example.gatar.Spizarka.Activities.DepotActivity;
import com.example.gatar.Spizarka.R;

/**
 * Main Menu Fragment. Contains buttons which starts each type of activities in program.
 */
public class MainMenuFragment extends Fragment {

    MenuFragmentActivityListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_main_menu,container,false);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonMainAdd:
                        listener.toAdd();
                        break;
                    case R.id.buttonMainRemove:
                        listener.toDecreaseQuantity();
                        break;
                    case R.id.buttonMainDepot:
                        listener.toDepot();
                        break;
                    case R.id.buttonMainShoppingList:
                        listener.toShoppingList();
                        break;
                    case R.id.buttonMainSettings:
                        listener.toDatabaseDelete();
                        break;
                    default:
                        break;
                }
            }
        };

        Button buttonToAdd = (Button) view.findViewById(R.id.buttonMainAdd);
        Button buttonToRemove = (Button) view.findViewById(R.id.buttonMainRemove);
        Button buttonToDepot = (Button) view.findViewById(R.id.buttonMainDepot);
        Button buttonToShoppingList = (Button) view.findViewById(R.id.buttonMainShoppingList);
        Button buttonToSettings = (Button) view.findViewById(R.id.buttonMainSettings);

        buttonToAdd.setOnClickListener(clickListener);
        buttonToRemove.setOnClickListener(clickListener);
        buttonToDepot.setOnClickListener(clickListener);
        buttonToShoppingList.setOnClickListener(clickListener);
        buttonToSettings.setOnClickListener(clickListener);

        return view;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        if( activity instanceof MenuFragmentActivityListener){
            listener = (MenuFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + " musi implementować interfejs:MainMenuFragment.MenuFragmentActivityListener");
        }

    }

    public interface MenuFragmentActivityListener{

        /**
         * TEMPORARY: Set dialog box about database delete, finally -> settings.
         * {@link MainDialogDatabaseDelete}
         */
        void toDatabaseDelete();

        /**
         * Set BarcodeScannerActivity to scan barcode of add product.
         * {@link BarcodeScannerActivity}
         */
        void toAdd();

        /**
         * Set BarcodeScannerActivity to scan barcode of product, which quantity will be decreased.
         * {@link BarcodeScannerActivity}
         * If removed quantity are lower than on stock quantity it will be set at 0.
         */
        void toDecreaseQuantity();

        /**
         * Set DepotActivity with view what are on stock.
         * {@link DepotActivity}
         */
        void toDepot();

        /**
         * Set DepotActivity with view what products are under minimum level of quantity.
         * {@link DepotActivity}
         */
        void toShoppingList();
    }
}
