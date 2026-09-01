public class Main {
    public static void main(String[] args) {
        String sourceCode = "int x; int y; x = 5; y = 10; 5 * 3 + y;";
        System.out.println("--- Starting Advanced Optimizing Compiler Pipeline ---\n");

        CompilerContext context = new CompilerContext();
        Lexer lexer = new Lexer(sourceCode);
        ASTParser parser = new ASTParser(lexer, context);
        ProgramNode programTree = parser.parseProgram();

        System.out.println("[Phase 1] Processing source text definitions...");
        new ASTPrinter().print(programTree); // Visualize the AST structure

        // Step 1: Generate Raw Middle-End IR
        IRGenerator irGen = new IRGenerator();
        java.util.List<IRInstruction> currentIR = irGen.generate(programTree);

        // Step 2: Optimization Pass #1 ── Constant Folding
        System.out.println("[Middle-End] Running Constant Folding Pass...");
        IROptimizer optimizer = new IROptimizer();
        currentIR = optimizer.optimize(currentIR); // Folds math into static literals

        // Step 3: Optimization Pass #2 ── Dead Code Elimination
        System.out.println("\n[Middle-End] Running Dead Code Elimination Pass...");
        currentIR = optimizer.eliminateDeadCode(currentIR); // Trims unused nodes

        System.out.println("\n[Phase 2] Fully Optimized Final IR Stream:");
        for (IRInstruction inst : currentIR) {
            System.out.println("   " + inst);
        }

        // Step 4: Emit Highly Efficient Assembly Code
        System.out.println("\n[Phase 3] Emitting Hardware Assembly Code:");
        BackendMemoryLayout hardwareLayout = new BackendMemoryLayout();
        CodeGenerator backend = new CodeGenerator();
        backend.compile(currentIR, hardwareLayout);

        System.out.println("\n🎉 DUAL-PASS OPTIMIZATION PIPELINE SUCCESSFUL.");
    }
}
