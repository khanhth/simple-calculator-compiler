// Context state object used to pass down the list of declared variables
class TypeContext {
    final CompilerContext compilerCtx;

    // Pass your active frontend table into the context
    TypeContext(CompilerContext compilerCtx) {
        this.compilerCtx = compilerCtx;
    }
}
