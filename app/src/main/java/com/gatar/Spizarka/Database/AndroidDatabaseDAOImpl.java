package com.gatar.Spizarka.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.Item;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Manager for Android internal SQLite Database
 */
class AndroidDatabaseDAOImpl extends SQLiteOpenHelper implements AndroidDatabaseDAO {


    private SQLiteDatabase db;

    AndroidDatabaseDAOImpl(Context context, String INTERNAL_DATABASE_NAME){
        super(context,INTERNAL_DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BARCODES);
        db.execSQL(SQL_CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public boolean isContainBarcode(String barcode) {
        db = this.getReadableDatabase();

        String[] projection = new String[]{COLUMN_NAME_BARCODE_KEY};
        String selection = COLUMN_NAME_BARCODE_KEY + " = ?";
        String[] selectionArgs = new String[]{barcode};

        Cursor cursor = db.query(
                TABLE_NAME_BARCODES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
                );

        return cursor.getCount() != 0;
    }

    @Override
    public Long getItemIdByBarcode(String barcode) {
        db = this.getReadableDatabase();

        String[] projection = new String[]{COLUMN_NAME_ITEM_ID};
        String selection = COLUMN_NAME_BARCODE_KEY + " = ?";
        String[] selectionArgs = new String[]{barcode};

        Cursor cursor = db.query(
                TABLE_NAME_BARCODES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        return cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ITEM_ID));
    }

    @Override
    public String getFirstBarcodeByItemId(Long itemId) {
        db = this.getReadableDatabase();

        String[] projection = new String[]{COLUMN_NAME_BARCODE_KEY};
        String selection = COLUMN_NAME_ITEM_ID + " = ?";
        String[] selectionArgs = new String[]{itemId.toString()};

        Cursor cursor = db.query(
                TABLE_NAME_BARCODES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BARCODE_KEY));
    }

    @Override
    public Long getItemIdByTitle(String title) {
        db = this.getReadableDatabase();

        String[] projection = new String[]{COLUMN_NAME_ID_KEY};
        String selection = COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = new String[]{title};

        Cursor cursor = db.query(
                TABLE_NAME_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        return cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID_KEY));
    }

    @Override
    public void addNewBarcode(Barcode barcode) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_BARCODE_KEY,barcode.getBarcode());
        values.put(COLUMN_NAME_ITEM_ID,barcode.getItemId());

        db.insert(TABLE_NAME_BARCODES,null,values);
    }

    @Override
    public void addNewBarcode(LinkedList<Barcode> barcodes) {
        db = this.getWritableDatabase();
        for(Barcode barcode : barcodes) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_BARCODE_KEY, barcode.getBarcode());
            values.put(COLUMN_NAME_ITEM_ID, barcode.getItemId());

            db.insert(TABLE_NAME_BARCODES, null, values);
        }
    }

    @Override
    public void addNewItem(Item item) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE,item.getTitle());
        values.put(COLUMN_NAME_CATEGORY,item.getCategory().name());
        values.put(COLUMN_NAME_QUANTITY,item.getQuantity());
        values.put(COLUMN_NAME_MINIMUM_QUANTITY,item.getMinimumQuantity());
        values.put(COLUMN_NAME_DESCRIPTION,item.getDescription());

        db.insert(
                TABLE_NAME_ITEMS,
                null,
                values);
        item.setId(getItemIdByTitle(item.getTitle()));
    }

    @Override
    public void addNewItem(LinkedList<Item> items) {
        db = this.getWritableDatabase();

        for(Item item : items) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_ID_KEY, item.getId());
            values.put(COLUMN_NAME_TITLE, item.getTitle());
            values.put(COLUMN_NAME_CATEGORY, item.getCategory().name());
            values.put(COLUMN_NAME_QUANTITY, item.getQuantity());
            values.put(COLUMN_NAME_MINIMUM_QUANTITY, item.getMinimumQuantity());
            values.put(COLUMN_NAME_DESCRIPTION, item.getDescription());

            db.insert(
                    TABLE_NAME_ITEMS,
                    null,
                    values);
            item.setId(getItemIdByTitle(item.getTitle()));
        }
    }

    @Override
    public void updateItem(Item item) {
        db = this.getReadableDatabase();

        String selection = COLUMN_NAME_ID_KEY + "= ?";
        String[] selectionArgs = new String[]{item.getId().toString()};

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, item.getTitle());
        values.put(COLUMN_NAME_CATEGORY,item.getCategory().name());
        values.put(COLUMN_NAME_QUANTITY,item.getQuantity());
        values.put(COLUMN_NAME_MINIMUM_QUANTITY,item.getMinimumQuantity());
        values.put(COLUMN_NAME_DESCRIPTION,item.getDescription());

        db.update(
                TABLE_NAME_ITEMS,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public Item getSingleItem(Long idItem) {
        db = this.getReadableDatabase();
        Item item = new Item();
        String selection = COLUMN_NAME_ID_KEY + " = ?";
        String [] selectionArgs = {idItem.toString()};

        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        fillItemWithValues(cursor,item);

        return item;
    }

    @Override
    public Item getSingleItem(String title) {
        db = this.getReadableDatabase();

        Item item = new Item();
        String selection = COLUMN_NAME_TITLE + " = ?";
        String [] selectionArgs = {title};

        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        fillItemWithValues(cursor,item);

        return item;
    }

    @Override
    public ArrayList<Item> getItemsByCategory(String category) {
        db = this.getReadableDatabase();

        ArrayList<Item> items = new ArrayList<Item>();

        String selection = COLUMN_NAME_CATEGORY + " = ?";
        String [] selectionArgs = {category};

        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            Item item = new Item();
            fillItemWithValues(cursor,item);
            items.add(item);
        }
        return items;
    }

    @Override
    public ArrayList<Item> getAllItems(boolean overZeroQuantity) {
        db = this.getReadableDatabase();

        ArrayList<Item> items = new ArrayList<Item>();

        String selection = COLUMN_NAME_QUANTITY + " > ?";
        String [] selectionArgs = {"0"};

        if(!overZeroQuantity) selectionArgs[0] = "-1";

        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            Item item = new Item();
            fillItemWithValues(cursor, item);
            items.add(item);
        }
        return items;
    }

    @Override
    public ArrayList<Item> getShoppingList() {
        db = this.getReadableDatabase();

        ArrayList<Item> items = new ArrayList<Item>();

        String selection = COLUMN_NAME_QUANTITY + " < " + COLUMN_NAME_MINIMUM_QUANTITY;

        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                selection,
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            Item item = new Item();
            fillItemWithValues(cursor,item);
            items.add(item);
        }
        return items;
    }


    @Override
    public void deleteDatabase() {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_BARCODES);
        db.execSQL("DELETE FROM " + TABLE_NAME_ITEMS);
    }

    private void fillItemWithValues(Cursor cursor, Item item){
        item.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID_KEY)));
        item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
        item.setCategory(Categories.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORY))));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_QUANTITY)));
        item.setMinimumQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MINIMUM_QUANTITY)));
        item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)));
    }
}
