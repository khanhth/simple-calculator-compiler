// ============================================================================
// 7. VISITOR 2 IMPLEMENTATION: SEMANTIC ANALYZER
// ============================================================================
// Return type is now DataType, Context remains TypeContext
// Context state payload passing the global compiler context
// Full-program type compliance verifier
class TypeChecker implements ASTVisitor<DataType, TypeContext> {

    // ─── NEW ENHANCED PROGRAM ENTRANCE GUARD ───
    public boolean check(ProgramNode program, TypeContext ctx) {
        // Iterate through each sequential line in the script
        for (Statement stmt : program.statements) {

            // Scenario A: It's an assignment statement (e.g., x = 5;)
            if (stmt instanceof AssignStmt) {
                AssignStmt assign = (AssignStmt) stmt;

                // 1. Look up the designated data type of the left-hand variable
                String varTypeStr = ctx.compilerCtx.getSymbolTable().getTypeOf(assign.varId);
                DataType expectedType = "string".equals(varTypeStr) ? DataType.STRING : DataType.INT;

                // 2. Synthesize the type of the right-hand math expression bottom-up
                DataType expressionType = assign.valueExpr.accept(this, ctx);

                // 3. Enforce assignment alignment compatibility
                if (expressionType == DataType.ERROR)
                    return false;
                if (expectedType != expressionType) {
                    System.err.println("❌ Semantic Error: Type Mismatch! Cannot assign " + expressionType + " to a "
                            + expectedType + " variable.");
                    return false;
                }
            }

            // Scenario B: It's a standard mathematical statement (e.g., x * 3 + y;)
            else if (stmt instanceof ExprStmt) {
                ExprStmt exprStmt = (ExprStmt) stmt;
                DataType resultType = exprStmt.expression.accept(this, ctx);
                if (resultType == DataType.ERROR)
                    return false;
            }
        }
        return true; // The entire file is semantically safe!
    }

    // ─── UNCHANGED INDIVIDUAL EXPRESSION VISITORS ───
    @Override
    public DataType visit(NumExp n, TypeContext ctx) {
        return DataType.INT;
    }

    @Override
    public DataType visit(IdExp id, TypeContext ctx) {
        String declaredType = ctx.compilerCtx.getSymbolTable().getTypeOf(id.varId);
        if ("string".equals(declaredType))
            return DataType.STRING;
        return DataType.INT;
    }

    @Override
    public DataType visit(OpExp op, TypeContext ctx) {
        DataType leftType = op.left.accept(this, ctx);
        DataType rightType = op.right.accept(this, ctx);

        if (leftType == DataType.ERROR || rightType == DataType.ERROR)
            return DataType.ERROR;

        if (op.op == OpExp.TIMES) {
            if (leftType == DataType.STRING || rightType == DataType.STRING) {
                System.err.println("❌ Semantic Error: Cannot mathematically multiply strings!");
                return DataType.ERROR;
            }
            return DataType.INT;
        }

        if (op.op == OpExp.PLUS) {
            if (leftType == DataType.INT && rightType == DataType.INT)
                return DataType.INT;
            if (leftType == DataType.STRING && rightType == DataType.STRING)
                return DataType.STRING;

            System.err.println("❌ Semantic Error: Type Mismatch! Cannot add " + leftType + " and " + rightType);
            return DataType.ERROR;
        }

        return DataType.INT;
    }
}
