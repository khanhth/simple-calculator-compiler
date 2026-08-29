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

    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode(null);

        // Parse list of variable declarations separated by semicolons: int x; int y;
        while (lexer.token.kind == TokenKind.INT) {
            lexer.match(TokenKind.INT);
            String name = (String) lexer.token.val;
            lexer.match(TokenKind.ID);
            lexer.match(TokenKind.SEMI); // Consume semicolon anchor

            context.registerVariable(name, "int"); // Auto-register
            program.declarations.add(new VarDeclStmt(name, "int"));
        }

        // Drop into main math expression
        Exp mathExpression = E();
        lexer.match(TokenKind.EOF);

        return new ProgramNode(mathExpression);
    }

    private Exp E() {
        return Eprime(T());
    }

    private Exp Eprime(Exp a) {
        if (lexer.token.kind == TokenKind.PLUS) {
            lexer.match(TokenKind.PLUS);
            return Eprime(new OpExp(a, OpExp.PLUS, T())); // Structural left-associative binding
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
        return a;
    }

    private Exp F() {
        if (lexer.token.kind == TokenKind.ID) {
            String name = (String) lexer.token.val;
            lexer.match(TokenKind.ID);
            VarID varId = context.getSymbolTable().lookup(name);
            if (varId == null) {
                throw new RuntimeException("Compile Error: Variable '" + name + "' used before declaration.");
                // if you want it to fail for undeclared variables here instead:
                // throw new RuntimeException("Compile Error: var. " + name + " not declared.");
            }

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
