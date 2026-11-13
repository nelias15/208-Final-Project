import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Sequential Decision-Tree Engine for LogicForge (COSC 208 Final Project).
 *
 * Evaluates boolean propositions one question at a time along true/false branches,
 * tracks the exact traversal path taken, and returns the final recommended action.
 */
public class DecisionTree {
    
    /**
     * Represents either an internal decision node (a Yes/No condition)
     * or a leaf node (a final recommended action).
     */
    public static class Node {
        private final String variableKey;   // e.g., "L", "P", "V" (null for leaf nodes)
        private final String question;      // e.g., "Is the account unlocked?"
        private final Node trueBranch;      // Next node if condition is TRUE (Yes)
        private final Node falseBranch;     // Next node if condition is FALSE (No)
        private final String outcomeAction; // Final action string (non-null for leaf nodes)

        private Node(String variableKey, String question, Node trueBranch, Node falseBranch, String outcomeAction) {
            this.variableKey = variableKey;
            this.question = question;
            this.trueBranch = trueBranch;
            this.falseBranch = falseBranch;
            this.outcomeAction = outcomeAction;
        }

        /**
         * Factory method for an internal condition node.
         */
        public static Node decision(String variableKey, String question, Node trueBranch, Node falseBranch) {
            return new Node(variableKey, question, trueBranch, falseBranch, null);
        }

        /**
         * Factory method for a terminal outcome leaf node.
         */
        public static Node leaf(String outcomeAction) {
            return new Node(null, null, null, null, outcomeAction);
        }

        public boolean isLeaf() {
            return outcomeAction != null;
        }

        public String getVariableKey() { return variableKey; }
        public String getQuestion() { return question; }
        public Node getTrueBranch() { return trueBranch; }
        public Node getFalseBranch() { return falseBranch; }
        public String getOutcomeAction() { return outcomeAction; }
    }

    /**
     * Stores the final outcome and step-by-step trace of a decision-tree evaluation.
     */
    public static class TraversalResult {
        private final String finalAction;
        private final List<String> stepsTaken;

        public TraversalResult(String finalAction, List<String> stepsTaken) {
            this.finalAction = finalAction;
            this.stepsTaken = Collections.unmodifiableList(stepsTaken);
        }

        public String getFinalAction() { return finalAction; }
        public List<String> getStepsTaken() { return stepsTaken; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Decision Tree Traversal ===\n");
            for (int i = 0; i < stepsTaken.size(); i++) {
                sb.append(String.format("Step %d: %s%n", i + 1, stepsTaken.get(i)));
            }
            sb.append("Final Recommendation: ").append(finalAction).append("\n");
            return sb.toString();
        }
    }

    private Node root;

    public DecisionTree() {
        this.root = null;
    }

    public DecisionTree(Node root) {
        this.root = root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Evaluates the decision tree using a map of proposition truth values.
     *
     * @param truthValues Map of variable names (e.g., "P", "V", "L") to boolean values
     * @return TraversalResult containing the ordered path and final recommendation
     */
    public TraversalResult evaluate(Map<String, Boolean> truthValues) {
        if (root == null) {
            throw new IllegalStateException("Decision tree has no root node configured.");
        }
        if (truthValues == null) {
            throw new IllegalArgumentException("Truth values map cannot be null.");
        }

        List<String> steps = new ArrayList<>();
        Node current = root;

        while (current != null && !current.isLeaf()) {
            String key = current.getVariableKey();
            Boolean value = truthValues.get(key);

            if (value == null) {
                throw new IllegalArgumentException("Missing truth value for proposition: " + key);
            }

            if (value) {
                steps.add(current.getQuestion() + " [" + key + " = TRUE] -> Yes");
                current = current.getTrueBranch();
            } else {
                steps.add(current.getQuestion() + " [" + key + " = FALSE] -> No");
                current = current.getFalseBranch();
            }
        }

        if (current == null) {
            throw new IllegalStateException("Decision tree branch terminated without a leaf outcome.");
        }

        steps.add("Outcome Reached -> " + current.getOutcomeAction());
        return new TraversalResult(current.getOutcomeAction(), steps);
    }

    /**
     * Convenience helper for evaluating Scenario D directly with (P, V, L) booleans.
     */
    public TraversalResult evaluateLogin(boolean passwordPassed, boolean verificationPassed, boolean accountUnlocked) {
        Map<String, Boolean> inputs = new LinkedHashMap<>();
        inputs.put("P", passwordPassed);
        inputs.put("V", verificationPassed);
        inputs.put("L", accountUnlocked);
        return evaluate(inputs);
    }

    /**
     * Renders a clean text diagram of the entire decision tree structure.
     * Useful for ExplanationView, console output, and project screenshots.
     */
    public String toTreeDiagramString() {
        if (root == null) {
            return "Decision Tree is empty.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sequential Decision Tree Structure ===\n");
        buildDiagramHelper(root, "", true, "ROOT", sb);
        return sb.toString();
    }

    private void buildDiagramHelper(Node node, String prefix, boolean isTail, String branchLabel, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append(prefix)
          .append(isTail ? "+-- " : "|-- ")
          .append("[").append(branchLabel).append("] ");

        if (node.isLeaf()) {
            sb.append("ACTION: ").append(node.getOutcomeAction()).append("\n");
        } else {
            sb.append(node.getQuestion()).append(" (").append(node.getVariableKey()).append(")\n");
            String childPrefix = prefix + (isTail ? "    " : "|   ");
            buildDiagramHelper(node.getTrueBranch(), childPrefix, false, "YES / TRUE", sb);
            buildDiagramHelper(node.getFalseBranch(), childPrefix, true, "NO / FALSE", sb);
        }
    }

    /**
     * Standalone test runner demonstrating Scenario D's Decision Tree.
     */
    public static void main(String[] args) {
        SecureSteps simulator = new SecureSteps();
        DecisionTree tree = simulator.buildDecisionTree();

        System.out.println(tree.toTreeDiagramString());
        System.out.println(tree.evaluateLogin(true, true, true));
        System.out.println(tree.evaluateLogin(true, false, true));
    }
}
