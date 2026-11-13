package com.logicforge;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MainViewController {

    @FXML
    private ComboBox<String> scenarioSelector;

    // Checkboxes for Inputs (Scenario B & D examples)
    @FXML private CheckBox inputP; // Prerequisite / Password Passed
    @FXML private CheckBox inputS; // Seat Available / 2FA Passed
    @FXML private CheckBox inputH; // Hold Exists (Scenario B)
    @FXML private CheckBox inputL; // Account Unlocked (Scenario D)
    @FXML private CheckBox inputD; // Deadline Open (Scenario B)

    @FXML
    private TextArea resultOutputArea;

    @FXML
    public void initialize() {
        scenarioSelector.getItems().addAll("Scenario B: Course Registration", "Scenario D: SecureSteps Login");
        scenarioSelector.setValue("Scenario B: Course Registration");
    }

    @FXML
    protected void onEvaluateButtonClick() {
        String selectedScenario = scenarioSelector.getValue();
        
        if (selectedScenario.contains("Course Registration")) {
            // TODO: Call Ahmed's CourseRegistration class once pulled from GitHub
            // boolean result = CourseRegistration.evaluate(inputP.isSelected(), inputS.isSelected(), inputH.isSelected(), inputD.isSelected());
            resultOutputArea.setText("Evaluating Scenario B...");
        } else {
            // TODO: Call Bruce's SecureSteps class once pulled from GitHub
            // boolean result = SecureSteps.evaluate(inputP.isSelected(), inputS.isSelected(), inputL.isSelected());
            resultOutputArea.setText("Evaluating Scenario D...");
        }
    }
}
