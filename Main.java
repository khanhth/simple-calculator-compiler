public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Visitor-Driven Semantic Analysis Test ---");

        // Target Expression: x * 3 + y
        Exp leftSubtree = new OpExp(new IdExp("x"), OpExp.TIMES, new NumExp(3));
        Exp completeTree = new OpExp(leftSubtree, OpExp.PLUS, new IdExp("y"));

        // Set up our initialized variables context
        TypeContext semanticEnvironment = new TypeContext();
        semanticEnvironment.initializedVariables.add("x"); // 'x' is declared!
        // Notice: We intentionally do NOT add 'y' to simulate a programmer error!

        // 1. Run Semantic Analysis Check
        System.out.println("\n[Step 1] Running Type Checker & Initialization Analysis...");
        TypeChecker checker = new TypeChecker();
        boolean isSafe = checker.check(completeTree, semanticEnvironment);

        // 2. Conditional Compilation Guard
        if (!isSafe) {
            System.out.println("\n🛑 Compilation Halted: Code contains semantic errors.");
            return;
        }

        // 3. Emit Assembly Code (Only executes if isSafe is true)
        System.out.println("\n[Step 2] Emitting target assembly instructions:");
        CodeGenerator codegen = new CodeGenerator();
        codegen.compile(completeTree);
    }

}
