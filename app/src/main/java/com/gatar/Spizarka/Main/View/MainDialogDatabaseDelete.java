package com.gatar.Spizarka.Main.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.ManagerDAOImpl;
import com.gatar.Spizarka.Main.MainMVP;

/**
 * Dialog asking user about confirmation of internal database delete decision.
 */
public class MainDialogDatabaseDelete extends DialogFragment {

    private MainMVP.PresenterOperations mPresenter;

    public void setPresenter(MainMVP.PresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ask_before_database_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.deleteInternalDatabase();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
