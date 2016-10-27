package com.gatar.Spizarka.BarcodeScanner.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.ItemFiller.View.ItemFillerActivity;

/**
 * Class providing dialog box asking user about handling new barcode, which doesn't exist in database.
 * User have two options:
 * <ol>
 *     <li> Add barcode to existing product: set {@link ItemFillerOptions} to AddBarcodeToProduct</li>
 *     <li> Create completely new product: set {@link ItemFillerOptions} to AddProduct</li>
 * </ol>
 * Finally dialog box start method proceeding chosen option via activityListener in {@link ItemFillerActivity}
 */
public class ChangeDialogNewBarcode extends DialogFragment{
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    private final static String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setPreferences();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.new_barcode_info)
                .setPositiveButton(R.string.add_barcode_to_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.AddBarcodeToProduct.toString());
                        preferencesEditor.commit();
                        toItemFillerActivity();
                    }
                })
                .setNegativeButton(R.string.add_new_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.AddProduct.toString());
                        preferencesEditor.commit();
                        toItemFillerActivity();
                    }
                });

        return builder.create();
    }


    private void toItemFillerActivity(){
        Intent intent = new Intent(getActivity(), ItemFillerActivity.class);
        startActivity(intent);
    }

    private void setPreferences(){
        preferences = getActivity().getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}
