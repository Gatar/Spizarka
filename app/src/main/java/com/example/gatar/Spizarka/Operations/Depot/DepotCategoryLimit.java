package com.example.gatar.Spizarka.Operations.Depot;

import com.example.gatar.Spizarka.Database.Categories;
import com.example.gatar.Spizarka.Database.Item;

import java.util.ArrayList;

/**
 * Limiting list of items show in DepotOverview by Category.
 */
public class DepotCategoryLimit {
    private ArrayList<Item> depotItemsCopy = new ArrayList<>();
    private ArrayList<Item> depotItems;

    /**
     * Set in class copy of reference to list of Items.
     * @param depotItems list of Items in DepotOverview.
     */
    public DepotCategoryLimit(ArrayList<Item> depotItems) {
        this.depotItemsCopy.addAll(depotItems);
        this.depotItems = depotItems;
    }

    /**
     * Create empty Item array in place of original and fill it with items from limited category only.
     * @param category Chosen limiting Category
     */
    public void limitByCategory(Categories category){
        depotItems.clear();
        for(Item item: depotItemsCopy){
            if(item.getCategory().equals(category)) depotItems.add(item);
        }
    }

    /**
     * Return original list of items.
     */
    public void getAllItems(){
        depotItems.clear();
        depotItems.addAll(depotItemsCopy);
    }

}
