package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.example.gatar.Spizarka.R;

/**
 * Set on view buttons for update item.
 */
public class UpdateMyButton extends MyButton {

    public UpdateMyButton(View view) {
        super(view);
    }

    @Override
    public void setButtonView() {
        buttonOkEnd.setText(R.string.actualize_item);
        buttonOk.setText("");
        buttonOk.setEnabled(false);
    }
}
