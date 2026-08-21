// ============================================================================
// 1. TOKENS & LEXER IMPLEMENTATION (Lexical Analysis)
// =======================================================================
enum TokenKind {
    ID, NUM, PLUS, TIMES, LPAREN, RPAREN, EOF
}

final class Token {
    final TokenKind kind;
    final Object value;

    Token(TokenKind kind, Object value) {
        this.kind = kind;
        this.value = value;
    }
}
