
package main.java.ticketGenerator.clients;

import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import java.util.*;
import redis.clients.jedis.Jedis;

public class RedisClient extends BaseClient {
    Scanner sc = new Scanner(System.in);
    ApplicationConfig appConfig = new ApplicationConfig();
    Slot slot = new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")), Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        Vehicle vehicle = registerVehicle();
        boolean isSlotAvailable = false;
        if (vehicle != null && !duplicateVehicle(vehicle)) {
            for (int i = 1; i <= slot.getCapacity(); i++) {
                String index = String.valueOf(i);
                Map < String, String > vehicleDetails = new HashMap < > ();
                vehicleDetails.put("registrationNumber", vehicle.getRegistrationNumber().toUpperCase());
                vehicleDetails.put("color", vehicle.getColor().toUpperCase());
                if (!appConfig.getJedis().hexists(index, "registrationNumber")) {
                    appConfig.getJedis().hmset(index, vehicleDetails);
                    System.out.println("Record inserted successfully");
                    generateTicket(index);
                    isSlotAvailable = true;
                    break;
                }
            }
            if (!isSlotAvailable) {
                System.out.println("So Parking is full");
            }
        } else {
            System.out.println("Incorrect details..No such vehicle found");
        }
    }
    @Override
    public void vehicleExit() {
        System.out.println("Enter Registration Number");
        String regNo = sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index = String.valueOf(i);
            if (appConfig.getJedis().hget(index, "registraionNumber").equalsIgnoreCase(regNo)) {
                appConfig.getJedis().hdel(index, "registrationNumber");
                appConfig.getJedis().hdel(index, "color");
                System.out.println("Slot " + appConfig.getJedis().hget(index, "Slot") + "is available");
                break;
            }
        }


    }
    public void generateTicket(String index) {
        System.out.println("Registration Number: " + appConfig.getJedis().hget(index, "registrationNumber"));
        System.out.println("Color: " + appConfig.getJedis().hget(index, "color"));
        System.out.println("Slot Number: " + appConfig.getJedis().hget(index, "Slot"));
    }

    public boolean duplicateVehicle(Vehicle vehicle) {
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index = String.valueOf(i);
            if (appConfig.getJedis().hexists(index, "registrationNumber") && appConfig.getJedis().hget(index, "registrationNumber").equalsIgnoreCase(vehicle.getRegistrationNumber())) {
                return true;
            }
        }
        return false;

    }


    @Override
    public void slotNumberByRegistrationNumber() {
        boolean isVehicleAvailable = false;
        System.out.println("Enter Registration Number");
        String regNo = sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index = String.valueOf(i);
            if (appConfig.getJedis().hget(index, "registrationNumber").equalsIgnoreCase(regNo)) {
                System.out.println("Vehicle color: " + appConfig.getJedis().hget(index, "color"));
                System.out.println("Slot Number: " + appConfig.getJedis().hget(index, "Slot"));
                isVehicleAvailable = true;
                break;

            }
        }
        if (!isVehicleAvailable) {
            System.out.println("Vehiclen with registration Number" + regNo + "not found");
        }


    }

    @Override
    public void registrationNumberBycolor() {
        boolean isVehicleAvailable = false;
        System.out.println("Enter Vehicle color");
        String color = sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index = String.valueOf(i);
            if (appConfig.getJedis().hget(index, "color").equalsIgnoreCase(color)) {
                System.out.println("Registration Number: " + appConfig.getJedis().hget(index, "registrationNumber"));
                isVehicleAvailable = true;
                break;

            }
        }
        if (!isVehicleAvailable) {
            System.out.println("No Vehicle of " + color + "color found");
        }


    }



    @Override
    public void slotNumberByColor() {
        boolean isVehicleAvailable = false;
        System.out.println("Enter Vehicle color");
        String color = sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index = String.valueOf(i);
            if (appConfig.getJedis().hget(index, "color").equalsIgnoreCase(color)) {
                System.out.println("Slot Number: " + appConfig.getJedis().hget(index, "Slot"));
                isVehicleAvailable = true;
                break;

            }
        }
        if (!isVehicleAvailable) {
            System.out.println("No Vehicle of " + color + "color found");
        }

    }
    public void createDictionary() {
        Jedis jedis = appConfig.getJedis();
        Map < String, String > details = new HashMap < > ();
        for (int i = 0; i <= slot.getCapacity(); i++) {
            details.put("Slot", String.valueOf(i));
            jedis.hmset(String.valueOf(i), details);
        }
    }

}