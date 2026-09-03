// ============================================================================
// 7. VISITOR 2 IMPLEMENTATION: SEMANTIC ANALYZER
// ============================================================================
// Return type is now DataType, Context remains TypeContext
class TypeChecker implements ASTVisitor<DataType, TypeContext> {

    public DataType check(Exp exp, TypeContext ctx) {
        return exp.accept(this, ctx);
    }

    @Override
    public DataType visit(NumExp n, TypeContext ctx) {
        // A literal number node always synthesizes to an INT type
        return DataType.INT;
    }

    @Override
    public DataType visit(IdExp id, TypeContext ctx) {
        // Navigate through the unified compiler context to get the type metadata
        String declaredType = ctx.compilerCtx.getSymbolTable().getTypeOf(id.varId);

        if ("string".equals(declaredType))
            return DataType.STRING;
        return DataType.INT;
    }

    @Override
    public DataType visit(OpExp op, TypeContext ctx) {
        // 1. Recursively discover the types of the child branches
        DataType leftType = op.left.accept(this, ctx);
        DataType rightType = op.right.accept(this, ctx);

        // 2. Type Compliance Check for Multiplication (*)
        if (op.op == OpExp.TIMES) {
            if (leftType == DataType.STRING || rightType == DataType.STRING) {
                System.err.println("❌ Semantic Error: Cannot mathematically multiply a STRING!");
                return DataType.ERROR; // Bubble the error type up the tree
            }
        }

        // 3. Type Compliance Check for Addition (+)
        if (op.op == OpExp.PLUS) {
            // Allow string concatenation if both sides are strings, or standard addition
            // for ints
            if (leftType == DataType.INT && rightType == DataType.INT) {
                return DataType.INT;
            } else if (leftType == DataType.STRING && rightType == DataType.STRING) {
                return DataType.STRING; // Type synthesis: String + String = String
            } else {
                System.err.println("❌ Semantic Error: Type Mismatch! Cannot add " + leftType + " and " + rightType);
                return DataType.ERROR;
            }
        }

        return DataType.INT;
    }

    @Override
    public DataType visit(VarDeclStmt decl, TypeContext ctx) {
        return null; // No register result for variable declarations
    }
}
