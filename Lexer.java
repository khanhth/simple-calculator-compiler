class Lexer {
    private final String input;
    private int position = 0;
    public Token token; // Holds the current active token

    public Lexer(String input) {
        this.input = input;
        this.advance(); // Bootstrap the lexer by reading the first token
    }

    // Read the next character in the stream
    private char peek() {
        if (position >= input.length()) return '\0';
        return input.charAt(position);
    }

    // Move to the next character in the stream
    private void consumeChar() {
        position++;
    }

    // Core method: Scans the string to construct the next Token object
    public void advance() {
        while (peek() != '\0' && Character.isWhitespace(peek())) {
            consumeChar(); // Skip whitespace spaces, tabs, and newlines
        }

        char current = peek();

        // Base case: Reached the end of the input string
        if (current == '\0') {
            token = new Token(TokenKind.EOF, null);
            return;
        }

        // 1. Lexing Math Operators & Parentheses
        if (current == '+') {
            consumeChar();
            token = new Token(TokenKind.PLUS, null);
            return;
        }
        if (current == '*') {
            consumeChar();
            token = new Token(TokenKind.TIMES, null);
            return;
        }
        if (current == '(') {
            consumeChar();
            token = new Token(TokenKind.LPAREN, null);
            return;
        }
        if (current == ')') {
            consumeChar();
            token = new Token(TokenKind.RPAREN, null);
            return;
        }

        // 2. Lexing NUM Tokens (Digits)
        if (Character.isDigit(current)) {
            StringBuilder buffer = new StringBuilder();
            while (Character.isDigit(peek())) {
                buffer.append(peek());
                consumeChar();
            }
            int integerValue = Integer.parseInt(buffer.toString());
            token = new Token(TokenKind.NUM, integerValue); // Carrying integer value
            return;
        }

        // 3. Lexing ID Tokens (Letters/Variable Names)
        if (Character.isLetter(current)) {
            StringBuilder buffer = new StringBuilder();
            while (Character.isLetterOrDigit(peek())) {
                buffer.append(peek());
                consumeChar();
            }
            String identifierName = buffer.toString();
            token = new Token(TokenKind.ID, identifierName); // Carrying string value
            return;
        }

        throw new RuntimeException("Lexical Error: Unknown character '" + current + "' at index " + position);
    }

    // Matches the current token kind and advances the stream
    public void match(TokenKind expected) {
        if (token.kind == expected) {
            advance();
        } else {
            throw new RuntimeException("Syntax Error: Expected " + expected + " but got " + token.kind);
        }
    }
}
