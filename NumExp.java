final class NumExp extends Exp {
    public final int value;

    NumExp(int value) {
        this.value = value;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context);
    }
}
