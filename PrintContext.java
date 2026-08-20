// ============================================================================
// 6. VISITOR IMPLEMENTATION: AST PRINTER
// ============================================================================
// Context payload to pass down the call stack context immutably
class PrintContext {
    final String indent;
    final boolean isLast;

    PrintContext(String indent, boolean isLast) {
        this.indent = indent;
        this.isLast = isLast;
    }
}
