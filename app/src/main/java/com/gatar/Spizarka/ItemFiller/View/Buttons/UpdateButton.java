package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;

import com.example.gatar.Spizarka.R;

/**
 * Created by Gatar on 2016-10-27.
 */
public class UpdateButton extends ButtonViewConnector implements ButtonViewStrategy {

    public UpdateButton(View view) {
        super(view);
    }

    @Override
    public void setButtonView() {
        buttonOk.setText(R.string.actualize_item);
        buttonOkEnd.setText("");
        buttonOkEnd.setEnabled(false);
    }
}
