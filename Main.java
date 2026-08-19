public class Main {
    public static void main(String[] args) {
        // Mock token stream representing the string expression: x * 3 + y
        Token[] stream = {
                new Token(TokenKind.ID, "x"),
                new Token(TokenKind.TIMES, null),
                new Token(TokenKind.NUM, 3),
                new Token(TokenKind.PLUS, null),
                new Token(TokenKind.ID, "y"),
                new Token(TokenKind.EOF, null)
        };

        System.out.println("--- Starting Compiler Pipeline for 'x * 3 + y' ---");

        // 1. Init Lexer & Parser
        MockLexer lexer = new MockLexer(stream);
        ASTParser parser = new ASTParser(lexer);

        // 2. Parse token stream into an Abstract Syntax Tree (AST)
        System.out.println("\n[Step 1] Parsing tokens into AST...");
        Exp astRoot = parser.parse();
        System.out.println(
                "Successfully generated complete AST root node object (" + astRoot.getClass().getSimpleName() + ").");

        // 3. Lower AST into Assembly Code via Target Code Generator
        System.out.println("\n[Step 2] Traversing AST to emit hardware assembly instructions:");
        CodeGenerator codegen = new CodeGenerator();
        codegen.compile(astRoot);

        System.out.println("\n--- Compilation Finished ---");
    }
}
