class Instruction {
    private Opcode opcode;
    private byte operand;

    public Instruction(Opcode opcode, byte operand) {
        this.opcode = opcode;
        this.operand = operand;
    }

    public Opcode getOpcode() {
        return this.opcode;
    }

    public byte getOperand() {
        return this.operand;
    }

    public void setOperand(byte o) {
        this.operand = o;
    }
}