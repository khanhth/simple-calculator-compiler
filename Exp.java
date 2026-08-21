// ============================================================================
// 3. ABSTRACT SYNTAX TREE (AST) *CLASSES* or *NODE DEFINITIONS* (With Double Dispatch Support)
// ============================================================================
public abstract class Exp {
    public abstract <R, C> R accept(ASTVisitor<R, C> v, C context);
}
