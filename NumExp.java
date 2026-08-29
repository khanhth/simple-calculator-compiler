class NumExp extends Exp {
    public final int val;

    public NumExp(int v) {
        this.val = v;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context);
    }
}
