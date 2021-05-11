package main.java.ticketGenerator.core;

import java.util.ArrayList;

public class Slot {


    public static Vehicle[] parkingSlots;
    private Integer floorNumber;
    private Integer capacity;
    private Integer spaceOnEachFloor;


    public Slot(Integer floorNumber, Integer spaceOnEachFloor) {
        this.floorNumber = floorNumber;
        this.capacity = capacity;
        this.spaceOnEachFloor = spaceOnEachFloor;
        Slot.parkingSlots = new Vehicle[floorNumber * spaceOnEachFloor];
    }
    public Vehicle[] getParkingSlots() {
        return parkingSlots;
    }
    public void setParkingSlots(Vehicle[] parkingSlots) {
        Slot.parkingSlots = parkingSlots;
    }
    public Integer getFloorNumber() {
        return floorNumber;
    }
    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getSpaceOnEachFloor() {
        return spaceOnEachFloor;
    }
    public void setSpaceOnEachFloor(Integer spaceOnEachFloor) {
        this.spaceOnEachFloor = spaceOnEachFloor;
    }



}