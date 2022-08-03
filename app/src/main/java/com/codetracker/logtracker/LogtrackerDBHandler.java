package com.codetracker.logtracker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

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
    public static final String COLUMN_LANDINGS = "landings";
    public static final String COLUMN_TYPEOFFLIGHT = "typeOfFlight";
    public static final String COLUMN_FLIGHTDURATION = "flightDuration";
    public static final String COLUMN_LIGHTCONDITIONS = "lightConditions";
    public static final String COLUMN_FLIGHTRULES = "flightRules";
    public static final String COLUMN_DUTYONBOARD = "dutyOnBoard";

    public static final String TABLE_HOURS= "hours";
    public File filePath = new File(Environment.getDownloadCacheDirectory().toString()+"/"+"flights.xls");




    public LogtrackerDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FLIGHTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
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

        String CREATE_HOURS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_HOURS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_AIRCRAFTTYPE + " TEXT UNIQUE," +
                COLUMN_FLIGHTDURATION + " TEXT" +
                ")";
        db.execSQL(CREATE_HOURS_TABLE);

       //storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



    }

    //Αναβάθμιση ΒΔ: εδώ τη διαγραφώ και τη ξαναδημιουργώ ίδια
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db); }

    public boolean addFlight() {
        SQLiteDatabase db = this.getWritableDatabase(); //opens the database
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
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }


    }

    public void deleteFlight(String index){
        SQLiteDatabase db = this.getWritableDatabase(); //opens the database
        String DELETE_FLIGHT = "DELETE FROM "+TABLE_FLIGHTS+ " WHERE "+ COLUMN_ID +" = "+index;
        System.out.println(DELETE_FLIGHT);
        db.execSQL(DELETE_FLIGHT);
    }


    public void resetDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }

    public void resetStartingHoursTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOURS);
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
        hssfCell = hssfRow.createCell(11);
        hssfCell.setCellValue("Duty on Board");
    }


