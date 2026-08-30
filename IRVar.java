// ============================================================================
// (Relating to) THREE-ADDRESS CODE (3AC) INTERMEDIATE REPRESENTATION STRUCTURES
// ============================================================================

class IRVar extends IROperand {
    final VarID varId;

    IRVar(VarID id) {
        this.varId = id;
    }

    @Override
    public String toString() {
        return varId.toString();
    }
}
