
package main.java.ticketGenerator.clients;

import main.java.ticketGenerator.core.*;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class BaseClient {
    Scanner sc = new Scanner(System.in);


    public Vehicle registerVehicle() {
        Vehicle vehicle = new Vehicle();
        System.out.println("please enter your Vehicle Number");
        String registrationNumber = sc.nextLine();
        System.out.println("please enter color of your Vehicle");
        String color = sc.nextLine();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setColor(color);
        System.out.println("Vehicle added successfully");
        return vehicle;

    }

    public abstract void vehicleEntry();
    public abstract void vehicleExit();
    public abstract void slotNumberByRegistrationNumber();
    public abstract void registrationNumberBycolor();
    public abstract void slotNumberByColor();

}