class OpExp extends Exp {
    static final int PLUS = 1;
    static final int TIMES = 2;

    final Exp left;
    final Exp right;
    final int op;

    public OpExp(Exp l, int o, Exp r) {
        this.left = l;
        this.op = o;
        this.right = r;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context); // Double dispatch: calls the OpExp visit method
    }
}
