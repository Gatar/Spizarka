package com.gatar.Spizarka.Operations.Change;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gatar.Spizarka.Activities.ChangeOptions;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Fragments.Change.ChangeDataViewFragment;

/**
 * Class providing object references for each field in {@link ChangeDataViewFragment} layout.
 * This provide access to change view and data in inherited classes.
 *
 */
abstract public class ChangeDataView {
    View view;
    ChangeOptions option;
    SharedPreferences preferences;

    TextView titleDescription;
    EditText titleText;

    TextView categoryDescription;
    Spinner categoryText;

    TextView quantityDescription;
    EditText quantityText;

    TextView quantityModificationDescription;
    EditText quantityModificationText;

    TextView quantityMinimumDescription;
    EditText quantityMinimumText;

    TextView descriptionDescription;
    EditText descriptionText;

    public ChangeDataView(View view, ChangeOptions option){
        this.view = view;
        this.option = option;

        titleDescription = (TextView)view.findViewById(R.id.textChangeTitleDescription);
        titleText = (EditText) view.findViewById(R.id.textChangeTitle);

        categoryDescription = (TextView)view.findViewById(R.id.textChangeCategoryDescription);
        categoryText = (Spinner) view.findViewById(R.id.textChangeCategory);

        quantityDescription = (TextView)view.findViewById(R.id.textChangeQuantityDescription);
        quantityText = (EditText) view.findViewById(R.id.textChangeQuantity);

        quantityModificationDescription = (TextView)view.findViewById(R.id.textChangeQuantityModificationDescription);
        quantityModificationText = (EditText) view.findViewById(R.id.textChangeQuantityModification);

        quantityMinimumDescription = (TextView)view.findViewById(R.id.textChangeQuantityMinimumDescription);
        quantityMinimumText = (EditText) view.findViewById(R.id.textChangeQuantityMinimum);

        descriptionDescription = (TextView)view.findViewById(R.id.textChangeDescriptionDescription);
        descriptionText = (EditText) view.findViewById(R.id.textChangeDescription);

        preferences = view.getContext().getSharedPreferences(view.getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);

    }
}
