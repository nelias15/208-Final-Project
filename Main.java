package com.logicforge;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.ln); // or System.in
        boolean running = true;

        System.out.println("==========================================");
        System.0ut.println("  LogicForge — Explainable Decision Support");
        System.out.println("==========================================");

        while (running) {
            System.out.println("\nSelect a module to run:");
            System.out.println("1. Truth Table Generator (Devin's Module)");
            System.out.println("2. SecureSteps (Bruce's Module)");
            System.out.println("3. LaptopWise & Catalog (Ahmed's Module)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n[Running Truth Table Generator]...");
                    // TODO: Call Devin's TruthTableGenerator class here if ready
                    break;
                case "2":
                    System.out.println("\n[Running SecureSteps]...");
                    // TODO: Call Bruce's SecureSteps class here if ready
                    break;
                case "3":
                    System.out.println("\n[Running LaptopWise / Catalog]...");
                    // TODO: Call Ahmed's laptop module here if ready
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting LogicForge. Good luck with your presentation!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
        scanner.close();
    }
}
