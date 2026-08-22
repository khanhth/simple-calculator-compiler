import java.util.HashMap;
import java.util.Map;

class FrontendSymbolTable {
    private final Map<String, VarID> idMap = new HashMap<>();
    private final Map<VarID, String> typeMap = new HashMap<>();
    private int counter = 0;

    public VarID declare(String name, String type) {
        if (idMap.containsKey(name)) {
            throw new RuntimeException("Compile Error: '" + name + "' already declared.");
        }
        VarID newId = new VarID(counter++);
        idMap.put(name, newId);
        typeMap.put(newId, type);
        return newId;
    }

    public VarID lookup(String name) {
        return idMap.get(name); // Returns null if not declared
    }
}
