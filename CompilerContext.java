// ============================================================================
// 2. MODERN COMPILER CONTEXT & SYMBOL MANAGERS
// ============================================================================
public class CompilerContext {
    private final FrontendSymbolTable symbolTable;

    public CompilerContext() {
        this.symbolTable = new FrontendSymbolTable();
    }

    public FrontendSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public VarID registerVariable(String name, String type) {
        return this.symbolTable.declare(name, type);
    }
}
