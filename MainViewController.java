import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class MainViewController {

    @FXML private CheckBox passwordCheckBox;     // P
    @FXML private CheckBox verificationCheckBox; // V
    @FXML private CheckBox unlockedCheckBox;     // L
    @FXML private Button evaluateButton;
    @FXML private TextArea resultTextArea;

    // Instance of Bruce's SecureSteps backend simulator
    private final SecureSteps secureStepsSimulator = new SecureSteps();

    @FXML
    public void initialize() {
        evaluateButton.setOnAction(event -> handleEvaluateSecureSteps());
    }

    private void handleEvaluateSecureSteps() {
        // 1. Read input states from your UI checkboxes
        boolean p = passwordCheckBox.isSelected();
        boolean v = verificationCheckBox.isSelected();
        boolean l = unlockedCheckBox.isSelected();

        // 2. Call Bruce's backend evaluation method
        SecureSteps.EvaluationResult result = secureStepsSimulator.evaluate(p, v, l);

        // 3. Display the structured result in your UI / ExplanationView
        resultTextArea.setText(result.toString());
    }
}
