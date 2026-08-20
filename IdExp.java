final class IdExp extends Exp {
    public final String name;

    IdExp(String name) {
        this.name = name;
    }

    @Override
    public <R, C> R accept(ASTVisitor<R, C> v, C context) {
        return v.visit(this, context); // Double dispatch: calls the IdExp visit method
    }
}