// ============================================================================
// 6. 3-ADDRESS INTERMEDIATE REPRESENTATION (IR) BUILDERS
// ============================================================================
import java.util.ArrayList;
import java.util.List;

class IRGenerator implements ASTVisitor<IROperand, Void> {
    private final List<IRInstruction> programIR = new ArrayList<>();
    private int tempCount = 0;

    public List<IRInstruction> generate(ProgramNode prog) {
        for (VarDeclStmt decl : prog.declarations) {
            decl.accept(this, null); // Process variable declarations first
        }
        prog.expression.accept(this, null);
        return programIR;
    }

    @Override
    public IROperand visit(NumExp n, Void ctx) {
        return new IRConst(n.val);
    }

    @Override
    public IROperand visit(IdExp id, Void ctx) {
        return new IRVar(id.varId);
    }

    @Override
    public IROperand visit(OpExp op, Void ctx) {
        IROperand leftOp = op.left.accept(this, null);
        IROperand rightOp = op.right.accept(this, null);
        IRTemp irTargetTemp = new IRTemp(tempCount++);
        IRInstruction.Op irOp = (op.op == OpExp.PLUS) ? IRInstruction.Op.ADD : IRInstruction.Op.MUL;
        programIR.add(new IRInstruction(irOp, irTargetTemp, leftOp, rightOp));
        return irTargetTemp;
    }

    @Override
    public IROperand visit(VarDeclStmt decl, Void ctx) {
        IROperand irDeclVar = new IRVarDeclStmt(decl.varName, decl.type);
        programIR.add(new IRInstruction(irDeclVar));
        return irDeclVar;
    }
}
