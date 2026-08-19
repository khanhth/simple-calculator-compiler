final class CodeGenerator {
    private int registerCount;

    String compile(Exp expression) {
        if (expression instanceof NumExp) {
            NumExp number = (NumExp) expression;
            String register = nextRegister();
            System.out.println("LOADI " + register + ", " + number.value);
            return register;
        }
        if (expression instanceof IdExp) {
            IdExp identifier = (IdExp) expression;
            String register = nextRegister();
            int offset = AddressTable.getOffset(identifier.name);
            System.out.println("LOAD  " + register + ", [fp + " + offset + "]");
            return register;
        }
        if (expression instanceof OpExp) {
            OpExp operation = (OpExp) expression;
            String leftRegister = compile(operation.left);
            String rightRegister = compile(operation.right);
            String resultRegister = nextRegister();
            String instruction = operation.operator == OpExp.PLUS ? "ADD" : "MUL";
            System.out.println(instruction + "   " + resultRegister + ", " + leftRegister + ", " + rightRegister);
            return resultRegister;
        }
        throw new IllegalArgumentException("Unknown AST node type");
    }

    private String nextRegister() {
        return "r" + registerCount++;
    }
}