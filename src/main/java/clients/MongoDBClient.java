
package main.java.ticketGenerator.clients;

import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;


public class MongoDBClient extends BaseClient {
    Scanner sc = new Scanner(System.in);
    ApplicationConfig appConfig = new ApplicationConfig();
    Slot slot = new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")), Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        boolean flag = false;
        Vehicle vehicle = registerVehicle();
        if (vehicle != null && !incorrectInfo(vehicle)) {
            appConfig.getUpdateFields().append("registrationNumber", vehicle.getRegistrationNumber().toUpperCase());

            appConfig.getUpdateFields().append("color", vehicle.getColor().toUpperCase());
            appConfig.getSetQuery().append("$set", appConfig.getUpdateFields());
            appConfig.getCollection().updateOne(Filters.eq("registrationNumber", null), appConfig.getSetQuery());
            generateTicket(vehicle.getRegistrationNumber().toUpperCase());
            flag = true;
        }
        if (!flag) {
            System.out.println("Invalid Input..Retry");
        }
    }

    public boolean incorrectInfo(Vehicle vehicle) {
        Document vehicleDetails = appConfig.getCollection().find(new Document("registrationNumber", vehicle.getRegistrationNumber())).first();
        return vehicleDetails != null;
    }
    public void generateTicket(String registrationNumber) {
        Document vehicleDetails = appConfig.getCollection().find(new Document("registrationNumber", registrationNumber)).first();
        System.out.println("Vehicle Registration Number: " + vehicleDetails.get("registrationNumber"));
        System.out.println("Vehicle Color: " + vehicleDetails.get("color"));
        System.out.println("Vehicle Slot Number: " + vehicleDetails.get("_id"));
    }

    @Override
    public void vehicleExit() {
        System.out.println("Enter Vehicle Registration Number");
        String regNumber = sc.nextLine().toUpperCase();
        appConfig.getUpdateFields().append("registrationNumber", null);
        appConfig.getUpdateFields().append("color", null);
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", appConfig.getUpdateFields());
        appConfig.getCollection().updateOne(Filters.eq("registrationNumber", regNumber), setQuery);
        System.out.println("Slot is free now");


    }

    @Override
    public void slotNumberByRegistrationNumber() {
        boolean flag = false;
        System.out.println("Enter Vehicle Registration Number");
        String regNumber = sc.nextLine().toUpperCase();
        List < Document > vehicleDetails = appConfig.getCollection().find(Filters.eq("registrationNumber", regNumber)).into(new ArrayList < > ());
        for (Document details: vehicleDetails) {
            System.out.println("Vehicle Slot Number: " + details.get("_id"));
            flag = true;

        }
        if (!flag) {
            System.out.println("Wrong details.No such vehicle available");
        }

    }
    @Override
    public void registrationNumberBycolor() {
        boolean flag = false;
        System.out.println("Enter Color of Vehicle");
        String color = sc.nextLine().toUpperCase();
        List < Document > vehicleDetails = appConfig.getCollection().find(Filters.eq("color", color)).into(new ArrayList < > ());
        for (Document details: vehicleDetails) {
            System.out.println("Vehicle Registration Number: " + details.get("registrationNumber"));
            flag = true;

        }
        if (!flag) {
            System.out.println("Wrong details.No such vehicle available of this color");
        }

    }

    @Override
    public void slotNumberByColor() {
        boolean flag = false;
        System.out.println("Enter Color of Vehicle");
        String color = sc.nextLine().toUpperCase();
        List < Document > vehicleDetails = appConfig.getCollection().find(Filters.eq("color", color)).into(new ArrayList < > ());
        for (Document details: vehicleDetails) {
            System.out.println("Vehicle Slot Number: " + details.get("_id"));
            flag = true;

        }
        if (!flag) {
            System.out.println("Wrong details.No such vehicle available of this color");
        }




    }


    public void createCollection() {
        try {
            MongoCollection < Document > collection = appConfig.getDatabase().getCollection("Vehicle");
            Document document = new Document();
            for (int i = 1; i <= slot.getCapacity(); i++) {
                document.append("_id", i);
                document.append("registrationNumber", null);
                document.append("color", null);
                collection.insertOne(document);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}