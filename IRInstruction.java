// ============================================================================
// (Relating to) THREE-ADDRESS CODE (3AC) INTERMEDIATE REPRESENTATION STRUCTURES
// ============================================================================

class IRInstruction {
    // MODERN FIX: Explicit Enum Configuration for ASSIGN, ADD, and MUL operators
    enum Op {
        ASSIGN, DECL
            , ADD, MUL
    }

    final Op op;
    final IROperand target;
    final IROperand left;
    final IROperand right;
    final IROperand declVar;

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
        switch (op) {
            case DECL:
                return declVar.toString();
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
