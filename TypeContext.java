import java.util.HashSet;
import java.util.Set;

// Context state object used to pass down the list of declared variables
class TypeContext {
    // Stores names of variables that have been safely declared/initialized
    final Set<String> initializedVariables;

    TypeContext() {
        this.initializedVariables = new HashSet<>();
    }
}
