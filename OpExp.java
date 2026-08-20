final class OpExp extends Exp {
    static final int PLUS = 1;
    static final int TIMES = 2;

    final Exp left;
    final Exp right;
    final int operator;

    OpExp(Exp left, int operator, Exp right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context); // Double dispatch: calls the OpExp visit method
    }
}
