package com.gatar.Spizarka.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Manager for Android internal SQLite Database
 */
class AndroidDatabaseDAO extends SQLiteOpenHelper implements MethodsDAO {

    //TODO przeobić wszystkie obsługi bazy w programie na sterowanie przez ID, a nie przez title

    private final String INTERNAL_DATABASE_NAME;

    private final String TABLE_NAME_ITEMS = "ITEMS";
    private final String COLUMN_NAME_ID_KEY = "id";
    private final String COLUMN_NAME_TITLE = "title";
    private final String COLUMN_NAME_CATEGORY = "category";
    private final String COLUMN_NAME_QUANTITY = "quantity";
    private final String COLUMN_NAME_MINIMUM_QUANTITY = "minimum";
    private final String COLUMN_NAME_DESCRIPTION = "description";

    private final String TABLE_NAME_BARCODES = "BARCODES";
    private final String COLUMN_NAME_BARCODE_KEY = "barcode";
    private final String COLUMN_NAME_ITEM_ID = "item_id";

    private final String SQL_CREATE_TABLE_ITEMS=
            "CREATE TABLE " + TABLE_NAME_ITEMS + " (" +
                    COLUMN_NAME_ID_KEY + "              INTEGER     PRIMARY KEY     AUTOINCREMENT, " +
                    COLUMN_NAME_TITLE + "               TEXT        NOT NULL        UNIQUE, " +
                    COLUMN_NAME_CATEGORY + "            TEXT        NOT NULL, " +
                    COLUMN_NAME_QUANTITY + "            INTEGER     NOT NULL, " +
                    COLUMN_NAME_MINIMUM_QUANTITY + "    INTEGER     NOT NULL, " +
                    COLUMN_NAME_DESCRIPTION + "         TEXT );";

    private final String SQL_CREATE_TABLE_BARCODES=
            "CREATE TABLE " + TABLE_NAME_BARCODES + "(" +
                    COLUMN_NAME_BARCODE_KEY +"          TEXT    PRIMARY KEY     NOT NULL, " +
                    COLUMN_NAME_ITEM_ID + "         INTEGER    NOT NULL );";


    private Context context;

    private SQLiteDatabase db;

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
    public Integer getItemIdByBarcode(String barcode) {
        db = this.getReadableDatabase();

        if(!isContainBarcode(barcode)) try {
            throw new Exception("BarcodeDAO.getTitle: Barcode " + barcode + " doesn't exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
        return cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ITEM_ID));
    }

    @Override
    public Integer getItemIdByTitle(String title) {
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
        return cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID_KEY));
    }

    @Override
    public void addNewBarcode(String barcode, Integer itemId) {
        db = this.getWritableDatabase();

        if(isContainBarcode(barcode)) try {
            throw new Exception("BarcodeDAO.putData: Barcode " + barcode + " exists in database");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_BARCODE_KEY,barcode);
        values.put(COLUMN_NAME_ITEM_ID,itemId);

        db.insert(TABLE_NAME_BARCODES,null,values);
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
    public Item getSingleItem(Integer idItem) {
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
        context.deleteDatabase(INTERNAL_DATABASE_NAME);
    }

    private void fillItemWithValues(Cursor cursor, Item item){
        item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID_KEY)));
        item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
        item.setCategory(Categories.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORY))));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_QUANTITY)));
        item.setMinimumQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MINIMUM_QUANTITY)));
        item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)));
    }
}
