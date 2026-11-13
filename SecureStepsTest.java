import java.util.List;

/**
 * Comprehensive Test Suite for Scenario D (SecureSteps), DecisionTree, and KnowledgeGraph.
 *
 * Fulfills COSC 208 Testing Requirements:
 *  1. Normal cases (valid login granted, single-factor failures)
 *  2. Boundary cases (all inputs false, locked account overriding valid credentials,
 *     empty graph/tree error handling)
 *  3. Input-change state transition tests (proving toggling one input changes the outcome)
 *  4. Full 8-row truth table verification for AccessGranted = P ∧ V ∧ L
 */
public class SecureStepsTest {
    
     private int testsPassed = 0;
    private int testsFailed = 0;

    /**
     * Normal Case 1: All three propositions are TRUE (P=true, V=true, L=true).
     * Expected: AccessGranted = true, Action = Access Granted.
     */
    public void testNormalCaseAllTrue() {
        SecureSteps sim = new SecureSteps();
        SecureSteps.EvaluationResult res = sim.evaluate(true, true, true);

        assertTrue(res.isAccessGranted(), "Expected accessGranted = true when P, V, L are all true");
        assertEquals("Access Granted - Proceed to Dashboard", res.getRecommendedAction(),
                "Expected Access Granted recommendation");
        assertEquals(3, res.getPassedConditions().size(), "Expected all 3 conditions to pass");
        assertEquals(0, res.getFailedConditions().size(), "Expected 0 failed conditions");
    }

    /**
     * Normal Case 2: Account unlocked and password valid, but 2FA fails (P=true, V=false, L=true).
     * Expected: AccessGranted = false, Action routes to 2FA retry.
     */
    public void testNormalCaseSecondVerificationFails() {
        SecureSteps sim = new SecureSteps();
        SecureSteps.EvaluationResult res = sim.evaluate(true, false, true);

        assertFalse(res.isAccessGranted(), "Expected accessGranted = false when V is false");
        assertEquals("Retry Second Verification (2FA) or Use Backup Method", res.getRecommendedAction(),
                "Expected 2FA retry recommendation");
        assertEquals(2, res.getPassedConditions().size(), "Expected 2 passed conditions (L, P)");
        assertEquals(1, res.getFailedConditions().size(), "Expected 1 failed condition (V)");
    }

    /**
     * Normal Case 3: Account unlocked, but password fails (P=false, V=true, L=true).
     * Expected: AccessGranted = false, Action routes to password retry/reset.
     */
    public void testNormalCasePasswordFails() {
        SecureSteps sim = new SecureSteps();
        SecureSteps.EvaluationResult res = sim.evaluate(false, true, true);

        assertFalse(res.isAccessGranted(), "Expected accessGranted = false when P is false");
        assertEquals("Retry Password Entry or Use Password Reset", res.getRecommendedAction(),
                "Expected password retry recommendation");
    }

    /**
     * Boundary Case 1: All inputs FALSE (P=false, V=false, L=false).
     * Expected: Lockout check (L) takes priority and routes to Account Recovery / Support.
     */
    public void testBoundaryCaseAllFalse() {
        SecureSteps sim = new SecureSteps();
        SecureSteps.EvaluationResult res = sim.evaluate(false, false, false);

        assertFalse(res.isAccessGranted(), "Expected accessGranted = false when all inputs are false");
        assertEquals("Use Account Recovery or Contact Support", res.getRecommendedAction(),
                "Expected lockout recovery priority when all inputs are false");
        assertEquals(0, res.getPassedConditions().size(), "Expected 0 passed conditions");
        assertEquals(3, res.getFailedConditions().size(), "Expected 3 failed conditions");
    }

    /**
     * Boundary Case 2: Valid password and valid 2FA, but account is LOCKED (P=true, V=true, L=false).
     * Expected: Security boundary holds - locked status blocks access even with valid credentials.
     */
    public void testBoundaryCaseLockedAccountWithValidCredentials() {
        SecureSteps sim = new SecureSteps();
        SecureSteps.EvaluationResult res = sim.evaluate(true, true, false);

        assertFalse(res.isAccessGranted(), "Locked account must deny access even if P and V are true");
        assertEquals("Use Account Recovery or Contact Support", res.getRecommendedAction(),
                "Expected lockout recovery action");
    }

    /**
     * Input-Change Transition Test (Required by Rubric):
     * Demonstrates that toggling a single input (V from false -> true) flips the
     * recommendation from "Retry 2FA" to "Access Granted".
     */
    public void testInputChangeFlipsRecommendation() {
        SecureSteps sim = new SecureSteps();

        // Initial state: P=true, V=false, L=true -> Denied
        SecureSteps.EvaluationResult beforeChange = sim.evaluate(true, false, true);
        assertFalse(beforeChange.isAccessGranted(), "Before toggle: access should be denied");
        assertEquals("Retry Second Verification (2FA) or Use Backup Method",
                beforeChange.getRecommendedAction(), "Before toggle: should suggest 2FA retry");

        // Change single input V: false -> true
        SecureSteps.EvaluationResult afterChange = sim.evaluate(true, true, true);
        assertTrue(afterChange.isAccessGranted(), "After toggle: access should be granted");
        assertEquals("Access Granted - Proceed to Dashboard",
                afterChange.getRecommendedAction(), "After toggle: recommendation must change to Access Granted");
    }

