// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

// High-Level Program structures
class VarDeclStmt extends Statement {
    final VarID varID;
    final String type;

    VarDeclStmt(VarID varID, String type) {
        this.varID = varID;
        this.type = type;
    }
}
