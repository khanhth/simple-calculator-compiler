
class IRConst extends IROperand {
    final int value;

    IRConst(int v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
