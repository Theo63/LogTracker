package com.example.logtracker;

import java.util.ArrayList;

public class flightLog {
    private static   ArrayList<Integer> dateSelected,timeSelected = new ArrayList<>();
    private static String aircraftType,typeofFlight, lightCond, flightRules, dutyonBoard, aircraftID = "";
    private static Integer landingsInput = 0 ;
    public flightLog(){
    }

    //settters
    public static void setDutyonBoard(String dutyonBoard) {
        flightLog.dutyonBoard = dutyonBoard;
    }
    public static void setFlightRules(String flightRules) {
        flightLog.flightRules = flightRules;
    }
    public static void setLightCond(String lightCond) {
        flightLog.lightCond = lightCond;
    }
    public static void setTypeofFlight(String typeofFlight) {
        flightLog.typeofFlight = typeofFlight;
    }
    public static void setAircraftType(String aircraftType) {
        flightLog.aircraftType = aircraftType;
    }
    public static void setLandingsInput(Integer landingsInput) {
        flightLog.landingsInput = landingsInput;
    }
    public static void setTimeSelected(ArrayList<Integer> timeSelected) {
        flightLog.timeSelected = timeSelected;
    }
    public static void setAircraftID(String aircraftID) {
        flightLog.aircraftID = aircraftID;
    }
    public static void setDateSelected(ArrayList<Integer> dateSelected){
        flightLog.dateSelected=dateSelected;
    }
    public static void settimeSelected(ArrayList<Integer> timeSelected){
        flightLog.timeSelected=timeSelected;
    }

    //getters
    public static String getDateSelected() {
        String date = "";
        int counter=0;
        for (Integer item: dateSelected){
            date=item+date;
            if (counter<2){
                date=":"+date;
                counter++;
            }
        }
        return date;
    }
    public static String getTimeSelected() {
        String time = "";
        int counter=0;
        for (Integer item: timeSelected){
            time=time+item;
            if (counter<1){
                time=time+":";
                counter++;
            }
        }
        return time;

    }
    public static Integer getLandingsInput() {
        return landingsInput;
    }
    public static String getAircraftID() {
        return aircraftID;
    }
    public static String getAircraftType() {
        return aircraftType;
    }
    public static String getDutyonBoard() {
        return dutyonBoard;
    }
    public static String getFlightRules() {
        return flightRules;
    }
    public static String getLightCond() {
        return lightCond;
    }
    public static String getTypeofFlight() {
        return typeofFlight;
    }
}
