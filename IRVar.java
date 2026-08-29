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
