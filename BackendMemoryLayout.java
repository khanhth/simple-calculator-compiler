
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
    public int getOrAllocateOffset(VarID varId) {
        if (!stackOffsets.containsKey(varId.id)) {
            stackOffsets.put(varId.id, frameOffset);
            frameOffset -= 4; // Allocate 4-byte boundaries on the target CPU stack
        }
        return stackOffsets.get(varId.id);
    }
}
