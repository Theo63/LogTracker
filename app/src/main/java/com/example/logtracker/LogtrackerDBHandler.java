package com.example.logtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LogtrackerDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flightsDB.db";
    public static final String TABLE_FLIGHTS = "flights";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_AIRCRAFTTYPE = "aircraftType";
    public static final String COLUMN_AIRCRAFTID = "aircraftID";
//    public static final String COLUMN_ARRIVAL = "arrival"; //arrival
//    public static final String COLUMN_DESTINATION = "destination"; //destination
    public static final String COLUMN_LANDINGS = "landings";
    public static final String COLUMN_TYPEOFFLIGHT = "typeOfFlight";
    public static final String COLUMN_FLIGHTDURATION = "flightDuration";
    public static final String COLUMN_LIGHTCONDITIONS = "lightConditions";
    public static final String COLUMN_FLIGHTRULES = "flightRules";
    public static final String COLUMN_DUTYONBOARD = "dutyOnBoard";


    public LogtrackerDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_FLIGHTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_AIRCRAFTTYPE + " TEXT," +
                COLUMN_AIRCRAFTID + " TEXT," +
                COLUMN_LANDINGS + " INTEGER," +
                COLUMN_TYPEOFFLIGHT + " TEXT," +
                COLUMN_FLIGHTDURATION + " TEXT," +
                COLUMN_LIGHTCONDITIONS + " TEXT," +
                COLUMN_FLIGHTRULES + " TEXT," +
                COLUMN_DUTYONBOARD + " TEXT" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    //Αναβάθμιση ΒΔ: εδώ τη διαγραφώ και τη ξαναδημιουργώ ίδια
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db); }

    public boolean addFlight() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE,flightLog.getDateSelected());
        contentValues.put(COLUMN_AIRCRAFTTYPE,flightLog.getAircraftType());
        contentValues.put(COLUMN_AIRCRAFTID,flightLog.getAircraftID());
        contentValues.put(COLUMN_LANDINGS,flightLog.getLandingsInput());
        contentValues.put(COLUMN_TYPEOFFLIGHT,flightLog.getTypeofFlight());
        contentValues.put(COLUMN_FLIGHTDURATION,flightLog.getTimeSelected());
        contentValues.put(COLUMN_LIGHTCONDITIONS,flightLog.getLightCond());
        contentValues.put(COLUMN_FLIGHTRULES,flightLog.getFlightRules());
        contentValues.put(COLUMN_DUTYONBOARD,flightLog.getDutyonBoard());
        long result = db.insert(TABLE_FLIGHTS,null,contentValues);

        if (result == -1){
            return false;
        }
        else
            return true;
//        db.close();
    }


//    public Product findProduct(String productname) {
//        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
//                COLUMN_PRODUCTNAME + " = '" + productname + "'"; SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Product product = new Product();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst(); product.setID(Integer.parseInt(cursor.getString(0))); product.setProductName(cursor.getString(1)); product.setQuantity(Integer.parseInt(cursor.getString(2))); cursor.close();
//        } else {
//            product = null;
//        }
//        db.close(); return product;
//    }
    //Delete registration method
}
