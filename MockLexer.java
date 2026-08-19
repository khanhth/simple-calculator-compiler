final class MockLexer {
    private final Token[] tokens;
    private int index;
    Token token;

    MockLexer(Token[] tokens) {
        if (tokens.length == 0) {
            throw new IllegalArgumentException("Token stream cannot be empty");
        }
        this.tokens = tokens;
        this.token = tokens[0];
    }

    void advance() {
        if (index < tokens.length - 1) {
            token = tokens[++index];
        }
    }

    void match(TokenKind expected) {
        if (token.kind != expected) {
            throw new RuntimeException("Syntax Error: Expected " + expected + " but got " + token.kind);
        }
        advance();
    }
}