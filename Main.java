public class Main {
    public static void main(String[] args) {
        // Input text containing equations that can be pre-calculated: e.g., 5 * 3 + y
        String sourceCode = "int x; int y; x = 5; y = 10; 5 * 3 + y;";
        System.out.println("--- Starting Modern Optimizing Compiler Pipeline ---\n");

        CompilerContext context = new CompilerContext();
        Lexer lexer = new Lexer(sourceCode);
        ASTParser parser = new ASTParser(lexer, context);
        ProgramNode programTree = parser.parseProgram();

        System.out.println("[Phase 1] Processing source text definitions...");
        new ASTPrinter().print(programTree); // Visualize the AST structure

        // 1. Generate Raw IR
        IRGenerator irGen = new IRGenerator();
        java.util.List<IRInstruction> rawIR = irGen.generate(programTree);

        System.out.println("[Phase 2] Original Generated IR Stream:");
        for (IRInstruction inst : rawIR) {
            System.out.println("   " + inst);
        }

        // 2. NEW PASS: Run Middle-End Optimization Code
        System.out.println("\n[Phase 2.5] Running Constant Folding Optimization Pass...");
        IROptimizer optimizer = new IROptimizer();
        java.util.List<IRInstruction> optimizedIR = optimizer.optimize(rawIR);

        System.out.println("\n[Phase 2.6] Optimized IR Stream:");
        for (IRInstruction inst : optimizedIR) {
            System.out.println("   " + inst);
        }

        // 3. Compile Backend from the Optimized Stream!
        System.out.println("\n[Phase 3] Generated Hardware Assembly Code (Optimized Backend):");
        BackendMemoryLayout hardwareLayout = new BackendMemoryLayout();
        CodeGenerator backend = new CodeGenerator();

        // Pass the optimizedIR list downstream instead of rawIR!
        backend.compile(optimizedIR, hardwareLayout);

        System.out.println("\n🎉 OPTIMIZING COMPILATION PIPELINE SUCCESSFUL.");
    }
}
