final class ASTParser {
    private final MockLexer lexer;

    ASTParser(MockLexer lexer) {
        this.lexer = lexer;
    }

    Exp parse() {
        return parseExpression();
    }

    private Exp parseExpression() {
        return parseExpressionTail(parseTerm());
    }

    private Exp parseExpressionTail(Exp left) {
        if (lexer.token.kind != TokenKind.PLUS) {
            return left;
        }
        lexer.match(TokenKind.PLUS);
        return parseExpressionTail(new OpExp(left, OpExp.PLUS, parseTerm()));
    }

    private Exp parseTerm() {
        return parseTermTail(parseFactor());
    }

    private Exp parseTermTail(Exp left) {
        if (lexer.token.kind != TokenKind.TIMES) {
            return left;
        }
        lexer.match(TokenKind.TIMES);
        return parseTermTail(new OpExp(left, OpExp.TIMES, parseFactor()));
    }

    private Exp parseFactor() {
        switch (lexer.token.kind) {
            case ID:
                String name = (String) lexer.token.value;
                lexer.match(TokenKind.ID);
                return new IdExp(name);
            case NUM:
                int value = (Integer) lexer.token.value;
                lexer.match(TokenKind.NUM);
                return new NumExp(value);
            case LPAREN:
                lexer.match(TokenKind.LPAREN);
                Exp expression = parseExpression();
                lexer.match(TokenKind.RPAREN);
                return expression;
            default:
                throw new RuntimeException("Syntax Error: Unexpected token " + lexer.token.kind);
        }
    }
}