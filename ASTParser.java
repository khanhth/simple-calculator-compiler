// Grammar:
// P  → DecList Expression $
// DecList → Declaration DecList | ε (can be empty)
// DecList → int ID
// S  → E $
// E  → T E'
// E' → + T E'
// E' → - T E'
// E' → ε

// T  → F T'
// T' → * F T'
// T' → / F T'
// T' → ε

// F  → id
// F  → num
// F  → ( E )
// ============================================================================
// 5. RECURSIVE-DESCENT PARSER (Syntax Analysis)
// =======================================================================
class ASTParser {
    private final Lexer lexer;
    private final CompilerContext context;

    public ASTParser(Lexer lexer, CompilerContext context) {
        this.lexer = lexer;
        this.context = context;
    }

    // Parsing Entry Point Supporting Sequential Statement Operations
    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode();

        // 1. Structural Declarations Loop
        while (lexer.token.kind == TokenKind.INT) {
            lexer.match(TokenKind.INT);
            String varName = (String) lexer.token.val;
            lexer.match(TokenKind.ID);
            lexer.match(TokenKind.SEMI); // Consume semicolon anchor

            context.registerVariable(varName, "int");
        }

        // 2. Sequential Statement Operations Loop
        while (lexer.token.kind != TokenKind.EOF) {

            // Scenario A: It's an assignment statement (e.g., x = 5;)
            if (lexer.token.kind == TokenKind.ID && lexer.peekNextTokenKind() == TokenKind.ASSIGN_OP) {
                String varName = (String) lexer.token.val;
                lexer.match(TokenKind.ID);
                lexer.match(TokenKind.ASSIGN_OP); // Match '='

                Exp value = E(); // Parse the value expression on the right
                lexer.match(TokenKind.SEMI);

                VarID id = context.getSymbolTable().lookup(varName);
                program.statements.add(new AssignStmt(id, value));
            }
            // Scenario B: It's a plain math expression evaluated for output (e.g., x + 3;)
            else {
                // TODO: Add validation to ensure that the expression is not empty and is well-formed
                Exp expr = E();
                lexer.match(TokenKind.SEMI);
                program.statements.add(new ExprStmt(expr));
            }
        }

        lexer.match(TokenKind.EOF);
        return program;
    }

    private Exp E() {
        return Eprime(T());
    }

    private Exp Eprime(Exp a) {
        if (lexer.token.kind == TokenKind.PLUS) {
            lexer.match(TokenKind.PLUS);
            return Eprime(new OpExp(a, OpExp.PLUS, T())); // Structural left-associative binding
        } else if (lexer.token.kind == TokenKind.MINUS) {
            lexer.match(TokenKind.MINUS);
            return Eprime(new OpExp(a, OpExp.MINUS, T())); // Structural left-associative binding
        }
        return a;
    }

    private Exp T() {
        return Tprime(F());
    }

    private Exp Tprime(Exp a) {
        if (lexer.token.kind == TokenKind.TIMES) {
            lexer.match(TokenKind.TIMES);
            return Tprime(new OpExp(a, OpExp.TIMES, F())); // Structural left-associative binding
        }
        // else if (lexer.token.kind == TokenKind.DIV) {
        //     lexer.match(TokenKind.DIV);
        //     return Tprime(new OpExp(a, OpExp.DIV, F())); // Structural left-associative binding
        // }
        return a;
    }

    private Exp F() {
        if (lexer.token.kind == TokenKind.ID) {
            String name = (String) lexer.token.val;
            lexer.match(TokenKind.ID);
            VarID varId = context.getSymbolTable().lookup(name);
            // if (varId == null) {
            //     throw new RuntimeException("Compile Error: Variable '" + name + "' used before declaration.");
            //     // if you want it to fail for undeclared variables here instead:
            //     // throw new RuntimeException("Compile Error: var. " + name + " not declared.");
            // }

            return new IdExp(varId);
        } else if (lexer.token.kind == TokenKind.NUM) {
            int value = (Integer) lexer.token.val;
            lexer.match(TokenKind.NUM);
            return new NumExp(value);
        } else if (lexer.token.kind == TokenKind.LPAREN) {
            lexer.match(TokenKind.LPAREN);
            Exp e = E();
            lexer.match(TokenKind.RPAREN);
            return e;
        }
        throw new RuntimeException("Syntax Error: Unexpected token " + lexer.token.kind);
    }
}
