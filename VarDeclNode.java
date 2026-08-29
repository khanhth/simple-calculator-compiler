class VarDeclNode extends Exp {
    final String name;
    final String type;

    VarDeclNode(String n, String t) {
        this.name = n;
        this.type = t;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return null;
    }
}
