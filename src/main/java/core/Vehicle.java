package main.java.ticketGenerator.core;


import java.util.ArrayList;
import java.util.Collections;


public class Vehicle {




    private String registrationNumber;
    private String color;

    //	    static ArrayList < Vehicle > parkingSlots = new ArrayList < > ();



    public Vehicle() {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }





    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return "vehicle [registrationNumber=" + registrationNumber + ", color=" + color + "]";
    }
}