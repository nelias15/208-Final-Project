import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Scenario D — SecureSteps: A Safe Login Logic Simulator.
 *
 * Educational simulator demonstrating multi-factor access control using propositional logic.
 * Core Rule: AccessGranted = P ∧ V ∧ L
 * Where:
 *   P = Password check passed (fictional toggle)
 *   V = Second verification (2FA) passed (fictional toggle)
 *   L = Account is not locked (fictional toggle)
 *
 * DISCLAIMER: This is strictly a learning simulator using fictional boolean states.
 * It does not collect, store, or validate real passwords, verification codes, or personal data.
 */
public class SecureSteps {
    
    public static final String SCENARIO_NAME = "Scenario D - SecureSteps: Login Logic Simulator";
    public static final String BOOLEAN_EXPRESSION = "P && V && L";
    public static final String FORMAL_LOGIC_RULE = "AccessGranted = P AND V AND L";
    public static final String SIMULATOR_DISCLAIMER =
            "EDUCATIONAL SIMULATOR ONLY: Uses fictional boolean toggles. " +
            "Do NOT enter real passwords, 2FA codes, or account credentials.";

    /**
     * Structured result returned after evaluating a simulated login attempt.
     * Designed for easy rendering in MainViewController and ExplanationView.
     */
    public static class EvaluationResult {
        private final boolean passwordPassed;      // P
        private final boolean verificationPassed;  // V
        private final boolean accountUnlocked;     // L
        private final boolean accessGranted;       // P ∧ V ∧ L
        private final String recommendedAction;
        private final String explanation;
        private final List<String> passedConditions;
        private final List<String> failedConditions;
        private final List<String> decisionPath;

        public EvaluationResult(
                boolean passwordPassed,
                boolean verificationPassed,
                boolean accountUnlocked,
                boolean accessGranted,
                String recommendedAction,
                String explanation,
                List<String> passedConditions,
                List<String> failedConditions,
                List<String> decisionPath) {
            this.passwordPassed = passwordPassed;
            this.verificationPassed = verificationPassed;
            this.accountUnlocked = accountUnlocked;
            this.accessGranted = accessGranted;
            this.recommendedAction = recommendedAction;
            this.explanation = explanation;
            this.passedConditions = passedConditions;
            this.failedConditions = failedConditions;
            this.decisionPath = decisionPath;
        }

        public boolean isPasswordPassed() { return passwordPassed; }
        public boolean isVerificationPassed() { return verificationPassed; }
        public boolean isAccountUnlocked() { return accountUnlocked; }
        public boolean isAccessGranted() { return accessGranted; }
        public String getRecommendedAction() { return recommendedAction; }
        public String getExplanation() { return explanation; }
        public List<String> getPassedConditions() { return passedConditions; }
        public List<String> getFailedConditions() { return failedConditions; }
        public List<String> getDecisionPath() { return decisionPath; }
        public String getFormalRule() { return FORMAL_LOGIC_RULE; }
        public String getDisclaimer() { return SIMULATOR_DISCLAIMER; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ").append(SCENARIO_NAME).append(" ===\n");
            sb.append("Notice: ").append(SIMULATOR_DISCLAIMER).append("\n");
            sb.append("Rule: ").append(FORMAL_LOGIC_RULE).append("\n");
            sb.append(String.format("Inputs: P (Password) = %b | V (2FA) = %b | L (Unlocked) = %b%n",
                    passwordPassed, verificationPassed, accountUnlocked));
            sb.append("Access Granted: ").append(accessGranted ? "YES (TRUE)" : "NO (FALSE)").append("\n");
            sb.append("Recommended Action: ").append(recommendedAction).append("\n");
            sb.append("Explanation: ").append(explanation).append("\n");
            sb.append("Decision Path: ").append(String.join(" -> ", decisionPath)).append("\n");
            return sb.toString();
        }
    }

