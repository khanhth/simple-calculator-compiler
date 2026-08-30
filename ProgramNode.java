// ============================================================================
// (Relating to the Visitor Pattern)
// ============================================================================

import java.util.ArrayList;
import java.util.List;

// Represents a standalone program containing declarations and a final math expression
// MODERN FIX: Clean no-args constructor mapping for multi-statement sequences
class ProgramNode {
    // final List<VarDeclStmt> declarations = new ArrayList<>();
    final List<Statement> statements = new ArrayList<>();
    // final Exp expression;

    // ProgramNode(Exp expr) {
    //     this.expression = expr;
    // }
}
