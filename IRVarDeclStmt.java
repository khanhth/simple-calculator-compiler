// @deprecated: This class is no longer used in the current implementation. Variable declarations are now represented by VarDeclStmt nodes instead of VarDeclNode nodes.
class IRVarDeclStmt extends IROperand {
    final String name;
    final String type;

    IRVarDeclStmt(String n, String t) {
        this.name = n;
        this.type = t;
    }

    @Override
    public String toString() {
        return "declStmt: {" + name + ":" + type + "}";
    }
}
