package com.example.logtracker;

import java.util.ArrayList;

public class flightLog {
    private static   ArrayList<Integer> dateSelected,timeSelected ;
    private static String aircraftType,typeofFlight, lightCond, flightRules, dutyonBoard, aircraftID,arrival,destination ;
    private static Integer landingsInput;
    public flightLog(){
        dateSelected= new ArrayList<>();
        timeSelected =new ArrayList<>();
        aircraftType= "";
        typeofFlight= "";
        lightCond= "";
        flightRules= "";
        dutyonBoard= "";
        aircraftID = "";
        arrival="";
        destination="";
        landingsInput=0;
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
    public static void setArrival(String Arrival) {
        flightLog.arrival = Arrival;
    }
    public static void setDestination(String Destination) {
        flightLog.destination = Destination;
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
                date="-"+date;
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
    public static String getTypeofFlight(){return typeofFlight;}
    public static String getArrival() {
        return arrival;
    }
    public static String getDestination() {
        return destination;
    }


    //check is all mandatory flight fields are inserted
    public static boolean fieldCompletion(){
        if (aircraftType.equals("") ||  typeofFlight.equals("") || lightCond.equals("")
                || flightRules.equals("") || dutyonBoard.equals("") || aircraftID.equals("")
                || arrival.equals("") || destination.equals("")
                || dateSelected.isEmpty() || timeSelected.isEmpty()){
            return false;
        }
        else{
            System.out.println(aircraftType+typeofFlight+lightCond+flightRules+dutyonBoard+aircraftID+aircraftID+dateSelected+timeSelected);
            return true;
        }
    }

}
