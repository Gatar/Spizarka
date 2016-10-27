package com.gatar.Spizarka.ItemFiller.View.DataView;

import com.gatar.Spizarka.Database.Item;

/**
 * Created by Gatar on 2016-10-27.
 */
public interface DataViewStrategy {

    void setDataView();

    void fillDataView(Item item);
}
