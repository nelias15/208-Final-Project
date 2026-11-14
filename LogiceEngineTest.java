public class LogiceEngineTest {

    // Check if a test passes
    static void checkTest(String name, boolean result, boolean expected) {

        if (result == expected) {
            System.out.println(name + ": PASS");
        } else {
            System.out.println(name + ": FAIL");
        }
    }

    public static void main(String[] args) {

        boolean p = true;
        boolean q = false;

        checkTest("AND", p && q, false);
        checkTest("OR", p || q, true);
        checkTest("NOT", !p, false);

        // Create expression tree
        ExpressionTree.Node root = new ExpressionTree.Node("AND");

        root.left = new ExpressionTree.Node("P");
        root.right = new ExpressionTree.Node("Q");

        // Test tree traversals
        System.out.print("Preorder: ");
        ExpressionTree.preorder(root);

        System.out.print("\nInorder: ");
        ExpressionTree.inorder(root);

        System.out.print("\nPostorder: ");
        ExpressionTree.postorder(root);

        System.out.println();
    }
}
