package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.example.gatar.Spizarka.R;

/**
 * Set on view buttons for increase quantity of item.
 */
public class IncreaseQuantityMyButton extends MyButton {

    public IncreaseQuantityMyButton(View view) {
        super(view);
    }

    @Override
    public void setButtonView() {
        buttonOk.setText(R.string.actualize_item);
        buttonOkEnd.setText(R.string.actualize_item_end);
        buttonOkEnd.setEnabled(true);
    }
}
