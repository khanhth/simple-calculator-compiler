import java.util.ArrayList;
import java.util.List;

// Represents a standalone program containing declarations and a final math expression
class ProgramNode {
    final List<VarDeclStmt> declarations = new ArrayList<>();
    final Exp expression;

    ProgramNode(Exp expr) {
        this.expression = expr;
    }
}
