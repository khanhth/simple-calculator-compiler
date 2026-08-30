// ============================================================================
// VISITOR 3 IMPLEMENTATION: TARGET CODE GENERATOR (Synthesis Phase)
// (Relating to) BACKEND SYNTHESIS: TRACK MEMORY CONFIGURATION & ASSEMBLY CODEGENERATOR
// ============================================================================

// NOTE on class header: Return type is `String` (Register Name), Context type is `Void` (No state needed)
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CodeGenerator {
    private int hardwareRegisterCount = 0;

    // Maps abstract IRTemp operands (like t0) to active hardware scratch registers
    // (like r0)
    private final Map<String, String> tempRegisterMap = new HashMap<>();

    // Allocates the next physical hardware register handle
    private String allocateRegister() {
        return "r" + (hardwareRegisterCount++);
    }

    // Resolves any abstract IROperand into a readable physical string location
    private String resolveOperand(IROperand op, BackendMemoryLayout layout) {
        if (op instanceof IRConst)
            return String.valueOf(((IRConst) op).value);
        if (op instanceof IRVar)
            return "[fp + " + layout.getOrAllocateOffset(((IRVar) op).varId) + "]";
        if (op instanceof IRTemp) {
            String key = op.toString();
            if (!tempRegisterMap.containsKey(key))
                tempRegisterMap.put(key, allocateRegister());
            return tempRegisterMap.get(key);
        }
        throw new IllegalArgumentException("Unknown IR Operand Type");
    }

    // ─── THE NEW FLAT BACKEND EMISSION LOOP ───
    // Backend sequential code emitter running over the flat 3AC intermediate list
    public void compile(List<IRInstruction> intermediateCode, BackendMemoryLayout layout) {
        for (IRInstruction inst : intermediateCode) {

            // Step A: Resolve the destination target register
            String targetReg = resolveOperand(inst.target, layout);

            // Step B: Resolve the left-hand source operand location
            String leftSrc = resolveOperand(inst.left, layout);

            // Step C: Emit targeted instructions based on the operation type
            if (inst.op == IRInstruction.Op.ASSIGN) {
                // If it's a direct load or literal assignment
                System.out.println("LOAD   " + targetReg + ", " + leftSrc);
            } else {
                // It's a binary operation. Resolve the right-hand source operand
                String rightSrc = resolveOperand(inst.right, layout);

                // If the left operand is sitting out in RAM stack space, load it into a
                // temporary register first
                if (leftSrc.startsWith("[")) {
                    String scratch = allocateRegister();
                    System.out.println("LOAD   " + scratch + ", " + leftSrc);
                    leftSrc = scratch;
                }

                // If the right operand is sitting out in RAM stack space, load it too
                if (rightSrc.startsWith("[")) {
                    String scratch = allocateRegister();
                    System.out.println("LOAD   " + scratch + ", " + rightSrc);
                    rightSrc = scratch;
                }

                // Emit the arithmetic operational assembly code
                String mnemonic = (inst.op == IRInstruction.Op.ADD) ? "ADD    " : "MUL    ";
                System.out.println(mnemonic + targetReg + ", " + leftSrc + ", " + rightSrc);
            }
        }
    }
}
