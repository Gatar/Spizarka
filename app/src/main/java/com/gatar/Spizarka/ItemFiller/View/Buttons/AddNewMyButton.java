package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.example.gatar.Spizarka.R;

/**
 * Set on view buttons for add new item.
 */
public class AddNewMyButton extends MyButton {

    public AddNewMyButton(View view) {
        super(view);
    }

    @Override
    public void setButtonView() {
        buttonOk.setText(R.string.add_item);
        buttonOkEnd.setText(R.string.add_item_end);
        buttonOkEnd.setEnabled(true);
    }
}
