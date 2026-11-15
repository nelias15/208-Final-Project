import java.time.LocalDate;
import java.util.Scanner;

public class MainViewController {

    private final SecureSteps secureStepsSimulator = new SecureSteps();
    private final LaptopWise laptopWiseSimulator = new LaptopWise();
    private final LaptopCatalog laptopCatalog = LaptopCatalog.createSampleCatalog();

    public void runConsoleApp() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("  LogicForge — Explainable Decision Support (Console)");
        System.out.println("==================================================");

        while (running) {
            System.out.println("\nSelect a Scenario:");
            System.out.println("1. Scenario D: SecureSteps (Login Logic)");
            System.out.println("2. Scenario B: LaptopWise (Course Eligibility)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                runSecureStepsScenario(scanner);
            } else if (choice.equals("2")) {
                runLaptopWiseScenario(scanner);
            } else if (choice.equals("3")) {
                running = false;
                System.out.println("Exiting LogicForge. Good luck with your presentation!");
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    public void runSecureStepsScenario(Scanner scanner) {
        System.out.println("\n--- Running Scenario D: SecureSteps ---");
        System.out.print("Is Password correct? (true/false): ");
        boolean p = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Is 2FA Verification passed? (true/false): ");
        boolean v = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Is Account Unlocked? (true/false): ");
        boolean l = Boolean.parseBoolean(scanner.nextLine());

        SecureSteps.EvaluationResult result = secureStepsSimulator.evaluate(p, v, l);

        StringBuilder truthTableSb = new StringBuilder();
        truthTableSb.append("\n--- Devin's Truth Table Engine ---\n");
        truthTableSb.append("P\tQ\tAND\tOR\tNOT P\n");

        boolean[] values = {true, false};
        for (boolean bp : values) {
            for (boolean bq : values) {
                boolean and = bp && bq;
                boolean or = bp || bq;
                boolean not = !bp;
                truthTableSb.append(bp).append("\t").append(bq).append("\t")
                        .append(and).append("\t").append(or).append("\t").append(not).append("\n");
            }
        }

        System.out.println("\n[Results]");
        System.out.println(result.toString());
        System.out.println(truthTableSb.toString());
    }

    public void runLaptopWiseScenario(Scanner scanner) {
        System.out.println("\n--- Running Scenario B: LaptopWise ---");
        System.out.println("Available Courses:");
        for (LaptopCatalog.Course course : laptopCatalog.getAllCourses()) {
            System.out.println(" - " + course.getCourseCode() + ": " + course.getTitle());
        }

        System.out.print("Enter course code (e.g., COSC 208): ");
        String selectedCourseCode = scanner.nextLine().trim();

        System.out.print("Prerequisite completed? (true/false): ");
        boolean prereq = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Any registration holds? (true/false): ");
        boolean hold = Boolean.parseBoolean(scanner.nextLine());

        try {
            LaptopWise.RegistrationResult regResult = laptopWiseSimulator.evaluate(
                    selectedCourseCode,
                    laptopCatalog,
                    prereq,
                    hold,
                    LocalDate.now().plusDays(10),
                    LocalDate.now()
            );

            System.out.println("\n=== Scenario B Results ===");
            System.out.println("Selected Course: " + selectedCourseCode);

            LaptopCatalog.Course courseObj = laptopCatalog.findCourse(selectedCourseCode);
            if (courseObj != null) {
                System.out.println("Title: " + courseObj.getTitle());
                System.out.println("Available Seats: " + courseObj.getAvailableSeats() + " / " + courseObj.getCapacity());
            }

            System.out.println("Eligible: " + (regResult.isEligible() ? "YES (TRUE)" : "NO (FALSE)"));
            System.out.println("Status: " + regResult.getStatusMessage());
            System.out.println("Explanations:");
            for (String exp : regResult.getExplanations()) {
                System.out.println(" - " + exp);
            }
        } catch (Exception e) {
            System.out.println("Error evaluating LaptopWise: " + e.getMessage());
        }
    }
}
