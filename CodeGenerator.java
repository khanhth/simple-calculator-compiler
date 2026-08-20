// ============================================================================
// 7. VISITOR IMPLEMENTATION: TARGET CODE GENERATOR
// ============================================================================
// Return type is String (Register Name), Context type is Void (No state needed)
class CodeGenerator implements ASTVisitor<String, Void> {
    private int registerCount = 0;

    private String nextRegister() {
        return "r" + (registerCount++);
    }

    public void compile(Exp exp) {
        exp.accept(this, null);
    }

    @Override
    public String visit(NumExp n, Void ctx) {
        String reg = nextRegister();
        System.out.println("LOADI " + reg + ", " + n.value);
        return reg;
    }

    @Override
    public String visit(IdExp id, Void ctx) {
        String reg = nextRegister();
        int offset = AddressTable.getOffset(id.name);
        System.out.println("LOAD  " + reg + ", [fp + " + offset + "]");
        return reg;
    }

    @Override
    public String visit(OpExp op, Void ctx) {
        // Evaluate child branches recursively using double-dispatch
        String leftReg = op.left.accept(this, null);
        String rightReg = op.right.accept(this, null);
        String resultReg = nextRegister();

        if (op.operator == OpExp.PLUS) {
            System.out.println("ADD   " + resultReg + ", " + leftReg + ", " + rightReg);
        } else if (op.operator == OpExp.TIMES) {
            System.out.println("MUL   " + resultReg + ", " + leftReg + ", " + rightReg);
        }
        return resultReg;
    }
}
