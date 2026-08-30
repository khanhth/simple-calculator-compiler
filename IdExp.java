// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

class IdExp extends Exp {
    public final VarID varId; // Pure abstract reference handle

    public IdExp(VarID varId) {
        this.varId = varId;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context);
    }
}
