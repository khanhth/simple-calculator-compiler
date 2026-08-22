class IdExp extends Exp {
    final VarID varId; // Pure abstract reference handle

    IdExp(VarID varId) {
        this.varId = varId;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context);
    }
}
