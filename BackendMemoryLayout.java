
import java.util.HashMap;
import java.util.Map;

// ============================================================================
// 7. BACKEND SYNTHESIS: TRACK MEMORY CONFIGURATION & ASSEMBLY CODEGENERATOR
// ============================================================================
class BackendMemoryLayout {
    private final Map<Integer, Integer> stackOffsets = new HashMap<>();
    private int frameOffset = -4;

    // The backend dynamically decides layouts as it discovers abstract variable
    // handles
    public int allocateOffset(VarID varId) {
        if (stackOffsets.containsKey(varId.id)) {
            throw new RuntimeException("Compiler Internal Error: VarID " + varId + " already allocated an offset.");
        }
        stackOffsets.put(varId.id, frameOffset);
        frameOffset -= 4; // Allocate 4-byte boundaries on the target CPU stack
        return stackOffsets.get(varId.id);
    }

    public int getOffset(VarID varId) {
        if (!stackOffsets.containsKey(varId.id)) {
            throw new RuntimeException("Compiler Internal Error: VarID " + varId + " not found in memory layout.");
        }
        return stackOffsets.get(varId.id);
    }
}
