class IRInstruction {
    enum Op {
        ADD, MUL
    }

    final Op op;
    final IROperand target, left, right;

    IRInstruction(Op op, IROperand target, IROperand left, IROperand right) {
        this.op = op;
        this.target = target;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        String sym = (op == Op.ADD) ? "+" : "*";
        return target + " = " + left + " " + sym + " " + right;
    }
}