//
//    public FileChannel writeFlightsToExcel() {
//
//        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
//        HSSFCell hssfCell = null;
//        HSSFRow hssfRow = null;
//
//        //initial
//        firstRow(hssfCell,hssfRow,hssfSheet);
//
//        if (cursor.moveToFirst()) {
//            int i = 1;
//            do {
//                hssfRow = hssfSheet.createRow(i);
//
//                for (int j = 0; j < 12; j++) {
//                    hssfCell = hssfRow.createCell(j);
//                    hssfCell.setCellValue(cursor.getString(j));
//                }
//                i++;
//            } while (cursor.moveToNext());
//
//        }
//        try {
//            System.out.println("this is"+!filePath.exists());
////            if (!filePath.exists()) {
////                filePath.createNewFile();
////            }
//            File excel = new File("flights.xls");
//            excel.createNewFile();
//            FileOutputStream fileOutputStream = new FileOutputStream();
//            hssfWorkbook.write(fileOutputStream);
////            if (fileOutputStream != null) {
////                fileOutputStream.flush();
////                fileOutputStream.close();
////            }
//            System.out.println(fileOutputStream.getChannel().size());
//            FileChannel src =  fileOutputStream.getChannel();
//
//            return src;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        db.close();//close database
//        return null;
//
//
//    }



    public ArrayList<ArrayList> getFlightSearch(HashMap searchData){
        // key values are  : fromDate, untilDate , aircraftType, typeOfFlight, dutyOnBoard, aircraftId, fromLocation, toLocation
        ArrayList<ArrayList> finalData = new ArrayList<>();
        ArrayList<String> flightData = new ArrayList<>();
        String and="";
        boolean allQuery = true; //if we pass no query attributes
        //String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
        String query= "SELECT * FROM " + TABLE_FLIGHTS + " WHERE " ;
        if (searchData.containsKey("fromDate")){
            String temp = (String) searchData.get("fromDate");
            query=query+"date>='"+temp+"'";
            and=" AND ";
            allQuery = false;
        }
        if (searchData.containsKey("untilDate")){
            String temp = (String) searchData.get("untilDate");
            query=query+and+"date<='"+temp+"'";
            and=" AND ";
            allQuery = false;
        }
        if (searchData.containsKey("aircraftType")){
            String temp = (String) searchData.get("aircraftType");
            query=query+and+"aircraftType LIKE '%"+temp+"%'";
            and=" AND ";
            allQuery = false;
        }
        if (searchData.containsKey("typeOfFlight")){
            String temp = (String) searchData.get("typeOfFlight");
            query=query+and+"typeOfFlight LIKE '%"+temp+"%'";
            and=" AND ";
            allQuery = false;
        }
        if (searchData.containsKey("dutyOnBoard")){
            String temp = (String) searchData.get("dutyOnBoard");
            query=query+and+"dutyOnBoard LIKE '%"+temp+"%'";
            and=" AND ";
            allQuery = false;
        }
        if (searchData.containsKey("aircraftId")){
            String temp = (String) searchData.get("aircraftId");
            if (!searchData.get("aircraftId").equals("")) // edittext filed return "" when nothing is picked
            {
                query=query+and+"aircraftID LIKE '%"+temp+"%'";
                and=" AND ";
                allQuery = false;
            }
        }
        if (searchData.containsKey("fromLocation")){
            String temp = (String) searchData.get("fromLocation");
            if (!searchData.get("fromLocation").equals("")){
                query=query+and+"arrival LIKE '%"+temp+"%'";
                and=" AND ";
                allQuery = false;
            }
        }
        if (searchData.containsKey("toLocation")){
            String temp = (String) searchData.get("toLocation");
            if (!searchData.get("toLocation").equals("")){
                query=query+and+"destination LIKE '%"+temp+"%'";
                and=" AND ";
                allQuery = false;
            }
        }
        if (allQuery){ //if everything is blank we get the user everything
            query = "SELECT * FROM " + TABLE_FLIGHTS + " ORDER BY "+COLUMN_DATE+" ASC "+";";
        }
        else
            query=query+" ORDER BY "+COLUMN_DATE+" ASC "+";"; //we add last ; to query
        //System.out.println(query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int sumHours = 0;
        if (cursor.moveToFirst()) {
            int i = 1;
            do {
                for (int j = 0; j < 12; j++) {
                    flightData.add(cursor.getString(j));
                    if (j == 8){// get sum of hours
                        sumHours += getDurationMinutes(cursor.getString(j));
                    }
                }
                i++;
                finalData.add(flightData);
                flightData = new ArrayList<>();
            } while (cursor.moveToNext());

        }
        db.close();//close database
        String hoursSum = Integer.toString(sumHours);  ////
        flightData = new ArrayList<>();
        flightData.add(hoursSum);//last array list total hours
        finalData.add(flightData);
        return finalData;

    }

    public boolean addStartingHours(String type , String duration ) {
        if (type == null || duration.equals("")){
            return false;
        }
        System.out.println("Type:"+type);
        SQLiteDatabase db = this.getWritableDatabase(); //opens the database
        String query= "SELECT "+ COLUMN_AIRCRAFTTYPE +" FROM " + TABLE_HOURS ;
        Cursor cursor = db.rawQuery(query, null);
        System.out.println(cursor.getColumnCount());

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AIRCRAFTTYPE,type);
        contentValues.put(COLUMN_FLIGHTDURATION,duration);
        long result = db.insert(TABLE_HOURS,null,contentValues);
        if (result == -1){
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }


    }

    public HashMap<String,Integer> getTotalHours(){
        SQLiteDatabase db = this.getWritableDatabase(); //opens the database
        String query= "SELECT * FROM " + TABLE_HOURS ;
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, Integer> types = new HashMap<>();
        if (cursor.moveToFirst() && cursor!=null && cursor.getCount()>0) {
            do {
                types.put(cursor.getString(1),Integer.valueOf(cursor.getString(2)));
                //System.out.println(types);
            } while (cursor.moveToNext());
        }
        for(String key: types.keySet()) {
            String query2 = "SELECT "+ COLUMN_FLIGHTDURATION + " FROM " + TABLE_FLIGHTS +" WHERE " + COLUMN_AIRCRAFTTYPE+ " = '" + key +"';";
            Cursor cursor2 = db.rawQuery(query2, null);
            System.out.println(query2);
            int sum =0;
            if (cursor2.moveToFirst() && cursor!=null && cursor.getCount()>0) {
                do {
                    sum += getDurationMinutes(cursor2.getString(0));
                } while (cursor2.moveToNext());
            }
            types.put(key,types.get(key)*60+sum);
            //System.out.println("type: "+key+" total hours "+ types.get(key)/60+" Ηours : "+types.get(key)%60+" Μinutes ");


        }
        return types;
        //Cursor cursor = db.rawQuery(query, null);
    }


    public boolean exportDatabase(){
        try
        {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            String currentDBPath = "//data//com.codetracker.logtracker//databases//flightsDB.db";
            String backupDBPath = "flightsDB.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            return true;

        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }





    private int getDurationMinutes(String time){
        String[] durationOfSelection  = time.split(":");
        return  (Integer.parseInt(durationOfSelection[0])*60) + Integer.parseInt(durationOfSelection[1]);
        //old version - replace function with this
        //String[] durationOfSelection  = cursor.getString(j).split(":");
        //sumHours += ((Integer.parseInt(durationOfSelection[0])*60) + Integer.parseInt(durationOfSelection[1]));

    }


}