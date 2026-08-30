// Context payload to pass down the call stack context immutably
class PrintContext {
    final String indent;

    PrintContext(String indent) {
        this.indent = indent;
    }
}
