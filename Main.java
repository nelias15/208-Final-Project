import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainViewController controller = new MainViewController();
        boolean running = true;

        System.out.println("==========================================");
        System.out.println("  LogicForge — Explainable Decision Support");
        System.out.println("==========================================");

        while (running) {
            System.out.println("\nSelect a module to run:");
            System.out.println("1. Truth Table Generator & SecureSteps (Scenario D)");
            System.out.println("2. LaptopWise & Catalog (Scenario B)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Calls the secure steps & truth table engine from MainViewController
                    controller.runSecureStepsScenario(scanner);
                    break;
                case "2":
                    // Calls Ahmed's LaptopWise scenario from MainViewController
                    controller.runLaptopWiseScenario(scanner);
                    break;
                case "3":
                    running = false;
                    System.out.println("Exiting LogicForge. Good luck with your presentation!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}
