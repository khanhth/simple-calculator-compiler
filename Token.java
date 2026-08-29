// ============================================================================
// 1. DATA INFRASTRUCTURE & TOKENS (Lexical Analysis)
// ============================================================================
enum TokenKind {
    ID, NUM, PLUS, TIMES, LPAREN, RPAREN, EOF, INT, SEMI
}

class Token {
    final TokenKind kind;
    final Object val;

    Token(TokenKind k, Object v) {
        this.kind = k;
        this.val = v;
    }
}
