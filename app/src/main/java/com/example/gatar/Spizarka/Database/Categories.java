package com.example.gatar.Spizarka.Database;

import com.example.gatar.Spizarka.Operations.MyApp;
import com.example.gatar.Spizarka.R;

/**
 * Categories which are available to set for item.
 */
public enum Categories {
    Pasta(R.string.category_pasta, R.drawable.pasta),
    Caryopsis(R.string.category_caryopsis,R.drawable.caryopsis),
    Can(R.string.category_can, R.drawable.can),
    Dairy(R.string.category_dairy,R.drawable.dairy),
    Drinks(R.string.category_drinks,R.drawable.drinks),
    Sweets(R.string.category_sweets,R.drawable.sweets),
    Sauces(R.string.category_sauces,R.drawable.sauces),
    Spices(R.string.category_spices, R.drawable.spices),
    Frozens(R.string.category_frozens,R.drawable.frozens),
    Medicine(R.string.category_medicine,R.drawable.medicine),
    Hygiene(R.string.category_hygiene,R.drawable.hygiene),
    Chemistry(R.string.category_chemistry,R.drawable.chemicals),
    Other(R.string.category_other,R.drawable.other);

    private int textId;
    private int iconId;

    Categories (int textId, int iconId){
        this.textId = textId;
        this.iconId = iconId;
    }

    /**
     * @return String with value (category name) set to constant, which are show to user in application
     */
    @Override
    public String toString(){
        return MyApp.getAppContext().getString(textId);
    }

    /**
     * @return A picture ID set to category constant
     */
    public int getIconId(){
        return iconId;
    }

    /**
     *  Get Categories object by its name.
     * @param name - Category value (name), which are show to user (NOT enum constants!)
     * @return object Category.constant which are set to specific value on parameter
     * Attention!: If parameter value doesn't exist in Categories return value is Categories.Other
     */
    static public Categories getEnumByCategoryName(String name){
        Categories [] categories = Categories.values();
        for(Categories c: categories){
            if (c.toString().equals(name)){
                return c;
            }
        }
        return Categories.Other;
    }

    /**
     * Get Categories ordinal number by its name.
     * @param name - Category value (name), which are show to user (NOT enum constants!)
     * @return ordinal number set to specific value on parameter
     * Attention!: If parameter value doesn't exist in Categories return value for Categories.Other
     */
    static public int getOrdinalNoByCategoryName(String name){
        Categories [] categories = Categories.values();
        for(Categories c: categories){
            if (c.toString().equals(name)){
                return c.ordinal();
            }
        }
        return Categories.Other.ordinal();
    }

}