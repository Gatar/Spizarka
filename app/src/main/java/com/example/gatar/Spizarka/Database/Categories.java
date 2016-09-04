package com.example.gatar.Spizarka.Database;

import com.example.gatar.Spizarka.R;

/**
 * Categories which are available to set for item.
 */
public enum Categories {
    Pasta("Makaron", R.drawable.pasta),
    Caryopsis("Ryż, kasza, etc.",R.drawable.caryopsis),
    Can("Produkty puszkowane", R.drawable.can),
    Dairy("Produkty mleczne",R.drawable.dairy),
    Drinks("Napoje, kawa, herbata",R.drawable.drinks),
    Sweets("Słodycze",R.drawable.sweets),
    Sauces("Sosy",R.drawable.sauces),
    Spices("Przyprawy", R.drawable.spices),
    Frozens("Mrożonki",R.drawable.frozens),
    Medicine("Lekarstwa",R.drawable.medicine),
    Hygiene("Art. higieniczne",R.drawable.hygiene),
    Chemistry("Srodki czystosci",R.drawable.chemicals),
    Other("Pozostałe",R.drawable.other);

    private String categoryName;
    private int iconId;

    Categories (String categoryName, int iconId){
        this.categoryName = categoryName;
        this.iconId = iconId;
    }

    /**
     * @return String with value (category name) set to constant, which are show to user in application
     */
    @Override
    public String toString(){
        return categoryName;
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