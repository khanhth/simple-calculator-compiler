// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

class ExprStmt extends Statement {
    final Exp expression;

    ExprStmt(Exp e) {
        this.expression = e;
    }
}
