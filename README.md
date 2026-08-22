## TODOS
[x] 1. Lexer is mock now we need to add its implementation.
[-] 2. In [https://share.google/aimode/MVU7ycp6rbxdSjM5e](this) thread, complete the following items:
    [-] 2.1. How to update the code to track and clean up temporary registers so you don't run out of them (Register Allocation).
    [-] 2.2 How a Symbol Table tracks scope blocks (like local vs. global variables) inside the AddressTable.getOffset() step. (See also: Static Scope Management: The address table allows the compiler to calculate exactly how much total memory a function needs before the program ever runs.)
    [x] 2.3. How to handle unassigned variables or type mismatches before the code generation step runs.
    [-] 2.4. Handle Scope Blocks (like variables that exist inside nested brackets {} but disappear outside them)?
    [-] 2.5. Introduce multiple types (like checking that a programmer doesn't try to multiply a string by an int)?
    [-] 2.6. Add an extra step to track the total number of lines/characters to show better compiler diagnostic messages if a typo happens
    [-] 2.7. How a parser can use a larger lookahead (like LL(2)) to peer further down the line
    [-] 2.8. What happens when the lookahead encounters an empty string or file.
    [-] 2.9. See how to implement **error reporting line numbers** to tell the programmer *where* a mistake happened?
    [-] 2.10. Add an **Optimizer Visitor** to simplify constant expressions (like rewriting `3 * 4` directly to `12` before generating assembly)?
    [-] 2.11. Explain why the `FrontendSymbolTable` class contains zero physical memory offsets.
    [-] 2.12. Explain why the AST node no longer carries the raw name string into the backend.
    [-] 2.13. Rewrite your core ASTParser.F() method to query the FrontendSymbolTable and instantiate this new variant of IdExp.
    [-] 2.14. Step into generating a text-based 3-Address Code (3AC) Intermediate Representation (IR) before making any assembly code at all.
    [-] 2.15. **The Pipeline in Action**: If you feed "x * 3 + y" through this modern separated setup, your pipeline connects seamlessly without breaking architectural walls...
    [-] 2.16. Update your ASTParser.F() method so it declares string names in the FrontendSymbolTable and correctly initializes this new variant of IdExp.
    [-] 2.17. Move forward with creating a true 3-Address Code (3AC) Intermediate Representation (IR) layer.
    [-] 2.18. Update your ASTPrinter class to print out %v0 and %v1 names instead of raw variable names.
    [-] 2.19. Explain why "Since TypeChecker is a high-level frontend validation step, it no longer needs to check string sets manually because the ASTParser itself now acts as the gatekeeper via the FrontendSymbolTable."
[-] 3. Examine the relationship between the enviroment (variable context) class and the address table class.
[-] 4. Examine the manually passing frontendTable code in `Main.java`, i.e. `ASTParser parser = new ASTParser(lexer, frontendTable); // Passed frontendTable here`
