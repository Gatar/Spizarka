package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Extendable class for all button view set objects. Contains references for each buttons in {@link com.gatar.Spizarka.ItemFiller.View.ButtonFragment}
 */
abstract public class MyButton {
    View view;

    Button buttonOk;
    Button buttonOkEnd;

    public MyButton(View view) {
        this.view = view;

        buttonOk = (Button) view.findViewById(R.id.itemFillerOkButton);
        buttonOkEnd = (Button) view.findViewById(R.id.itemFillerOkEndButton);
    }

    /**
     * Defines buttons parameters like labels, enable etc.
     */
    abstract public void setButtonView();
}
