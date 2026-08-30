// ============================================================================
// 1. DATA INFRASTRUCTURE & TOKENS (Lexical Infrastructure)
// ============================================================================
enum TokenKind {
    ID, NUM, PLUS, MINUS, TIMES, LPAREN, RPAREN, EOF, INT, SEMI, ASSIGN_OP
}

class Token {
    final TokenKind kind;
    final Object val;

    Token(TokenKind k, Object v) {
        this.kind = k;
        this.val = v;
    }
}
