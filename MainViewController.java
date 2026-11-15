import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MainViewController {

    @FXML private ComboBox<String> scenarioSelector;
    
    // UI elements for Scenario D (SecureSteps)
    @FXML private CheckBox passwordCheckBox;     // P
    @FXML private CheckBox verificationCheckBox; // V
    @FXML private CheckBox unlockedCheckBox;     // L
    
    @FXML private Button evaluateButton;
    @FXML private TextArea resultTextArea;

    // Backend instance for Bruce's part
    private final SecureSteps secureStepsSimulator = new SecureSteps();

    @FXML
    public void initialize() {
        // 1. Populate the scenario dropdown options
        scenarioSelector.getItems().addAll(
            "Scenario D: SecureSteps (Login Logic)",
            "Scenario B: LaptopWise (Laptop Recommendation)"
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

            // 2. Run Devin's Truth Table generator logic (adapted for UI text)
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

            // 3. Display both Bruce's result and Devin's truth table in your text area
            resultTextArea.setText(result.toString() + "\n" + truthTableSb.toString());
        } 
        else if (selectedScenario != null && selectedScenario.contains("LaptopWise")) {
            // Placeholder for Ahmed's part
            resultTextArea.setText("Scenario B (LaptopWise) - Ready for Ahmed's code integration next!");
        }
        else {
            resultTextArea.setText("Please select a valid scenario.");
        }
    }
}
