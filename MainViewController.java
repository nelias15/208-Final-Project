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

    // Backend instances
    private final SecureSteps secureStepsSimulator = new SecureSteps();

    @FXML
    public void initialize() {
        // 1. Populate the scenario dropdown options
        scenarioSelector.getItems().addAll(
            "Scenario D: SecureSteps (Login Logic)",
            "Scenario B: LaptopWise (Laptop Recommendation)" // Ready for Ahmed's part next
        );
        scenarioSelector.setValue("Scenario D: SecureSteps (Login Logic)");

        // 2. Set button action
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
            resultTextArea.setText(result.toString());
        } 
        else {
            resultTextArea.setText("Please select a valid scenario or wait for Ahmed's LaptopWise module to load.");
        }
    }
}
