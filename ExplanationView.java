package com.logicforge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ExplanationView {

    @FXML
    private Label recommendationBanner;

    @FXML
    private TextArea truthTableArea;

    @FXML
    private TextArea expressionTreeArea;

    @FXML
    private TextArea knowledgeGraphArea;

    // Method to update the UI with data from Devin's and Bruce's backend engines
    public void displayResults(String recommendation, String truthTableText, String treeText, String graphText) {
        recommendationBanner.setText(recommendation);
        truthTableArea.setText(truthTableText);
        expressionTreeArea.setText(treeText);
        knowledgeGraphArea.setText(graphText);
    }
}
