package com.example.gatar.Spizarka.Fragments.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.gatar.Spizarka.Database.ManagerDAO;
import com.example.gatar.Spizarka.R;


/**
 * Dialog in database asking user about confirmation of database delete decision.
 * If user confirm, DATABASE IS DELETED IN THIS CLASS by {@link ManagerDAO}
 */
public class MainDialogDatabaseDelete extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ask_before_database_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ManagerDAO managerDAO = new ManagerDAO(getActivity().getApplicationContext());
                        managerDAO.deleteDatabase();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
