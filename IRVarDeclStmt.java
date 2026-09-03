// @deprecated: This class is no longer used in the current implementation. Variable declarations are now represented by VarDeclStmt nodes instead of VarDeclNode nodes.
class IRVarDeclStmt extends IROperand {
    final VarID varID;
    final String type;

    IRVarDeclStmt(VarID varID, String t) {
        this.varID = varID;
        this.type = t;
    }

    @Override
    public String toString() {
        return "declStmt: {" + varID + ":" + type + "}";
    }
}
