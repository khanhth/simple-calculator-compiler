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
        if (op instanceof IRVarDeclStmt) {
            // Variable declarations don't have a direct runtime representation, so we can
            // return an empty string or a placeholder.
            return "[fp + " + layout.allocateOffset(((IRVarDeclStmt) op).varID) + "]";
        }
        if (op instanceof IRConst)
            return String.valueOf(((IRConst) op).value);
        if (op instanceof IRVar)
            return "[fp + " + layout.getOffset(((IRVar) op).varId) + "]";
        if (op instanceof IRTemp) {
            String key = op.toString();
            if (!tempRegisterMap.containsKey(key)) {
                throw new RuntimeException("Compiler Internal Error: IRTemp " + key + " has not been allocated a hardware register.");
            }
            return tempRegisterMap.get(key);
        }
        throw new IllegalArgumentException("Unknown IR Operand Type");
    }

    // ─── THE NEW FLAT BACKEND EMISSION LOOP ───
    // Backend sequential code emitter running over the flat 3AC intermediate list
    public void compile(List<IRInstruction> intermediateCode, BackendMemoryLayout layout) {
        for (IRInstruction inst : intermediateCode) {
            if (inst.op == IRInstruction.Op.DECL) {
                // Resolve the right-hand source operand location (if applicable)
                String decSrc = resolveOperand(inst.declVar, layout);
                // If it's a variable declaration
                System.out.println("DECL   " + decSrc);
                continue; // Skip to the next instruction since DECL doesn't have a target or source
            }
            if (inst.op != IRInstruction.Op.ASSIGN && inst.op != IRInstruction.Op.DECL) {
                tempRegisterMap.put(inst.target.toString(), allocateRegister());
            }
            // Step A: Resolve the destination target register
            String targetReg = resolveOperand(inst.target, layout);

            // Step B: Resolve the left-hand source operand location
            String leftSrc = resolveOperand(inst.left, layout);

            // Step D: Emit targeted instructions based on the operation type
            if (inst.op == IRInstruction.Op.ASSIGN) {
                // If it's a direct load or literal assignment
                System.out.println("LOAD   " + targetReg + ", " + leftSrc);
                continue; // Skip to the next instruction since ASSIGN doesn't have a right operand
            }
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
