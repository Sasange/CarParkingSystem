package main.java.ticketGenerator.utility;


import java.util.Scanner;


public class Option {





    public int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Enter your choice");
        System.out.println("1. Entry");
        System.out.println("2. Exit");
        System.out.println("3. Search by Category");
        System.out.println("4. Exit");
        int choice = sc.nextInt();
        return choice;

    }

    public int searchByCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Enter your choice");
        System.out.println("5. Enter Registration Number of cars to search cars of a particular color");
        System.out.println("6. Enter Slot Number of cars to search cars of a  particular color");
        System.out.println("7. Enter Slot Number to search for Registration number");
        int choice1 = sc.nextInt();
        return choice1;
    }
}