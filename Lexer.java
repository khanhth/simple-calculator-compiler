// ============================================================================
// 2. THE LEXER (With Semicolon, Assignment Op, and Lookahead Peek)
// ============================================================================
class Lexer {
    private final String input;
    private int position = 0;
    public Token token; // Holds the current active token

    public Lexer(String input) {
        this.input = input;
        this.advance(); // Bootstrap the lexer by reading the first token (lookahead?)
    }

    // Read the next character in the stream
    private char peekChar() {
        if (position >= input.length()) {
            return '\0';
        }
        return input.charAt(position);
    }

    // Move to the next character in the stream
    private void consumeChar() {
        position++;
    }

    // Core method: Scans the string to construct the next Token object
    public void advance() {
        while (peekChar() != '\0' && Character.isWhitespace(peekChar())) {
            consumeChar(); // Skip whitespace spaces, tabs, and newlines
        }

        char current = peekChar();

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
        if (current == ';') {
            consumeChar();
            token = new Token(TokenKind.SEMI, null);
            return;
        } // Semicolon support

        if (current == '=') {
            consumeChar();
            token = new Token(TokenKind.ASSIGN_OP, null);
            return;
        } // Assignment operator support

        if (Character.isDigit(current)) {
            StringBuilder buffer = new StringBuilder();
            while (Character.isDigit(peekChar())) {
                buffer.append(peekChar());
                consumeChar();
            }
            token = new Token(TokenKind.NUM, Integer.parseInt(buffer.toString()));
            return;
        }

        // 3. Lexing ID Tokens (Letters/Variable Names)
        if (Character.isLetter(current)) {
            StringBuilder buffer = new StringBuilder();
            while (Character.isLetterOrDigit(peekChar())) {
                buffer.append(peekChar());
                consumeChar();
            }
            String lexeme = buffer.toString();

            // ─── RESERVED KEYWORD LOOKUP ───
            if ("int".equals(lexeme)) {
                token = new Token(TokenKind.INT, null);
            } else {
                token = new Token(TokenKind.ID, lexeme); // Generic variable name
            }
            // TODO:
            // Check the else branch for other cases where the lexeme is not "int" but also
            // not a variable name.
            // For example, if the lexeme is a reserved keyword like "if" or "while", we
            // should handle those cases as well.
            return;
        }

        throw new RuntimeException("Lexical Error: Unknown character '" + current + "' at index " + position);
    }

    // MODERN FIX: Simple lookahead peek to determine if an '=' follows an
    // identifier name
    public TokenKind peekNextTokenKind() {
        int savedPosition = this.position;
        while (savedPosition < input.length() && Character.isWhitespace(input.charAt(savedPosition))) {
            savedPosition++;
        }
        if (savedPosition < input.length() && input.charAt(savedPosition) == '=') {
            return TokenKind.ASSIGN_OP;
        }
        return TokenKind.EOF;
    }

    public void match(TokenKind expected) {
        if (token.kind == expected) {
            advance();
        } else {
            throw new RuntimeException("Syntax Error: Expected " + expected + " but got " + token.kind);
        }
    }
}
