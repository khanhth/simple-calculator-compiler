// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

class AssignStmt extends Statement {
    final VarID varId;
    final Exp valueExpr;

    AssignStmt(VarID varId, Exp valueExpr) {
        this.varId = varId;
        this.valueExpr = valueExpr;
    }
}
