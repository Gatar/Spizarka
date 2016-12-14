package com.gatar.Spizarka.Account.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Account.AccountMVP;

/**
 * Dialogbox for confirm password for complete delete account from WebAPI (and also all data from internal database).
 */

public class AccountDialogDelete extends DialogFragment implements View.OnClickListener {

    private AccountMVP.PresenterOperations mPresenter;

    private EditText mPassword;
    private Button mConfirm;
    private Button mCancel;

    public void setPresenter(AccountMVP.PresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_account, container);

        mPassword = (EditText) view.findViewById(R.id.confirmAccountDeletePassword);

        mConfirm = (Button) view.findViewById(R.id.button_confirm_delete_account);
        mCancel = (Button) view.findViewById(R.id.button_cancel_delete_account);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        String password = mPassword.getText().toString();


        if(v.equals(mCancel)) this.dismiss();

        if (v.equals(mConfirm) && password.length() > 3) {
            mPresenter.deleteAccount(password);
            this.dismiss();
        } else {
            mPassword.setError(getString(R.string.text_too_short_password));
            mPassword.requestFocus();
        }
    }


}
