// ============================================================================
// 6. VISITOR 1 IMPLEMENTATION: STATELESS AST PRINTER (Visualization Phase)
// ============================================================================

// Return type is Void (prints directly), Context type is PrintContext
class ASTPrinter implements ASTVisitor<Void, PrintContext> {

    // ─── NEW ENHANCED PROGRAM LAUNCHER ───
    public void print(ProgramNode program) {
        System.out.println("ProgramNode");
        int size = program.statements.size();
        for (int i = 0; i < size; i++) {
            Statement stmt = program.statements.get(i);
            boolean isLastStmt = (i == size - 1);
            printStatement(stmt, "├── ", isLastStmt ? "    " : "│   ");
        }
    }

    private void printStatement(Statement stmt, String prefix, String childIndent) {
        if (stmt instanceof AssignStmt) {
            AssignStmt assign = (AssignStmt) stmt;
            System.out.println(prefix + "AssignStmt (=)");
            System.out.println(childIndent + "├── VarID (" + assign.varId + ")");
            System.out.print(childIndent + "└── ");
            // Delegate the expression to the visitor double-dispatch pattern
            assign.valueExpr.accept(this, new PrintContext(childIndent + "    "));
        } else if (stmt instanceof ExprStmt) {
            ExprStmt exprStmt = (ExprStmt) stmt;
            System.out.println(prefix + "ExprStmt");
            System.out.print(childIndent + "└── ");
            exprStmt.expression.accept(this, new PrintContext(childIndent + "    "));
        }
    }

    // ─── UNCHANGED STRUCTURAL EXPRESSION VISITORS ───
    @Override
    public Void visit(NumExp n, PrintContext ctx) {
        System.out.println("NUM (" + n.val + ")");
        return null;
    }

    @Override
    public Void visit(IdExp id, PrintContext ctx) {
        System.out.println("ID (" + id.varId + ")");
        return null;
    }

    @Override
    public Void visit(OpExp op, PrintContext ctx) {
        String opSymbol = (op.op == OpExp.PLUS) ? "PLUS (+)" : "TIMES (*)";
        System.out.println("OP (" + opSymbol + ")");

        System.out.print(ctx.indent + "├── ");
        op.left.accept(this, new PrintContext(ctx.indent + "│   "));

        System.out.print(ctx.indent + "└── ");
        op.right.accept(this, new PrintContext(ctx.indent + "    "));
        return null;
    }
}
