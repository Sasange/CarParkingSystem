	package main.java.ticketGenerator.clients;

	import java.util.Scanner;
	import main.java.ticketGenerator.core.*;
	import main.java.ticketGenerator.utility.*;
	import java.util.ArrayList;
	import java.util.Collections;
	import main.java.ticketGenerator.clients.*;

	public class InMemoryClient extends BaseClient {

	    Scanner sc = new Scanner(System.in);
	    ApplicationConfig appConfig = new ApplicationConfig();
	    Slot slot = new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")), Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

	    public void vehicleEntry() {
	        boolean flag = false;
	        Vehicle vehicle = availableSlot();
	        if (vehicle != null) {
	            for (int i = 0; i < slot.getParkingSlots().length; i++) {
	                if (slot.getParkingSlots()[i] == null) {
	                    slot.getParkingSlots()[i] = vehicle;


	                    System.out.println(slot.getParkingSlots()[i]);
	                    System.out.println("Vehicle slot number :" + (i + 1));
	                    flag = true;
	                    break;
	                }
	            }
	            if (!flag) {
	                System.out.println("Sorry..Parking is full");
	            }
	        }
	    }
	    public void vehicleExit() {
	        boolean vehicleFound = false;
	        System.out.print("Enter vehicle Registration Number :");
	        String regNo = sc.nextLine();
	        for (int i = 0; i < slot.parkingSlots.length; i++) {
	            if (slot.parkingSlots[i] != null && slot.parkingSlots[i].getRegistrationNumber().equalsIgnoreCase(regNo)) {
	                slot.parkingSlots[i] = null;
	                System.out.println("Slot Number " + (i + 1) + " is now Available ");
	                vehicleFound = true;
	                break;
	            }
	        }
	        if (!vehicleFound) {
	            System.out.println("Vehicle Not Found !!!");
	        }
	    }
	    public void slotNumberByRegistrationNumber() {
	        boolean vehicleFound = false;
	        System.out.print("Enter Registration no to search vehicle Slot :");
	        String regNo = sc.nextLine();
	        for (int i = 0; i < slot.getParkingSlots().length; i++) {
	            if (Slot.parkingSlots[i] != null && Slot.parkingSlots[i].getRegistrationNumber().equalsIgnoreCase(regNo)) {
	                System.out.println("Slot Number is " + (i + 1));
	                vehicleFound = true;
	                break;
	            }
	        }
	        if (!vehicleFound) {
	            System.out.println("Vehicle Not Found !!!");
	        }
	    }

	    public void registrationNumberBycolor() {
	        boolean vehicleFound = false;
	        System.out.print("Enter Colour to find Registration numbers of all cars of a particular color :");
	        String colour = sc.nextLine();
	        for (int i = 0; i < Slot.parkingSlots.length; i++) {
	            if (Slot.parkingSlots[i] != null && Slot.parkingSlots[i].getColor().equalsIgnoreCase(colour)) {
	                System.out.println("Registration Number : " + Slot.parkingSlots[i].getRegistrationNumber());
	                vehicleFound = true;
	            }
	        }
	        if (!vehicleFound) {
	            System.out.println("No Vehicle Available with " + colour + " Colour !!!");
	        }
	    }
	    public void slotNumberByColor() {
	        boolean vehicleFound = false;
	        System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked: ");
	        String colour = sc.nextLine();
	        for (int i = 0; i < Slot.parkingSlots.length; i++) {
	            if (Slot.parkingSlots[i] != null && Slot.parkingSlots[i].getColor().equalsIgnoreCase(colour)) {
	                System.out.println("Slot Number : " + (i + 1));
	                vehicleFound = true;
	            }
	        }
	        if (!vehicleFound) {
	            System.out.println("No Vehicle Available with " + colour + " Colour !!!");
	        }
	    }
	    public Vehicle availableSlot() {
	        Vehicle vehicle = registerVehicle();
	        if (vehicle != null) {
	            for (int i = 0; i < slot.getParkingSlots().length; i++) {
	                if (slot.getParkingSlots()[i] != null && slot.getParkingSlots()[i].getRegistrationNumber().equalsIgnoreCase(vehicle.getRegistrationNumber())) {
	                    System.out.println("Duplicate Vehicle Info");
	                    return null;
	                } else {
	                    return vehicle;
	                }
	            }

	        }
	        System.out.println("Invalid Vehicle Info");
	        return null;
	    }

	}