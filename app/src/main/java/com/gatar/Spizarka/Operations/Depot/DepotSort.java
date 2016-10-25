package com.gatar.Spizarka.Operations.Depot;

import com.gatar.Spizarka.Database.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Provide sorting operations on ArrayList of items received by reference in constructor.
 * Sorting by:
 * <ol>
 *     <li>Title</li>
 *     <li>Category</li>
 * </ol>
 */
public class DepotSort {

    private ArrayList<Item> depotItems;
    private SortByName sortByName = new SortByName();
    private SortByCategory sortByCategory = new SortByCategory();
    private boolean ascending = true;

    /**
     *
     * @param depotItems default ArrayList to sort.
     */
    public DepotSort(ArrayList<Item> depotItems){
        this.depotItems = depotItems;
    }

    /**
     * Set new ArrayList of Item to sort.
     * @param depotItems change reference to sorting ArrayList
     */
    public void setDepotItems(ArrayList<Item> depotItems) {
        this.depotItems = depotItems;
    }

    public void sort(DepotSortTypes sortType){
        switch (sortType){
            case ByName:
                Collections.sort(depotItems,sortByName);
                break;
            case ByCategory:
                Collections.sort(depotItems,sortByCategory);
                break;
        }

        if(ascending)ascending = false;
        else ascending = true;

    }

    private class SortByCategory implements Comparator<Item>{

        @Override
        public int compare(Item lhs, Item rhs) {
            if(ascending == true){
                return lhs.getCategory().name().compareTo(rhs.getCategory().name());
            }else{
                return rhs.getCategory().name().compareTo(lhs.getCategory().name());
            }
        }
    }

    private class SortByName implements Comparator<Item>{

        @Override
        public int compare(Item lhs, Item rhs) {
            if(ascending == true){
                return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
            }else{
                return rhs.getTitle().compareToIgnoreCase(lhs.getTitle());
            }
        }
    }



}
