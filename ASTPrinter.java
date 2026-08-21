// ============================================================================
// 6. VISITOR 1 IMPLEMENTATION: STATELESS AST PRINTER (Visualization Phase)
// ============================================================================

// Return type is Void (prints directly), Context type is PrintContext
class ASTPrinter implements ASTVisitor<Void, PrintContext> {

    public void print(Exp exp) {
        // Start traversal with an initial stack-allocated context frame
        exp.accept(this, new PrintContext("", true));
        // TODO: KTR to check why `isLast` is set to true here, see commented code below
        // exp.accept(this, new PrintContext(""));
    }

    @Override
    public Void visit(NumExp n, PrintContext ctx) {
        System.out.println(ctx.indent + "NUM (" + n.value + ")");
        // TODO: KTR to check what happens if we remove `ctx.indent` here, see commented code below
        // System.out.println("NUM (" + n.value + ")");
        return null;
    }

    @Override
    public Void visit(IdExp id, PrintContext ctx) {
        System.out.println(ctx.indent + "ID (\"" + id.name + "\")");
        // TODO: KTR to check what happens if we remove `ctx.indent` here, see commented code below
        // System.out.println("\"" + id.name + "\"");
        return null;
    }

    @Override
    public Void visit(OpExp op, PrintContext ctx) {
        // 1. Print the current node using its own pre-calculated indent and branch
        // symbol
        System.out.println(ctx.indent + "OP (" + ((op.operator == OpExp.PLUS) ? "PLUS (+)" : "TIMES (*)") + ")");

        // 2. Derive the correct prefix line for our children based on whether we are
        // last or not
        String childPrefix = ctx.indent + (ctx.isLast ? "    " : "│   ");

        // 3. Explicitly construct the exact visual path for the left and right child
        // nodes
        op.left.accept(this, new PrintContext(childPrefix + "├── ", false));
        op.right.accept(this, new PrintContext(childPrefix + "└── ", true));
        return null;
    }
}
