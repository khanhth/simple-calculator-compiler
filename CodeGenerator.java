// ============================================================================
// 8. VISITOR 3 IMPLEMENTATION: TARGET CODE GENERATOR (Synthesis Phase)
// ============================================================================

// NOTE on class header: Return type is `String` (Register Name), Context type is `Void` (No state needed)
class CodeGenerator implements ASTVisitor<String, BackendMemoryLayout> {
    private int registerCount = 0;

    private String nextRegister() {
        return "r" + (registerCount++);
    }

    public void compile(Exp exp, BackendMemoryLayout layout) {
        exp.accept(this, layout);
    }

    @Override
    public String visit(NumExp n, BackendMemoryLayout layout) {
        String reg = nextRegister();
        System.out.println("LOADI " + reg + ", " + n.value);
        return reg;
    }

    @Override
    public String visit(IdExp id, BackendMemoryLayout layout) {
        String reg = nextRegister();
        // The backend reads its own layout map using the abstract ID handle
        int offset = layout.getOrAllocateOffset(id.varId);
        System.out.println("LOAD  " + reg + ", [fp + " + offset + "]  ; Loaded " + id.varId);
        return reg;
    }

    @Override
    public String visit(OpExp op, BackendMemoryLayout layout) {
        // Crucial: Pass 'layout' instead of 'null' so child identifiers can read memory
        // offsets
        // Evaluate child branches recursively using double-dispatch
        String leftReg = op.left.accept(this, layout);
        String rightReg = op.right.accept(this, layout);

        String resultReg = nextRegister();
        String inst = (op.operator == OpExp.PLUS) ? "ADD   " : "MUL   ";
        System.out.println(inst + resultReg + ", " + leftReg + ", " + rightReg);
        return resultReg;
    }
}
