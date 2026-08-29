class IRTemp extends IROperand {
    final int number;

    IRTemp(int n) {
        this.number = n;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}
