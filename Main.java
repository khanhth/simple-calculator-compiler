public class Main {
    public static void main(String[] args) {
        String sourceCode = "x * 3 + y";
        System.out.println("--- Executing Modern Context-Driven Compiler Pipeline ---");

        // 1. Initialize a unified compiler session context
        CompilerContext context = new CompilerContext();

        // Populate source definitions through a single interface
        context.registerVariable("x", "int");
        context.registerVariable("y", "int");

        // 2. Lexical & Syntax Parsing
        Lexer lexer = new Lexer(sourceCode);
        ASTParser parser = new ASTParser(lexer, context.getSymbolTable()); // Fed from unified context
        Exp astRoot = parser.parse();
        System.out.println("[Step 1] Parsing complete. Abstract tree generated.");

        // 2. Visual Layout Phase
        System.out.print("[Step 2] AST Tree Visual: ");
        new ASTPrinter().print(astRoot);

        // 3. Structural Validation (Type Checker)
        System.out.println("[Step 3] Running Type Checker Validation...");
        TypeChecker checker = new TypeChecker();
        TypeContext typeState = new TypeContext(context); // Binds the session context to semantic pass
        DataType finalType = checker.check(astRoot, typeState);

        if (finalType == DataType.ERROR) {
            System.out.println("🛑 COMPILATION HALTED: Semantic validation failed.");
            return;
        }
        System.out.println("👉 Semantic Analysis Clean.");

        // 4. Backend Lowering
        System.out.println("[Step 4] Lowering to Target Hardware Assembly:");
        BackendMemoryLayout hardwareLayout = new BackendMemoryLayout();
        CodeGenerator codegen = new CodeGenerator();
        codegen.compile(astRoot, hardwareLayout);

        System.out.println("\n🎉 COMPILATION SUCCESSFUL!");
    }
}
