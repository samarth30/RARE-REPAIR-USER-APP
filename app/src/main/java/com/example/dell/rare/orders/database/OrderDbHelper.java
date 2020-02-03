package com.example.dell.rare.orders.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import com.airbnb.lottie.model.layer.NullLayer;

public class OrderDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Order_db";
    public static final int DB_VERSION = 1;
    public static final String CREATE_QUERY = "create table " + OrderContract.OrderEntry.TABLE_NAME+
                                               "( " + OrderContract.OrderEntry.COMPLETED + " text,"
                                                     + OrderContract.OrderEntry.BRAND + " text,"
                                                     + OrderContract.OrderEntry.MODEL + " text,"
                                                     + OrderContract.OrderEntry.COLOR + " text,"
                                                     + OrderContract.OrderEntry.DEFECTS+ " text,"
                                                     + OrderContract.OrderEntry.ID + " text" + ");";

    public static final String DROP_QUERY = "drop table if exists " + OrderContract.OrderEntry.TABLE_NAME + ";";

    public OrderDbHelper(Context context){
        super(context,DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL(DROP_QUERY);
    }

    public void putInfromation(String completed, String brand, String color, String model, String defect, String id , SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderContract.OrderEntry.COMPLETED,completed);
        contentValues.put(OrderContract.OrderEntry.BRAND,brand);
        contentValues.put(OrderContract.OrderEntry.MODEL,model);
        contentValues.put(OrderContract.OrderEntry.COLOR,color);
        contentValues.put(OrderContract.OrderEntry.DEFECTS,defect);
        contentValues.put(OrderContract.OrderEntry.ID,id);
        long l = db.insert(OrderContract.OrderEntry.TABLE_NAME,null,contentValues);


    }

    public Cursor getInformation(SQLiteDatabase db){
        String[] projections = {OrderContract.OrderEntry.COMPLETED , OrderContract.OrderEntry.BRAND,OrderContract.OrderEntry.MODEL,OrderContract.OrderEntry.COLOR,OrderContract.OrderEntry.DEFECTS,OrderContract.OrderEntry.ID};

        Cursor cursor = db.query(OrderContract.OrderEntry.TABLE_NAME , projections , null,null,null,null,null);
        return cursor;
    }
}
