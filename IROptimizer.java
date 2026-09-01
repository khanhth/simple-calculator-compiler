import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    // ─── NEW OPTIMIZATION PASS: DEAD CODE ELIMINATION ───
    public List<IRInstruction> eliminateDeadCode(List<IRInstruction> intermediateCode) {
        // Tracks symbols that are still needed by operations downstream
        Set<String> liveSymbols = new HashSet<>();
        List<IRInstruction> prunedIR = new ArrayList<>();

        // Phase A: Seed the analysis backwards.
        // The final statement/expression in the stream computes the critical final result.
        // We find the last expression instruction and mark its components alive.
        boolean foundRootExpression = false;
        for (int i = intermediateCode.size() - 1; i >= 0; i--) {
            IRInstruction inst = intermediateCode.get(i);
            // In our language, an ExprStmt or the final temp register (e.g., t1) holds the output
            if (!foundRootExpression && inst.target instanceof IRTemp) {
                liveSymbols.add(inst.target.toString());
                foundRootExpression = true;
                break;
            }
        }

        // Standard fallback: If it's a simple program with just user assignments,
        // keep variables alive by default so we don't accidentally erase variables the user explicitly set.
        if (liveSymbols.isEmpty()) {
            for (IRInstruction inst : intermediateCode) {
                if (inst.target instanceof IRVar) liveSymbols.add(inst.target.toString());
            }
        }

        // Phase B: Scan backward from the bottom instruction to the top
        for (int i = intermediateCode.size() - 1; i >= 0; i--) {
            IRInstruction inst = intermediateCode.get(i);
            String targetKey = inst.target.toString();

            // 1. CRITICAL GUARD: User variable assignments (like %v0 = 5) should stay
            // unless we have an aggressive tracking system for variables. For safety, we keep all IRVar targets alive.
            boolean isUserVariable = (inst.target instanceof IRVar);

            // 2. CHECK ALIVE STATUS:
            if (liveSymbols.contains(targetKey) || isUserVariable) {
                // The instruction is ALIVE. Keep it.
                prunedIR.add(0, inst); // Insert at the front to maintain correct chronological order

                // Update Liveness: The target is now defined (dead upwards), but its inputs become live
                if (!isUserVariable) {
                    liveSymbols.remove(targetKey);
                }

                if (inst.left != null && !(inst.left instanceof IRConst)) {
                    liveSymbols.add(inst.left.toString());
                }
                if (inst.right != null && !(inst.right instanceof IRConst)) {
                    liveSymbols.add(inst.right.toString());
                }
            } else {
                // The instruction is DEAD! Drop it completely.
                System.out.println("   ✂️ Eliminated Dead Code: " + inst);
            }
        }

        return prunedIR;
    }

}
