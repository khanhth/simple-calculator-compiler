// ============================================================================
// 4. SYMBOL ADDRESS TABLE (Code Generation Metadata)
// ============================================================================

import java.util.Map;
import java.util.HashMap;

final class AddressTable {
    private static final Map<String, Integer> offsets = new HashMap<>();
    static {
        offsets.put("x", -8); // Allocate x at Frame Pointer Offset -8
        offsets.put("y", -12); // Allocate y at Frame Pointer Offset -12
    }

    public static int getOffset(String name) {
        if (!offsets.containsKey(name)) {
            throw new RuntimeException("Backend Error: Missing memory offset mapping for target: " + name);
        }
        return offsets.get(name);
    }
}