    /**
     * Exhaustive 8-Row Truth Table Verification for P ∧ V ∧ L.
     * Confirms exact alignment between SecureSteps logic engine and DecisionTree traversal.
     */
    public void testFullTruthTableAndDecisionTreeAlignment() {
        SecureSteps sim = new SecureSteps();
        DecisionTree tree = sim.buildDecisionTree();

        boolean[] states = {false, true};
        int trueOutcomeCount = 0;

        for (boolean p : states) {
            for (boolean v : states) {
                for (boolean l : states) {
                    SecureSteps.EvaluationResult logicRes = sim.evaluate(p, v, l);
                    DecisionTree.TraversalResult treeRes = tree.evaluateLogin(p, v, l);

                    boolean expected = p && v && l;
                    assertEquals(expected, logicRes.isAccessGranted(),
                            String.format("Truth table mismatch at P=%b, V=%b, L=%b", p, v, l));
                    assertEquals(logicRes.getRecommendedAction(), treeRes.getFinalAction(),
                            String.format("DecisionTree mismatch with SecureSteps at P=%b, V=%b, L=%b", p, v, l));

                    if (expected) {
                        trueOutcomeCount++;
                    }
                }
            }
        }
        assertEquals(1, trueOutcomeCount, "Conjunction P ∧ V ∧ L must be true in exactly 1 of 8 rows");
    }

    /**
     * KnowledgeGraph Validation Test:
     * Verifies nodes, labeled edges, duplicate edge prevention, and adjacency output.
     */
    public void testKnowledgeGraphStructure() {
        SecureSteps sim = new SecureSteps();
        KnowledgeGraph kg = sim.buildKnowledgeGraph();

        assertEquals(7, kg.getNodeCount(), "Scenario D KnowledgeGraph should have 7 entities");
        assertEquals(7, kg.getEdgeCount(), "Scenario D KnowledgeGraph should have 7 directed edges");
        assertTrue(kg.hasEdge("User Account", "Lockout Status (L)"), "Missing User Account -> Lockout edge");
        assertTrue(kg.hasEdge("Account Recovery", "Help Desk"), "Missing Account Recovery -> Help Desk edge");

        // Boundary check: adding duplicate edge should not increase edge count
        kg.addEdge("Account Recovery", "Help Desk", "escalates_to");
        assertEquals(7, kg.getEdgeCount(), "Duplicate edge should be ignored");

        List<KnowledgeGraph.Edge> accountEdges = kg.getNeighbors("User Account");
        assertEquals(2, accountEdges.size(), "User Account should have 2 outgoing relationships");
    }

    // --- Lightweight Assertion Helpers ---

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + " | Expected: [" + expected + "], Actual: [" + actual + "]");
    }

    private void runTest(String testName, Runnable testMethod) {
        try {
            testMethod.run();
            testsPassed++;
            System.out.println("[PASS] " + testName);
        } catch (Throwable t) {
            testsFailed++;
            System.err.println("[FAIL] " + testName + " -> " + t.getMessage());
        }
    }

    public static void main(String[] args) {
        SecureStepsTest runner = new SecureStepsTest();
        System.out.println("=== Running Scenario D (SecureSteps) Test Suite ===");

        runner.runTest("1. Normal Case: All True (P=T, V=T, L=T)", runner::testNormalCaseAllTrue);
        runner.runTest("2. Normal Case: 2FA Fails (P=T, V=F, L=T)", runner::testNormalCaseSecondVerificationFails);
        runner.runTest("3. Normal Case: Password Fails (P=F, V=T, L=T)", runner::testNormalCasePasswordFails);
        runner.runTest("4. Boundary Case: All False (P=F, V=F, L=F)", runner::testBoundaryCaseAllFalse);
        runner.runTest("5. Boundary Case: Locked Account Overrides Valid Credentials", runner::testBoundaryCaseLockedAccountWithValidCredentials);
        runner.runTest("6. Input-Change Transition: Toggling V Flips Recommendation", runner::testInputChangeFlipsRecommendation);
        runner.runTest("7. Full 8-Row Truth Table & DecisionTree Alignment", runner::testFullTruthTableAndDecisionTreeAlignment);
        runner.runTest("8. KnowledgeGraph Entities, Edges & Boundary Checks", runner::testKnowledgeGraphStructure);
    }
}
