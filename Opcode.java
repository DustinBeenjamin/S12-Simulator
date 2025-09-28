enum Opcode {
    // Data movement
    LOAD(0b0100, "LOAD"),            // Load value from memory into accumulator
    LOADI(0b0110, ""),               // Load immediate value into accumulator
    STORE(0b0101, "STORE"),          // Store accumulator value into memory
    STOREI(0b0111, "STOREI"),

    // Arithmetic
    ADD(0b1010, "ADD"),             // Add memory value to accumulator
    SUB(0b1011, "SUB"),             // Subtract memory value from accumulator

    // Bitwise / Logic
    AND(0b1000, "AND"),              // Bitwise AND accumulator with memory value
    OR(0b1001, "OR"),                // Bitwise OR accumulator with memory value

    // Control flow
    JMP(0b0000, "JMP"),              // Jump to address
    JZ(0b0010, "JZ"),                // Jump if zero
    JN(0b0001, "JN"),                // Jump if negative
    HALT(0b1111, "HALT");           // Halt program
        
    ///////
    ///////
    ///////
    
    private final int code;
    private String name;
    private static final Opcode[] CODES = new Opcode[16];
    
    static {
        for (Opcode opcode : values()) CODES[opcode.code] = opcode;
    }
    
    ///////
    ///////
    ///////
    
    Opcode(int code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public int getBinary() {
        return this.code;
    }

    public static Opcode fromOperand(int code) {
        return (code >= 0 && code < CODES.length) ? CODES[code] : null;
    }
}
