import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.time.LocalDate;

public class MainViewController {

    @FXML private ComboBox<String> scenarioSelector;
    @FXML private ComboBox<String> courseComboBox; // ComboBox for Ahmed's courses
    
    // UI elements for Scenario D (SecureSteps)
    @FXML private CheckBox passwordCheckBox;     // P
    @FXML private CheckBox verificationCheckBox; // V
    @FXML private CheckBox unlockedCheckBox;     // L
    
    // UI elements for Scenario B (LaptopWise / Course Eligibility)
    @FXML private CheckBox prereqCheckBox;       
    @FXML private CheckBox holdCheckBox;         
    
    @FXML private Button evaluateButton;
    @FXML private TextArea resultTextArea;

    // Backend instances for team modules
    private final SecureSteps secureStepsSimulator = new SecureSteps();
    private final LaptopWise laptopWiseSimulator = new LaptopWise();
    
    // Initialize Ahmed's sample catalog with COSC 208, COSC 301, and MATH 231
    private final LaptopCatalog laptopCatalog = LaptopCatalog.createSampleCatalog();

    @FXML
    public void initialize() {
        // 1. Populate the scenario dropdown
        scenarioSelector.getItems().addAll(
            "Scenario D: SecureSteps (Login Logic)",
            "Scenario B: LaptopWise (Course Eligibility)"
        );
        scenarioSelector.setValue("Scenario D: SecureSteps (Login Logic)");

        // 2. Populate Ahmed's Course ComboBox dynamically from the LaptopCatalog
        for (LaptopCatalog.Course course : laptopCatalog.getAllCourses()) {
            courseComboBox.getItems().add(course.getCourseCode());
        }
        if (!courseComboBox.getItems().isEmpty()) {
            courseComboBox.setValue(courseComboBox.getItems().get(0));
        }

        // 3. Set button action
        evaluateButton.setOnAction(event -> handleEvaluation());
    }

    private void handleEvaluation() {
        String selectedScenario = scenarioSelector.getValue();

        if (selectedScenario != null && selectedScenario.contains("SecureSteps")) {
            // Run Bruce's SecureSteps logic
            boolean p = passwordCheckBox.isSelected();
            boolean v = verificationCheckBox.isSelected();
            boolean l = unlockedCheckBox.isSelected();

            SecureSteps.EvaluationResult result = secureStepsSimulator.evaluate(p, v, l);

            // Run Devin's Truth Table generator logic
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

            resultTextArea.setText(result.toString() + "\n" + truthTableSb.toString());
        } 
        else if (selectedScenario != null && selectedScenario.contains("LaptopWise")) {
            // Run Ahmed's LaptopWise / Course Eligibility evaluation
            boolean prereq = prereqCheckBox != null && prereqCheckBox.isSelected();
            boolean hold = holdCheckBox != null && holdCheckBox.isSelected();
            
            String selectedCourseCode = courseComboBox.getValue();

            try {
                LaptopWise.RegistrationResult regResult = laptopWiseSimulator.evaluate(
                    selectedCourseCode,
                    laptopCatalog,
                    prereq,
                    hold,
                    LocalDate.now().plusDays(10), // Deadline in 10 days
                    LocalDate.now()
                );

                StringBuilder sb = new StringBuilder();
                sb.append("=== Scenario B: LaptopWise / Eligibility Checker ===\n");
                sb.append("Selected Course: ").append(selectedCourseCode).append("\n");
                
                // Fetch course details from catalog for extra context
                LaptopCatalog.Course courseObj = laptopCatalog.findCourse(selectedCourseCode);
                if (courseObj != null) {
                    sb.append("Title: ").append(courseObj.getTitle()).append("\n");
                    sb.append("Prerequisite Required: ").append(courseObj.getPrerequisiteCode()).append("\n");
                    sb.append("Available Seats: ").append(courseObj.getAvailableSeats())
                      .append(" / ").append(courseObj.getCapacity()).append("\n\n");
                }

                sb.append("Eligible: ").append(regResult.isEligible() ? "YES (TRUE)" : "NO (FALSE)").append("\n");
                sb.append("Status: ").append(regResult.getStatusMessage()).append("\n\n");
                sb.append("Explanations:\n");
                for (String exp : regResult.getExplanations()) {
                    sb.append(" - ").append(exp).append("\n");
                }

                resultTextArea.setText(sb.toString());
            } catch (Exception e) {
                resultTextArea.setText("Error evaluating LaptopWise: " + e.getMessage());
            }
        }
        else {
            resultTextArea.setText("Please select a valid scenario.");
        }
    }
}
