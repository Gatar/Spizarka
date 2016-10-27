package com.gatar.Spizarka.ItemFiller.View.Buttons;

import android.view.View;
import android.widget.Button;

import com.example.gatar.Spizarka.R;

/**
 * Created by Gatar on 2016-10-27.
 */
abstract public class ButtonViewConnector {
    View view;

    Button buttonOk;
    Button buttonOkEnd;

    public ButtonViewConnector(View view) {
        this.view = view;

        buttonOk = (Button) view.findViewById(R.id.itemFillerOkButton);
        buttonOkEnd = (Button) view.findViewById(R.id.itemFillerOkEndButton);
    }
}
