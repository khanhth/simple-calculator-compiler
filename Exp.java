// ============================================================================
// 3. ABSTRACT SYNTAX TREE (AST) CLASSES (With Double Dispatch Support)
// ============================================================================
public abstract class Exp {
    public abstract <R, C> R accept(ASTVisitor<R, C> v, C context);
}
