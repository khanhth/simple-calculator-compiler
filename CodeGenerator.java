// ============================================================================
// 8. VISITOR 3 IMPLEMENTATION: TARGET CODE GENERATOR (Synthesis Phase)
// ============================================================================

// NOTE on class header: Return type is `String` (Register Name), Context type is `Void` (No state needed)
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
        String inst = (op.operator == OpExp.PLUS) ? "ADD   " : "MUL   ";
        System.out.println(inst + resultReg + ", " + leftReg + ", " + rightReg);
        return resultReg;
    }
}
