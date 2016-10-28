package com.gatar.Spizarka.BarcodeScanner.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerMVP;
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

    private BarcodeScannerMVP.PresenterOperations mPresenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.new_barcode_info)
                .setPositiveButton(R.string.add_barcode_to_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.handleNewBarcode(ItemFillerOptions.AddBarcodeToProduct);
                    }
                })
                .setNegativeButton(R.string.add_new_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.handleNewBarcode(ItemFillerOptions.AddProduct);
                    }
                });

        return builder.create();
    }

    public void setPresenter(BarcodeScannerMVP.PresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
    }

}
