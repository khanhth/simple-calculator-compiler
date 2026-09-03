import java.util.ArrayList;
import java.util.List;

// Represents a standalone program containing declarations and a final math expression
class ProgramNode {
    public List<VarDeclStmt> declarations = new ArrayList<>();
    public Exp expression;
}
