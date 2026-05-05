package org.example;

public class TicketManagementSystem {
    private int balance;
    private int totalCollected;

    public TicketManagementSystem() {
        balance = 0;
        totalCollected = 0;
    }

    public void insertMoney(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Inserted: " + amount);
        } else {
            System.out.println("Invalid amount!");
        }
    }

    public int getBalance() { return balance; }

    public void printTicket(String route, int quantity) {
        int price = 0;
        if (route.equals("CityA to CityB")) price = 125;
        else if (route.equals("CityA to CityC")) price = 150;
        else if (route.equals("CityA to CityD")) price = 100;
        else {
            System.out.println("Invalid route!");
            return;
        }

        int totalCost = price * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            totalCollected += totalCost;
            System.out.println("Printed " + quantity + " ticket(s) for " + route + " costing " + totalCost);
            giveChange();
        } else {
            System.out.println("Not enough balance! Please insert at least " + (totalCost - balance) + " more.");
        }
    }

    public void giveChange() {
        if (balance > 0) {
            System.out.println("Returning change: " + balance);
            balance = 0;
        }
    }

    public void showRoutes() {
        System.out.println("Available Routes and Prices:");
        System.out.println("CityA to CityB - 125");
        System.out.println("CityA to CityC - 150");
        System.out.println("CityA to CityD - 100");
    }

    public int getTotalCollected() { return totalCollected; }

    public static void main(String[] args) {
        TicketManagementSystem machine = new TicketManagementSystem();
        machine.showRoutes();
        machine.insertMoney(500);
        machine.printTicket("CityA to CityB", 4);
        machine.insertMoney(300);
        machine.printTicket("CityA to CityC", 2);
        System.out.println("Total collected: " + machine.getTotalCollected());
    }
}