class VarID {
    final int id;

    VarID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "%v" + id;
    }
}
