// High-Level Program structures
class VarDeclStmt {
    final String varName;
    final String type;

    VarDeclStmt(String name, String type) {
        this.varName = name;
        this.type = type;
    }

    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context);
    }
}
