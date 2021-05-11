
package main.java.ticketGenerator.clients;

import main.java.ticketGenerator.core.*;
import java.util.Scanner;
import main.java.ticketGenerator.utility.*;
import java.sql.*;
import main.java.ticketGenerator.clients.*;

public class MySQLClient extends BaseClient {
    Scanner sc = new Scanner(System.in);
    ApplicationConfig appConfig = new ApplicationConfig();

    Slot slot = new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")), Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    public void vehicleEntry() {
        Vehicle vehicle = registerVehicle();
        if (vehicle != null) {
            if (isSlotAvailable()) {
                try {
                    String query1 = "SELECT registrationNumber,color,slotNumber FROM VEHICLE";
                    ResultSet rs = appConfig.getStatement().executeQuery(query1);
                    while (rs.next()) {
                        if (rs.getString(1) == null) {
                            String query = "UPDATE Vehicle SET registrationNumber=?,color=?" +
                                "WHERE slot=?";
                            PreparedStatement statement1 = appConfig.getConnection().prepareStatement(query);
                            statement1.setString(1, vehicle.getRegistrationNumber());
                            statement1.setString(2, vehicle.getColor());
                            statement1.setInt(3, rs.getInt(3));
                            statement1.executeUpdate();
                            System.out.println();
                            System.out.println("Data inserted successfully");
                            generateTicket(vehicle.getRegistrationNumber());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println();
                System.out.println("Sorry..Parking is full");
            }
        } else {
            System.out.println("Invalid Input");
        }

    }
    public void generateTicket(String getRegistrationNumber) {
        try {
            String query2 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query2);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (getRegistrationNumber.equalsIgnoreCase(registrationNumber)) {
                    System.out.println("Registration Number:" + rs.getString(1));
                    System.out.println("Vehicle Color :" + rs.getString(2));
                    System.out.println("Slot Number :" + rs.getInt(3));
                    break;
                }
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }

    }

    public void vehicleExit() {
        System.out.print("Enter Registration Number :");
        String regNo = sc.nextLine();
        if (isVehicleAvailable(regNo)) {
            String query3 = "UPDATE Vehicle SET registrationNumber=?,color=? WHERE registrationNumber=?";
            try {
                PreparedStatement statement = appConfig.getConnection().prepareStatement(query3);
                statement.setString(1, null);
                statement.setString(2, null);
                statement.setString(3, regNo);
                System.out.println("Vehicle exited");
                statement.executeUpdate();
            } catch (SQLException throwable) {
                System.out.println(throwable.getMessage());
            }
        } else {
            System.out.println("No such vehicle available!! ");
        }
    }
    public void slotNumberByRegistrationNumber() {
        boolean vehicleFound = false;
        System.out.print("Enter Registration Number to search vehicle Slot :");
        String regNo = sc.nextLine();
        try {
            String query4 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query4);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (registrationNumber != null && registrationNumber.equalsIgnoreCase(regNo)) {
                    vehicleFound = true;
                    System.out.println("Slot Number for " + regNo + " is " + rs.getInt(3));
                    break;
                }
            }
            if (!vehicleFound) {
                System.err.println("Vehicle not available");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void registrationNumberBycolor() {
        boolean vehicleFound = false;
        System.out.print("Enter Color to search all vehicles Registration Number :");
        String vehicleColor = sc.nextLine();
        try {
            String query5 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query5);
            while (rs.next()) {
                String color = rs.getString(2);
                if (color != null && color.equalsIgnoreCase(vehicleColor)) {
                    vehicleFound = true;
                    System.out.println("Registration Number : " + rs.getString(1));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle of " + vehicleColor + " Color is available");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void slotNumberByColor() {
        boolean vehicleFound = false;
        System.out.print("Enter Color to find slot numbers of all Vehicle of a particular color is parked :");
        String color = sc.nextLine();
        try {
            String query6 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query6);
            while (rs.next()) {
                String vehColor = rs.getString(2);
                if (color != null && color.equalsIgnoreCase(vehColor)) {
                    vehicleFound = true;
                    System.out.println("Slot Number : " + rs.getInt(3));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle is available with " + color + " Color");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public boolean isVehicleAvailable(String regNo) {
        try {
            String query7 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query7);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (regNo.equalsIgnoreCase(registrationNumber)) {
                    return true;
                }
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return false;
    }
    public boolean isSlotAvailable() {
        try {
            String query8 = "SELECT * FROM Vehicle";
            ResultSet rs = appConfig.getStatement().executeQuery(query8);
            int slotNumber = -1;
            while (rs.next()) {
                if (rs.getString(1) == null && rs.getInt(3) != 0) {
                    slotNumber = rs.getInt(3);
                    break;
                }
            }
            if (slotNumber != -1 && slotNumber <= slot.getCapacity()) {
                return true;
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return false;
    }
    public void createTable() {
        try {
            String qry1 = "CREATE TABLE IF NOT EXISTS Vehicle(" +
                "registrationNumber CHAR(10) UNIQUE," +
                "color VARCHAR(10)," +
                "slotNumber int PRIMARY KEY)";

            appConfig.getStatement().execute(qry1);

            for (int i = 1; i <= slot.getCapacity(); i++) {
                String qry2 = "INSERT INTO Vehicle(registrationNumber,color,slotNumber) VALUES(?,?,?)";
                PreparedStatement statement = appConfig.getConnection().prepareStatement(qry2);
                statement.setString(1, null);
                statement.setString(2, null);
                statement.setInt(3, i);
                statement.executeUpdate();
            }
        } catch (SQLException throwable) {

        }

    }
}