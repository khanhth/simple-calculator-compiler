public class Main {
    public static void main(String[] args) {
        // The source code file can now change variables dynamically mid-flight!
        String sourceCode = "int x; int y; x = 5; y = 10; x * 3 + y;";
        // String sourceCode = "int z; int y; x = 5; y = 10; x * 3 + y;"; // <= throws compile error for undeclared variable 'x'
        System.out.println("--- Starting Modern Unified Decoupled 3AC Compiler ---\n");

        CompilerContext context = new CompilerContext();
        Lexer lexer = new Lexer(sourceCode);
        ASTParser parser = new ASTParser(lexer, context);

        System.out.println("[Phase 1] Processing source text definitions...");
        ProgramNode programTree = parser.parseProgram();

        // TODO: Add implementation for ASTPrinter to visualize the AST structure
        new ASTPrinter().print(programTree); // Visualize the AST structure

        // Generate IR from the entire program node statements sequence
        System.out.println("\n[Phase 2] Generated 3-Address Code (IR Stream List):");
        IRGenerator irGen = new IRGenerator();
        // FIXED LINE: Explicitly use java.util.List here
        java.util.List<IRInstruction> flatIR = irGen.generate(programTree);
        for (IRInstruction inst : flatIR) {
            System.out.println("   " + inst);
        }

        System.out.println("\n[Phase 3] Generated Physical Hardware Assembly Code:");
        BackendMemoryLayout hardwareLayout = new BackendMemoryLayout();
        CodeGenerator backend = new CodeGenerator();
        backend.compile(flatIR, hardwareLayout);

        System.out.println("\n🎉 COMPILATION PIPELINE SUCCESSFUL.");
    }

}
