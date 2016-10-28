package com.gatar.Spizarka.ItemFiller.View.DataView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Class using for force close the keyboard when clicked outside of EditText fields.
 */
public class KeyboardHider implements View.OnClickListener {

        Activity mAct;

        public KeyboardHider(Activity act) {
            this.mAct = act;
        }

        @Override
        public void onClick(View v) {
            if ( v instanceof ViewGroup) {
                hideSoftKeyboard( this.mAct );
            }
        }

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