    /**
     * Evaluates the propositional logic rule AccessGranted = P ∧ V ∧ L
     * and generates a human-readable explanation and safe recovery guidance.
     *
     * @param passwordPassed     P: true if fictional password check passed
     * @param verificationPassed V: true if fictional second verification passed
     * @param accountUnlocked    L: true if fictional account is not locked
     * @return EvaluationResult containing logic outcome, explanation, and sequential path
     */
    public EvaluationResult evaluate(boolean passwordPassed, boolean verificationPassed, boolean accountUnlocked) {
        // Core propositional logic evaluation: P ∧ V ∧ L
        boolean accessGranted = passwordPassed && verificationPassed && accountUnlocked;

        List<String> passed = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        if (accountUnlocked) {
            passed.add("L (Account Unlocked): True");
        } else {
            failed.add("L (Account Unlocked): False - Account is currently locked.");
        }

        if (passwordPassed) {
            passed.add("P (Password Check): True");
        } else {
            failed.add("P (Password Check): False - Password verification failed.");
        }

        if (verificationPassed) {
            passed.add("V (Second Verification / 2FA): True");
        } else {
            failed.add("V (Second Verification / 2FA): False - Secondary verification failed.");
        }

        // Sequential decision-tree routing & safe recovery guidance
        List<String> path = new ArrayList<>();
        String action;
        String explanation;

        path.add("Start Login Simulation");
        if (!accountUnlocked) {
            path.add("Is account unlocked (L)? -> No");
            path.add("Outcome: Contact Support / Account Recovery");
            action = "Use Account Recovery or Contact Support";
            explanation = "Access denied because L (Account Unlocked) is FALSE. " +
                          "Even if credentials are valid, a locked account blocks authentication. " +
                          "Follow safe account recovery procedures or contact the help desk.";
        } else {
            path.add("Is account unlocked (L)? -> Yes");
            if (!passwordPassed) {
                path.add("Did password check pass (P)? -> No");
                path.add("Outcome: Retry Password / Reset Password");
                action = "Retry Password Entry or Use Password Reset";
                explanation = "Access denied because P (Password Check) is FALSE. " +
                              "Your account is unlocked (L = True), so you may safely re-enter your password " +
                              "or initiate a password reset before attempting 2FA.";
            } else {
                path.add("Did password check pass (P)? -> Yes");
                if (!verificationPassed) {
                    path.add("Did second verification pass (V)? -> No");
                    path.add("Outcome: Retry 2FA Verification");
                    action = "Retry Second Verification (2FA) or Use Backup Method";
                    explanation = "Access denied because V (Second Verification) is FALSE. " +
                                  "Your account is unlocked (L = True) and your password passed (P = True), " +
                                  "but multi-factor authentication is required to satisfy P AND V AND L.";
                } else {
                    path.add("Did second verification pass (V)? -> Yes");
                    path.add("Outcome: Access Granted");
                    action = "Access Granted - Proceed to Dashboard";
                    explanation = "Access granted because all three required propositions are TRUE " +
                                  "(P = True, V = True, L = True), satisfying AccessGranted = P AND V AND L.";
                }
            }
        }

        return new EvaluationResult(
                passwordPassed,
                verificationPassed,
                accountUnlocked,
                accessGranted,
                action,
                explanation,
                passed,
                failed,
                path
        );
    }

    /**
     * Returns a map of variable symbols to their plain-English descriptions.
     * Useful for populating TruthTableGenerator, ExpressionTree, and UI labels.
     * @return
     */
    public Map<String, String> getPropositionDefinitions() {
        Map<String, String> definitions = new LinkedHashMap<>();
        definitions.put("P", "Password check passed");
        definitions.put("V", "Second verification (2FA) passed");
        definitions.put("L", "Account is not locked");
        return definitions;
    }

    /**
     * Builds the Scenario D Knowledge Graph linking Account, Password Check,
     * Verification Step, Lockout, and Help Desk.
     * Requires KnowledgeGraph.java in the same package.
     * @return populated KnowledgeGraph instance for Scenario D
     */
    public KnowledgeGraph buildKnowledgeGraph() {
        KnowledgeGraph graph = new KnowledgeGraph();

        graph.addNode("User Account");
        graph.addNode("Password Check (P)");
        graph.addNode("2FA Verification (V)");
        graph.addNode("Lockout Status (L)");
        graph.addNode("Access Granted");
        graph.addNode("Account Recovery");
        graph.addNode("Help Desk");

        graph.addEdge("User Account", "Lockout Status (L)", "checked_first_for");
        graph.addEdge("User Account", "Password Check (P)", "authenticated_by");
        graph.addEdge("Password Check (P)", "2FA Verification (V)", "followed_by");
        graph.addEdge("2FA Verification (V)", "Access Granted", "grants_when_all_true");
        graph.addEdge("Lockout Status (L)", "Account Recovery", "triggers_if_locked");
        graph.addEdge("Password Check (P)", "Account Recovery", "triggers_reset_if_failed");
        graph.addEdge("Account Recovery", "Help Desk", "escalates_to");

        return graph;
    }
    
    public DecisionTree buildDecisionTree() {
        DecisionTree tree = new DecisionTree();

        DecisionTree.Node accessGrantedNode = DecisionTree.Node.leaf("Access Granted - Proceed to Dashboard");
        DecisionTree.Node retry2faNode = DecisionTree.Node.leaf("Retry Second Verification (2FA) or Use Backup Method");
        DecisionTree.Node retryPasswordNode = DecisionTree.Node.leaf("Retry Password Entry or Use Password Reset");
        DecisionTree.Node lockoutRecoveryNode = DecisionTree.Node.leaf("Use Account Recovery or Contact Support");

        DecisionTree.Node check2faNode = DecisionTree.Node.decision(
                "V", "Did second verification (2FA) pass?", accessGrantedNode, retry2faNode);
        DecisionTree.Node checkPasswordNode = DecisionTree.Node.decision(
                "P", "Did password check pass?", check2faNode, retryPasswordNode);
        DecisionTree.Node rootNode = DecisionTree.Node.decision(
                "L", "Is the account unlocked?", checkPasswordNode, lockoutRecoveryNode);

        tree.setRoot(rootNode);
        return tree;
    }

    public static void main(String[] args) {
        SecureSteps simulator = new SecureSteps();
        System.out.println(simulator.evaluate(true, true, true));
        System.out.println(simulator.evaluate(true, false, true));
        System.out.println(simulator.evaluate(true, true, false));
    }
}
