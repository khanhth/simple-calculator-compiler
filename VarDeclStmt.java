// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

// High-Level Program structures
class VarDeclStmt extends Statement {
    final String varName;
    final String type;

    VarDeclStmt(String name, String type) {
        this.varName = name;
        this.type = type;
    }
}
