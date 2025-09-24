enum Opcode {
    // Data movement
    LOAD,        // Load value from memory into accumulator
    LOADI,       // Load immediate value into accumulator
    STORE,       // Store accumulator value into memory
    STOREI,

    // Arithmetic
    ADD,         // Add memory value to accumulator
    SUB,         // Subtract memory value from accumulator

    // Bitwise / Logic
    AND,         // Bitwise AND accumulator with memory value
    OR,          // Bitwise OR accumulator with memory value

    // Control flow
    J,           // Jump to address
    JZ,          // Jump if zero
    JN,          // Jump if negative
    HALT          // Halt program
}
