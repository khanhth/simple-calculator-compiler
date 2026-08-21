// ============================================================================
// 7. VISITOR 2 IMPLEMENTATION: SEMANTIC ANALYZER (Type and Initialization Verification)
// ============================================================================

// NOTE on class header: Return type is `Boolean` (Is Valid?), Context type is `TypeContext`
class TypeChecker implements ASTVisitor<Boolean, TypeContext> {

    public boolean check(Exp exp, TypeContext ctx) {
        return exp.accept(this, ctx);
    }

    @Override
    public Boolean visit(NumExp n, TypeContext ctx) {
        // Literal numbers are always valid and type-safe
        return true;
    }

    @Override
    public Boolean visit(IdExp id, TypeContext ctx) {
        // Check if the variable name has been registered in our initialized context
        if (!ctx.initializedVariables.contains(id.name)) {
            System.err.println("❌ Semantic Error: Variable '" + id.name + "' used but never declared!");
            return false;
        }
        return true;
    }

    @Override
    public Boolean visit(OpExp op, TypeContext ctx) {
        // Recursively verify both sides of the mathematical operator are valid
        boolean leftValid = op.left.accept(this, ctx);
        boolean rightValid = op.right.accept(this, ctx);

        return leftValid && rightValid;
    }
}
