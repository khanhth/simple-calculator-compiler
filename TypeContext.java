// Context state object used to pass down the list of declared variables
class TypeContext {
    final FrontendSymbolTable symbolTable;

    // Pass your active frontend table into the context
    TypeContext(FrontendSymbolTable table) {
        this.symbolTable = table;
    }
}
