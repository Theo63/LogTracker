package com.example.logtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class LogtrackerDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flightsDB.db";
    public static final String TABLE_FLIGHTS = "flights";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_AIRCRAFTTYPE = "aircraftType";
    public static final String COLUMN_AIRCRAFTID = "aircraftID";
    public static final String COLUMN_ARRIVAL = "arrival";
    public static final String COLUMN_DESTINATION = "destination";
//    public static final String COLUMN_ARRIVAL = "arrival"; //arrival
//    public static final String COLUMN_DESTINATION = "destination"; //destination
    public static final String COLUMN_LANDINGS = "landings";
    public static final String COLUMN_TYPEOFFLIGHT = "typeOfFlight";
    public static final String COLUMN_FLIGHTDURATION = "flightDuration";
    public static final String COLUMN_LIGHTCONDITIONS = "lightConditions";
    public static final String COLUMN_FLIGHTRULES = "flightRules";
    public static final String COLUMN_DUTYONBOARD = "dutyOnBoard";
    public File filePath = new File(Environment.getExternalStorageDirectory().toString()+"/"+"flights.xls");




    public LogtrackerDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FLIGHTS_TABLE = "CREATE TABLE " +
                TABLE_FLIGHTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_AIRCRAFTTYPE + " TEXT," +
                COLUMN_AIRCRAFTID + " TEXT," +
                COLUMN_ARRIVAL + " TEXT," +
                COLUMN_DESTINATION + " TEXT," +
                COLUMN_LANDINGS + " INTEGER," +
                COLUMN_TYPEOFFLIGHT + " TEXT," +
                COLUMN_FLIGHTDURATION + " TEXT," +
                COLUMN_LIGHTCONDITIONS + " TEXT," +
                COLUMN_FLIGHTRULES + " TEXT," +
                COLUMN_DUTYONBOARD + " TEXT" +
                ")";
        db.execSQL(CREATE_FLIGHTS_TABLE);
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
        contentValues.put(COLUMN_ARRIVAL,flightLog.getArrival());
        contentValues.put(COLUMN_DESTINATION,flightLog.getDestination());
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

    public void resetDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }


//set title to excel
    private void firstRow(HSSFCell hssfCell,HSSFRow hssfRow,HSSFSheet hssfSheet) {
        hssfRow = hssfSheet.createRow(0);
        hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("No.");
        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("Date");
        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("Type");
        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("AirCraft No");
        hssfCell = hssfRow.createCell(4);
        hssfCell.setCellValue("From");
        hssfCell = hssfRow.createCell(5);
        hssfCell.setCellValue("To");
        hssfCell = hssfRow.createCell(6);
        hssfCell.setCellValue("Landings");
        hssfCell = hssfRow.createCell(7);
        hssfCell.setCellValue("Mission");
        hssfCell = hssfRow.createCell(8);
        hssfCell.setCellValue("Hours");
        hssfCell = hssfRow.createCell(9);
        hssfCell.setCellValue("Light Conts");
        hssfCell = hssfRow.createCell(10);
        hssfCell.setCellValue("Flight Type");
    }



    public String writeFlightsToExcel() {

        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        HSSFCell hssfCell = null;
        HSSFRow hssfRow = null;

        //initial
        firstRow(hssfCell,hssfRow,hssfSheet);

        if (cursor.moveToFirst()) {
            int i = 1;
            do {
                hssfRow = hssfSheet.createRow(i);

                for (int j = 0; j < 11; j++) {
                    hssfCell = hssfRow.createCell(j);
                    hssfCell.setCellValue(cursor.getString(j));
                }
                i++;
            } while (cursor.moveToNext());

        }
        try {
            System.out.println("this is"+!filePath.exists());
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            return e.getMessage();
        }

        return filePath.getAbsolutePath();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst(); product.setID(Integer.parseInt(cursor.getString(0))); product.setProductName(cursor.getString(1)); product.setQuantity(Integer.parseInt(cursor.getString(2))); cursor.close();
//        } else {
//            product = null;
//        }
//        db.close(); return product;
    }
//    Delete RegistrationActivity method
}
