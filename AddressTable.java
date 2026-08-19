import java.util.Map;

final class AddressTable {
    private static final Map<String, Integer> OFFSETS = Map.ofEntries(
            Map.entry("x", -8),
            Map.entry("y", -12));

    private AddressTable() {
    }

    static int getOffset(String name) {
        Integer offset = OFFSETS.get(name);
        if (offset == null) {
            throw new RuntimeException("Compile Error: Variable '" + name + "' not declared.");
        }
        return offset;
    }
}
