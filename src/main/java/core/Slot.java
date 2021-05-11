package main.java.ticketGenerator.core;

import java.util.ArrayList;

public class Slot {

	
	
	    private Integer floorNumber;
	    private Integer slotNumber;
	    private boolean isSlotEmpty;
	    private Integer capacity;
	    public Slot(int floorNumber, int capacity) {
	        this.floorNumber = floorNumber;
	        this.capacity = capacity;
	    }
	    public Integer getFloorNumber() {
	        return floorNumber;
	    }
	    public void setFloorNumber(Integer floorNumber) {
	        this.floorNumber = floorNumber;
	    }
	    public Integer getSlotNumber() {
	        return slotNumber;
	    }
	    public void setSlotNumber(Integer slotNumber) {
	        this.slotNumber = slotNumber;
	    }
	    public boolean isSlotEmpty() {
	        return isSlotEmpty;
	    }
	    public void setSlotEmpty(boolean isSlotEmpty) {
	        this.isSlotEmpty = isSlotEmpty;
	    }
	    public Integer getCapacity() {
	        return capacity;
	    }
	    public void setCapacity(Integer capacity) {
	        this.capacity = capacity;
	    }
	    public static Vehicle[] getParkingSlot() {
	        return parkingSlot;
	    }
	    public static void setParkingSlot(Vehicle[] parkingSlot) {
	        Slot.parkingSlot = parkingSlot;
	    }
	    public Slot(Integer floorNumber, Integer slotNumber, boolean isSlotEmpty, Integer capacity) {
	        super();
	        this.floorNumber = floorNumber;
	        this.slotNumber = slotNumber;
	        this.isSlotEmpty = isSlotEmpty;
	        this.capacity = capacity;
	    }
	    public static Vehicle[] parkingSlot;
	    @Override
	    public String toString() {
	        return "Slot [floorNumber=" + floorNumber + ", slotNumber=" + slotNumber + ", isSlotEmpty=" + isSlotEmpty +
	            ", capacity=" + capacity + "]";
	    }
	}

