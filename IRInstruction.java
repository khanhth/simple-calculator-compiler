// ============================================================================
// (Relating to) THREE-ADDRESS CODE (3AC) INTERMEDIATE REPRESENTATION STRUCTURES
// ============================================================================

class IRInstruction {
    // MODERN FIX: Explicit Enum Configuration for ASSIGN, ADD, and MUL operators
    enum Op {
        ASSIGN, ADD, MUL
    }

    final Op op;
    final IROperand target;
    final IROperand left;
    final IROperand right;

    IRInstruction(Op op, IROperand target, IROperand left, IROperand right) {
        this.op = op;
        this.target = target;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        switch (op) {
            case ASSIGN:
                return target + " = " + left;
            case ADD:
                return target + " = " + left + " + " + right;
            case MUL:
                return target + " = " + left + " * " + right;
            default:
                return "";
        }
    }
}
