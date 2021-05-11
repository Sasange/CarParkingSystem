package main.java.ticketGenerator;

import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import main.java.ticketGenerator.clients.*;

public class Main {
    public static void main(String[] args) {
        Option op = new Option();
        ApplicationConfig appconfig = new ApplicationConfig();



        BaseClient client = new InMemoryClient();
        String clientName = appconfig.getClient();



        while (true) {
            try {
                switch (op.menu()) {
                    case 1: //ticket generation
                        client.vehicleEntry();
                        break;
                    case 2: //exit
                        client.vehicleExit();
                        break;
                    case 3: //search by category
                        switch (op.searchByCategory()) {
                            case 5:
                                client.registrationNumberBycolor();
                                break;
                            case 6:
                                client.slotNumberByColor();
                                break;
                            case 7: //Slot numbers of cars of particular color	
                                client.slotNumberByRegistrationNumber();
                                break;

                            case 10:
                                System.exit(0);
                            default:
                                System.out.println("Invalid input");
                                break;

                        }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}