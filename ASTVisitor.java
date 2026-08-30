// ============================================================================
// 4. ABSTRACT SYNTAX TREE (AST) NODE DEFINITIONS & ABSTRACT VISITORS
// ============================================================================
// R = Return type of the visit operation
// C = Context state type passed down the traversal path

interface ASTVisitor<R, C> {
    R visit(NumExp n, C context);

    R visit(IdExp id, C context);

    R visit(OpExp op, C context);
}
