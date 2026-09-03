// ============================================================================
// (Relating to) THREE-ADDRESS CODE (3AC) INTERMEDIATE REPRESENTATION STRUCTURES
// ============================================================================

class IRTemp extends IROperand {
    final int number;

    IRTemp(int n) {
        this.number = n;
    }

    @Override
    public String toString() {
        return "temp" + number;
    }
}
