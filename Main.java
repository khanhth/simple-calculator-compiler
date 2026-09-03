import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Punctuated C-Style code string featuring valid semicolons!
        String sourceCode = "int x; int y; int z; x * 3 + y";
        System.out.println("--- Executing Decoupled, Semicolon-Punctured Compiler Engine ---");

        // Step 1: Initialize modern empty (sandbox?) context session
        CompilerContext context = new CompilerContext();

        // Step 2: Feed source code to Lexer & Parser (front-end layers?)
        Lexer lexer = new Lexer(sourceCode);
        ASTParser parser = new ASTParser(lexer, context); // Automatically binds to context

        // 3. Synthesize the text into a Program Tree block
        System.out.println("\n1. Lexing & Parsing Source Text...");
        ProgramNode programTree = parser.parseProgram();
        new ASTPrinter().print(programTree); // Visualize the AST structure
        // System.out.println("👉 Variable mapping and registration achieved
        // successfully!");
        System.out.println("👉 Automated Variable Discovery and Parsing successful!");

        // Step 3: Flatten hierarchical math tree down to target-agnostic 3AC Linear IR
        // Form (IR Code?)
        System.out.println("\n2. Lowering AST to 3-Address Code (IR Stream):");
        IRGenerator irGen = new IRGenerator();
        List<IRInstruction> intermediateCode = irGen.generate(programTree);
        for (IRInstruction inst : intermediateCode) {
            System.out.println(" " + inst);
        }
        System.out.println("\n🎉 COMPILATION SUCCESSFUL: IR generation complete!");
    }
}
