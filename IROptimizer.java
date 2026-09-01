import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class IROptimizer {
    // Tracks which abstract operands have been reduced to known literal integers
    private final Map<String, Integer> constantEnv = new HashMap<>();

    // Helper to evaluate if an operand resolves to a known integer constant
    private Integer tryResolveConstant(IROperand operand) {
        if (operand instanceof IRConst) {
            return ((IRConst) operand).value;
        }
        if (operand != null && constantEnv.containsKey(operand.toString())) {
            return constantEnv.get(operand.toString());
        }
        return null; // Not a known compile-time constant
    }

    // ─── THE OPTIMIZATION STREAM PASS ───
    public List<IRInstruction> optimize(List<IRInstruction> originalIR) {
        List<IRInstruction> optimizedIR = new ArrayList<>();
        constantEnv.clear();

        for (IRInstruction inst : originalIR) {
            // Attempt to resolve both inputs to constant integers
            Integer leftConst = tryResolveConstant(inst.left);
            Integer rightConst = tryResolveConstant(inst.right);

            // SCENARIO A: Constant Folding Opportunity Found!
            if (leftConst != null && rightConst != null) {
                int foldedValue = 0;
                if (inst.op == IRInstruction.Op.ADD) {
                    foldedValue = leftConst + rightConst;
                } else if (inst.op == IRInstruction.Op.MUL) {
                    foldedValue = leftConst * rightConst;
                }

                // Record this optimized value for future instructions to look up
                constantEnv.put(inst.target.toString(), foldedValue);

                // Rewrite the complex math instruction into a simple literal assignment
                optimizedIR.add(new IRInstruction(
                        IRInstruction.Op.ASSIGN,
                        inst.target,
                        new IRConst(foldedValue),
                        null));
                System.out.println("   ✨ Folded: " + inst + "  ──>  " + inst.target + " = " + foldedValue);
            }

            // SCENARIO B: Left or Right is variable, but maybe we can propagate known
            // constants
            else {
                IROperand newLeft = (leftConst != null) ? new IRConst(leftConst) : inst.left;
                IROperand newRight = (rightConst != null) ? new IRConst(rightConst) : inst.right;

                // Track simple literal variable assignments like %v0 = 5
                if (inst.op == IRInstruction.Op.ASSIGN && leftConst != null) {
                    constantEnv.put(inst.target.toString(), leftConst);
                }

                optimizedIR.add(new IRInstruction(inst.op, inst.target, newLeft, newRight));
            }
        }
        return optimizedIR;
    }
}
