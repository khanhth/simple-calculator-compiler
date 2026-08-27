public class CompilerContext {
    private final FrontendSymbolTable symbolTable;

    public CompilerContext() {
        this.symbolTable = new FrontendSymbolTable();
    }

    public FrontendSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    // High-level utility method to hide table internals from the caller
    public VarID registerVariable(String name, String type) {
        return this.symbolTable.declare(name, type);
    }
}
