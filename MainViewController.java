import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.time.LocalDate;

public class MainViewController {

    @FXML private ComboBox<String> scenarioSelector;
    
    // UI elements for Scenario D (SecureSteps)
    @FXML private CheckBox passwordCheckBox;     // P
    @FXML private CheckBox verificationCheckBox; // V
    @FXML private CheckBox unlockedCheckBox;     // L
    
    // UI elements for Scenario B (LaptopWise / Course Eligibility)
    @FXML private CheckBox prereqCheckBox;       // Prerequisite completed
    @FXML private CheckBox holdCheckBox;         // Registration hold exists
    
    @FXML private Button evaluateButton;
    @FXML private TextArea resultTextArea;

    // Backend instances for team modules
    private final SecureSteps secureStepsSimulator = new SecureSteps();
    private final LaptopWise laptopWiseSimulator = new LaptopWise();
    private final LaptopCatalog laptopCatalog = new LaptopCatalog();

    @FXML
    public void initialize() {
        // 1. Populate the scenario dropdown options
        scenarioSelector.getItems().addAll(
            "Scenario D: SecureSteps (Login Logic)",
            "Scenario B: LaptopWise (Course Eligibility)"
        );
        scenarioSelector.setValue("Scenario D: SecureSteps (Login Logic)");

        // 2. Set button action
        evaluateButton.setOnAction(event -> handleEvaluation());
    }

    private void handleEvaluation() {
        String selectedScenario = scenarioSelector.getValue();

        if (selectedScenario != null && selectedScenario.contains("SecureSteps")) {
            // 1. Run Bruce's SecureSteps logic
            boolean p = passwordCheckBox.isSelected();
            boolean v = verificationCheckBox.isSelected();
            boolean l = unlockedCheckBox.isSelected();

            SecureSteps.EvaluationResult result = secureStepsSimulator.evaluate(p, v, l);

            // 2. Run Devin's Truth Table generator logic
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

            // 3. Display both Bruce's result and Devin's truth table
            resultTextArea.setText(result.toString() + "\n" + truthTableSb.toString());
        } 
        else if (selectedScenario != null && selectedScenario.contains("LaptopWise")) {
            // 1. Read inputs for Ahmed's LaptopWise / CoursePath logic
            boolean prereq = prereqCheckBox != null && prereqCheckBox.isSelected();
            boolean hold = holdCheckBox != null && holdCheckBox.isSelected();

            try {
                // Evaluating using Ahmed's method (testing against a sample course code like "CS101")
                LaptopWise.RegistrationResult regResult = laptopWiseSimulator.evaluate(
                    "CS101",
                    laptopCatalog,
                    prereq,
                    hold,
                    LocalDate.now().plusDays(10), // Deadline in 10 days
                    LocalDate.now()
                );

                // 2. Format Ahmed's results for the UI text area
                StringBuilder sb = new StringBuilder();
                sb.append("=== Scenario B: LaptopWise / Eligibility Checker ===\n");
                sb.append("Eligible: ").append(regResult.isEligible() ? "YES (TRUE)" : "NO (FALSE)").append("\n");
                sb.append("Status: ").append(regResult.getStatusMessage()).append("\n\n");
                sb.append("Explanations:\n");
                for (String exp : regResult.getExplanations()) {
                    sb.append(" - ").append(exp).append("\n");
                }

                resultTextArea.setText(sb.toString());
            } catch (Exception e) {
                resultTextArea.setText("Error evaluating LaptopWise: " + e.getMessage() + 
                                       "\n(Ensure Ahmed's LaptopCatalog contains a default course like 'CS101').");
            }
        }
        else {
            resultTextArea.setText("Please select a valid scenario.");
        }
    }
}
