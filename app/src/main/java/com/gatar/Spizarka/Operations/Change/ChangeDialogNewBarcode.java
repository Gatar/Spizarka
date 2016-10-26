package com.gatar.Spizarka.Operations.Change;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gatar.Spizarka.Activities.ChangeOptions;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Activities.ChangeActivityUpdate;

/**
 * Class providing dialog box asking user about handling new barcode, which doesn't exist in database.
 * User have two options:
 * <ol>
 *     <li> Add barcode to existing product: set {@link ChangeOptions} to AddBarcodeToProduct</li>
 *     <li> Create completely new product: set {@link ChangeOptions} to AddProduct</li>
 * </ol>
 * Finally dialog box start method proceeding chosen option via activityListener in {@link ChangeActivityUpdate}
 */
public class ChangeDialogNewBarcode extends DialogFragment{
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ChangeDialogAddTypeListener listener;

    private final static String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setPreferences();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.new_barcode_info)
                .setPositiveButton(R.string.add_barcode_to_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ChangeOptions.AddBarcodeToProduct.toString());
                        preferencesEditor.commit();
                        listener.proceedChosenOption();
                    }
                })
                .setNegativeButton(R.string.add_new_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION, ChangeOptions.AddProduct.toString());
                        preferencesEditor.commit();
                        listener.proceedChosenOption();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeDialogAddTypeListener) {
            listener = (ChangeDialogAddTypeListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface ChangeDialogAddTypeListener {
        /**
         * Get from shared preferences chosen option {@link ChangeOptions} of work and set right data view and buttons fragments for it.
         */
        void proceedChosenOption();
    }

    private void setPreferences(){
        preferences = getActivity().getSharedPreferences(getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}
