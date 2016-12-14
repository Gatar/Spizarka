package com.gatar.Spizarka.Account.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Account.AccountDTO;
import com.gatar.Spizarka.Account.AccountMVP;

/**
 * Custom dialog box for input by user new password (twice time) and confirm the old one password.
 * Dialog box will be open until both new passwords are not equal or new password is shorter than 4 signs.
 */
public class AccountDialogNewPassword extends DialogFragment implements View.OnClickListener {

    private AccountMVP.PresenterOperations mPresenter;

    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mNewConfirmPassword;
    private Button mConfirm;
    private Button mCancel;

    public void setPresenter(AccountMVP.PresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_password, container);

        mOldPassword = (EditText) view.findViewById(R.id.oldPassword);
        mNewPassword = (EditText) view.findViewById(R.id.newPassword);
        mNewConfirmPassword = (EditText) view.findViewById(R.id.newPasswordConfirm);

        mConfirm = (Button) view.findViewById(R.id.button_confirm_new_password);
        mCancel = (Button) view.findViewById(R.id.button_cancel_new_password);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        String oldPassword = mOldPassword.getText().toString();
        String newPassword = mNewPassword.getText().toString();
        String newConfirmPassword = mNewConfirmPassword.getText().toString();

        if(v.equals(mCancel)) this.dismiss();

        if (v.equals(mConfirm) && newPassword.equals(newConfirmPassword) && newPassword.length() > 3) {
            AccountDTO newCredentials = new AccountDTO();
            newCredentials.setPassword(newPassword);
            mPresenter.changePassword(newCredentials,oldPassword);
            this.dismiss();
        } else {
            mNewConfirmPassword.setError(getString(R.string.text_new_password_dialog_alert));
            mNewConfirmPassword.requestFocus();
        }
    }

}
