
// ============================================================================
// 3-ADDRESS INTERMEDIATE REPRESENTATION (IR) BUILDERS
// (Relating to) THREE-ADDRESS CODE (3AC) INTERMEDIATE REPRESENTATION STRUCTURES
// ============================================================================
import java.util.ArrayList;
import java.util.List;

class IRGenerator {
    private final List programIR = new ArrayList<>();
    private int tempCount = 0;

    private IRTemp nextTemp() {
        return new IRTemp(tempCount++);
    }

    public List generate(ProgramNode program) {
        for (Statement stmt : program.statements) {
            if (stmt instanceof AssignStmt) {
                AssignStmt assign = (AssignStmt) stmt;
                // 1. Flatten the right hand math equation first
                IROperand sourceValue = lowerExpr(assign.valueExpr);
                // 2. Emit a direct ASSIGN instruction into the flat stream
                programIR.add(new IRInstruction(IRInstruction.Op.ASSIGN, new IRVar(assign.varId), sourceValue, null));
            } else if (stmt instanceof ExprStmt) {
                lowerExpr(((ExprStmt) stmt).expression);
            }
        }
        return programIR;
    }

    // Expression lowering dispatcher helper
    private IROperand lowerExpr(Exp exp) {
        if (exp instanceof NumExp)
            return new IRConst(((NumExp) exp).val);
        if (exp instanceof IdExp)
            return new IRVar(((IdExp) exp).varId);
        if (exp instanceof OpExp) {
            OpExp op = (OpExp) exp;
            IROperand left = lowerExpr(op.left);
            IROperand right = lowerExpr(op.right);
            IRTemp temp = nextTemp();
            IRInstruction.Op irOp = (op.op == OpExp.PLUS) ? IRInstruction.Op.ADD : IRInstruction.Op.MUL;
            programIR.add(new IRInstruction(irOp, temp, left, right));
            return temp;
        }
        return null;
    }
}
