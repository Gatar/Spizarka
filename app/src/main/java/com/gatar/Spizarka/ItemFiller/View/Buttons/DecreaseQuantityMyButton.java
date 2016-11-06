package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.example.gatar.Spizarka.R;

/**
 * Set on view buttons for remove item.
 */
public class DecreaseQuantityMyButton extends MyButton {

    public DecreaseQuantityMyButton(View view) {
        super(view);
    }

    @Override
    public void setButtonView() {
        buttonOk.setText(R.string.remove_item);
        buttonOkEnd.setText(R.string.remove_item_end);
        buttonOkEnd.setEnabled(true);
    }
}
