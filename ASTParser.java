// Grammar:
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
    private final FrontendSymbolTable symbolTable; // 1. Added frontend table reference

    // 2. Inject the FrontendSymbolTable via the constructor
    public ASTParser(Lexer lexer, FrontendSymbolTable symbolTable) {
        this.lexer = lexer;
        this.symbolTable = symbolTable;
    }

    public Exp parse() {
        return E();
    }

    private Exp E() {
        return Eprime(T());
    }

    private Exp Eprime(Exp a) {
        if (lexer.token.kind == TokenKind.PLUS) {
            lexer.match(TokenKind.PLUS);
            return Eprime(new OpExp(a, OpExp.PLUS, T()));
        }
        return a;
    }

    private Exp T() {
        return Tprime(F());
    }

    private Exp Tprime(Exp a) {
        if (lexer.token.kind == TokenKind.TIMES) {
            lexer.match(TokenKind.TIMES);
            return Tprime(new OpExp(a, OpExp.TIMES, F()));
        }
        return a;
    }

    private Exp F() {
        if (lexer.token.kind == TokenKind.ID) {
            String name = (String) lexer.token.value;
            lexer.match(TokenKind.ID);

            // 3. LOOKUP THE VARID: Convert the raw string name into an abstract VarID
            VarID varId = symbolTable.lookup(name);
            if (varId == null) {
                // If your language allows automatic dynamic declaration upon usage:
                varId = symbolTable.declare(name, "int");

                // OR if you want it to fail for undeclared variables here instead:
                // throw new RuntimeException("Compile Error: Variable '" + name + "' not
                // declared.");
            }

            // 4. FIXED LINE: Pass the clean abstract VarID to IdExp instead of the string!
            return new IdExp(varId);
        } else if (lexer.token.kind == TokenKind.NUM) {
            int value = (Integer) lexer.token.value;
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
