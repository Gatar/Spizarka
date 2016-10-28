package com.gatar.Spizarka.Main.View;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity;
import com.gatar.Spizarka.Depot.View.DepotActivity;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Main.MainMVP;
import com.gatar.Spizarka.Main.MainPresenter;

/**
 * Main Menu Fragment. Contains buttons which starts each type of activities in program.
 */
public class MainMenuFragment extends Fragment implements MainMVP.RequiredViewOperations {

    private MainMVP.PresenterOperations mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.main_menu_fragment,container,false);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonMainAdd:
                        mPresenter.toAdd();
                        break;
                    case R.id.buttonMainRemove:
                        mPresenter.toDecreaseQuantity();
                        break;
                    case R.id.buttonMainDepot:
                        mPresenter.toDepot();
                        break;
                    case R.id.buttonMainShoppingList:
                        mPresenter.toShoppingList();
                        break;
                    case R.id.buttonMainSettings:
                        mPresenter.toDatabaseDeleteDialog();
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

    @Override
    public void toDatabaseDeleteView() {
        MainDialogDatabaseDelete databaseDeleteDialog = new MainDialogDatabaseDelete();
        databaseDeleteDialog.setmPresenter(mPresenter);
        databaseDeleteDialog.show(getFragmentManager(),"databaseDelete");

    }

    @Override
    public void toBarcodeScannerView() {
        Intent intent = new Intent(getActivity(),BarcodeScannerActivity.class);
        startActivity(intent);
    }

    @Override
    public void toDepotView() {
        Intent intent = new Intent(getActivity(),DepotActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity().getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }
}
