class IRInstruction {
    enum Op {
        ADD, MUL, DECL
    }

    final Op op;
    final IROperand target, left, right, declVar;

    IRInstruction(Op op, IROperand target, IROperand left, IROperand right) {
        this.op = op;
        this.target = target;
        this.left = left;
        this.right = right;
        this.declVar = null;
    }

    IRInstruction(IROperand declVar) {
        this.op = Op.DECL;
        this.target = null;
        this.left = null;
        this.right = null;
        this.declVar = declVar;
    }

    @Override
    public String toString() {
        if (op == Op.DECL) {
            return declVar.toString();
        }
        String sym = (op == Op.ADD) ? "+" : "*";
        return target.toString() + " = " + left.toString() + " " + sym + " " + right.toString();
    }
}
