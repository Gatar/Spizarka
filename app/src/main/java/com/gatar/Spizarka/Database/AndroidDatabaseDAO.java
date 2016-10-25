package com.gatar.Spizarka.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Manager for Android internal SQLite Database
 */
class AndroidDatabaseDAO extends SQLiteOpenHelper implements MethodsDAO {

    public final String INTERNAL_DATABASE_NAME;
    public final int    DATABASE_VERSION = 1;

    public final String TABLE_NAME_ITEMS = "itemsTable";
    public final String COLUMN_NAME_TITLE = "title";
    public final String COLUMN_NAME_CATEGORY = "category";
    public final String COLUMN_NAME_QUANTITY = "quantity";
    public final String COLUMN_NAME_MINIMUM_QUANTITY = "minimum";
    public final String COLUMN_NAME_DESCRIPTION = "description";

    public final String TABLE_NAME_BARCODES = "barcodes";
    public final String COLUMN_NAME_BARCODE = "barcode";

    public final String SQL_CREATE_TABLE_ITEMS=
            "CREATE TABLE " + TABLE_NAME_ITEMS + " (" +
                    COLUMN_NAME_TITLE + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_CATEGORY + " TEXT NOT NULL, " +
                    COLUMN_NAME_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_MINIMUM_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_DESCRIPTION + " TEXT );";

    public final String SQL_CREATE_TABLE_BARCODES=
            "CREATE TABLE " + TABLE_NAME_BARCODES + "(" +
                    COLUMN_NAME_BARCODE +" TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_TITLE + " TEXT NOT NULL );";


    private Context context;

    public AndroidDatabaseDAO(Context context, String INTERNAL_DATABASE_NAME){
        super(context,INTERNAL_DATABASE_NAME,null,1);
        this.context = context;
        this.INTERNAL_DATABASE_NAME = INTERNAL_DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BARCODES);
        db.execSQL(SQL_CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean isContainBarcode(String barcode) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ COLUMN_NAME_BARCODE +" FROM "+ TABLE_NAME_BARCODES +" WHERE "+ COLUMN_NAME_BARCODE +"='" + barcode + "'",null);
        if(cursor.getCount() != 0){
            return true;
        }
        return false;
    }

    @Override
    public String getTitle(String barcode) {
        if(!isContainBarcode(barcode)) try {
            throw new Exception("BarcodeDAO.getTitle: Barcode " + barcode + " doesn't exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ COLUMN_NAME_TITLE +" FROM "+ TABLE_NAME_BARCODES +" WHERE "+ COLUMN_NAME_BARCODE +"='" + barcode + "'",null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    @Override
    public void addNewBarcode(String barcode, String title) {
        if(isContainBarcode(barcode)) try {
            throw new Exception("BarcodeDAO.putData: Barcode " + barcode + " exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_BARCODE,barcode);
        values.put(COLUMN_NAME_TITLE,title);

        db.insert(TABLE_NAME_BARCODES,null,values);
    }

    @Override
    public void deleteBarcode(String barcode) {
        if(!isContainBarcode(barcode)) try {
            throw new NoSuchElementException("BarcodeDAO.deleteData: Data with barcode " + barcode + " doesn't exists in database");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME_BARCODES +" WHERE " + COLUMN_NAME_BARCODE +"='" + barcode +"'");
    }

    @Override
    public boolean isContainItem(String title) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ COLUMN_NAME_TITLE +" FROM "+ TABLE_NAME_ITEMS +" WHERE "+ COLUMN_NAME_TITLE +"='" + title + "'",null);
        if(cursor.getCount() != 0){
            cursor.close();
            return true;
        }

        return false;
    }

    @Override
    public void addNewItem(Item item) {
        if(isContainItem(item.getTitle())) try {
            throw new Exception("ItemDAO.addNewItem: Item with title  " + item.getTitle() + " exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE,item.getTitle());
        values.put(COLUMN_NAME_CATEGORY,item.getCategory().name());
        values.put(COLUMN_NAME_QUANTITY,item.getQuantity());
        values.put(COLUMN_NAME_MINIMUM_QUANTITY,item.getMinimumQuantity());
        values.put(COLUMN_NAME_DESCRIPTION,item.getDescription());

        db.insert(TABLE_NAME_ITEMS,null,values);
    }

    @Override
    public void updateItem(Item item) {
        if(!isContainItem(item.getTitle())) try {
            throw new Exception("ItemDAO.updateItem: Item with title  " + item.getTitle() + " doesn't exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_NAME_TITLE + "='" + item.getTitle() + "'";
        String [] selectionArgs = {item.getTitle()};

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CATEGORY,item.getCategory().name());
        values.put(COLUMN_NAME_QUANTITY,item.getQuantity());
        values.put(COLUMN_NAME_MINIMUM_QUANTITY,item.getMinimumQuantity());
        values.put(COLUMN_NAME_DESCRIPTION,item.getDescription());

        db.update(TABLE_NAME_ITEMS,values,selection,null);
    }

    @Override
    public Item getSingleItem(String title) {
        if(!isContainItem(title)) try {
            throw new Exception("ItemDAO.getSingleItem: Item with title  " + title + " doesn't exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Item item = new Item();
        String [] selectionArgs = {title};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                COLUMN_NAME_TITLE+"=?",
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

        ArrayList<Item> items = new ArrayList<Item>();

        String [] selectionArgs = {category};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                COLUMN_NAME_CATEGORY+"=?",
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
        ArrayList<Item> items = new ArrayList<Item>();

        String [] selectionArgs = {"0"};
        if(overZeroQuantity == false) selectionArgs[0] = "-1";


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                COLUMN_NAME_QUANTITY+">?",
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
        ArrayList<Item> items = new ArrayList<Item>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(false,
                TABLE_NAME_ITEMS,
                null,
                COLUMN_NAME_QUANTITY+"<"+COLUMN_NAME_MINIMUM_QUANTITY,
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
    public void deleteItem(String title) {
        if(!isContainItem(title)) try {
            throw new Exception("ItemDAO.deleteItem: Item with title  " + title + " doesn't exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME_ITEMS +" WHERE " + COLUMN_NAME_TITLE +"='" + title +"'");
    }

    @Override
    public void deleteDatabase() {
        context.deleteDatabase(INTERNAL_DATABASE_NAME);
    }

    private void fillItemWithValues(Cursor cursor, Item item){
        item.setTitle(cursor.getString(0));
        item.setCategory(Categories.valueOf(cursor.getString(1)));
        item.setQuantity(cursor.getInt(2));
        item.setMinimumQuantity(cursor.getInt(3));
        item.setDescription(cursor.getString(4));
    }
}
