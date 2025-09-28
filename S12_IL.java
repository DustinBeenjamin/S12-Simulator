import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class S12_IL implements S12_IL_Interface {

    private final int PC_BITS = 8;
    private final int A_BITS = 12;
    private final int MEMORY_BITS =  A_BITS;                    
    private final int OPCODE_BITS = 4;
    private final int OPERAND_BITS = 8;
    private int a = 0;                                                  //Accumulator
    private int pc = 0;                                                 //Program Counter
    private int cyclesExecuted = 0;                                     //The running number of instructions executed
    private int targetCyclesExecuted = -1;                              //Use -1 to indicate that user has not indicated a target num of clocks.
    private int[] memory = new int[(int) (Math.pow(2, A_BITS) - 1)];  //Instructions and Data  
    private File memFile;                                               //The 12-bit instruction and data file formatted in binary
    private String inputFileName;
    private String outputFileName = null;                               //Name for optional output file (binary formatted)
    private boolean halted = false;
    private Scanner scan;  
    private int inputDataFirstIndex = -1;                               //In memory array, the first index that contains the first operand of N
    private int N = -1;                                                 //The number of operands that need to be processed by program   
    private int isTimeOptimized = -1;                                   //1 if you are running the timeOptimized benchmark, 0 for the memory optmized, -1 indicates not set
    private String[] traceRecord = new String[50];                      //Each time an instruciton executes, add it to the record of traces.

    public void run(){
        //Iterate through the instructions. When no 'targetCyclesExecuted' value is not provided via 
        //the command line, it defaults to -1. Cycles executed starts at 0.
        while ((cyclesExecuted < targetCyclesExecuted || targetCyclesExecuted == -1) && !halted) {
            cyclesExecuted++;
            update();
            traceRecord[cyclesExecuted - 1] = binaryToS12(String.format("%12s", Integer.toBinaryString(0xFFF & memory[pc - 1])));
        }

        //Finish by writing the final trace
        writeTrace(inputFileName);
        writeMem(inputFileName);
    }

    /**
     * intializeMem reads in the plain text file and instantiates the memory array
     *
     * @param filename of memory to be read in
     * @return true if memory successfully parsed and instantiated
     */
    public boolean intializeMem(String filename) {
        try {
            //GRAB THE BINARY MEMFILE
            memFile = new File(filename);
            scan = new Scanner(memFile);
            
            //READ THE 8 PC BITS
            String temp;
            if (scan.hasNext()) {
                temp = scan.next();
                if (temp.length() != PC_BITS) {
                    throw new Exception("ERROR: Program counter incorrect number of bits in memfile. Found " + temp.length() + " but expected " + PC_BITS);
                } 
            } else {
                throw new Exception("ERROR: Did not find PC bits as the first entry in memfile.");
            }
            pc = 0xFF & Integer.parseInt(temp, 2);

            //READ THE ACCUMULATOR 12 BITS
            if (scan.hasNext()) {
                temp = scan.next();
                if (temp.length() != A_BITS) {
                    throw new Exception("ERROR: Accumulator has incorrect number of bits in memfile");
                }
            } else {
                throw new Exception("ERROR: Did not find the A bits as the second entry in memfile.");
            }
            a = 0xFFF & Integer.parseInt(temp, 2);

            //PARSE THE INSTRUCTIONS AND DATA INTO MEMORY
            int i = 0;
            while (scan.hasNext()) {
                if (i > memory.length) {throw new Exception("ERROR: Memfile has more instructions than memory can fit.");} 
                scan.next();
                temp = scan.next();
                if (temp.length() != MEMORY_BITS) {throw new Exception("ERROR: Memfile has incorrect number of bits on line " + (i + 1));}
                if (!temp.matches("[01]+")) throw new Exception("ERROR: Non-binary pattern in memfile: " + temp);
                memory[i] = (0xFFF) & Integer.parseInt(temp, 2);
                i++;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("ERROR: File not found. Unable to initialize memeory.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }    
        finally {
            if (scan != null) {scan.close();}
        }
        return true;
    }

    /**
    * execute one cycle of the machine
    *
    * @return String representation of the instruction executed (binary)
    */
    public String update(){

        String instructionBinary = String.format("%" + MEMORY_BITS + "s", Integer.toBinaryString(memory[pc] & 0xFFF)).replace(' ', '0');
        int opcodeInt = (memory[pc] >> 8) & 0xF;
        Opcode code = Opcode.fromOperand(opcodeInt);
        if (code == null) {
            String opcodeBinary = String.format("%4s", Integer.toBinaryString(opcodeInt)).replace(' ', '0');
            System.out.println("ERROR: Invalid opcode '" + opcodeBinary + "'" + " on line " + pc + " at clock " + cyclesExecuted );
            System.exit(1);
        }
        byte operand = (byte) (memory[pc] & 0xFF);

        switch (code) {
            case JMP:
                JMP(operand);
                break;

            case JN:
                JN(operand);
                break;

            case JZ:
                JZ(operand);
                break;

            case LOAD:
                LOAD(operand);
                break;
            
            case STORE:
                STORE(operand);
                break;

            case LOADI:
                LOADI(operand);
                break;

            case AND:
                AND(operand);
                break;

            case OR:
                OR(operand);
                break;

            case ADD:
                ADD(operand);
                break;

            case SUB:
                SUB(operand);
                break;

            case HALT:
                HALT();
                halted = true;
            }

        //return the instruction binary as a string
        return instructionBinary;
    }
    
    /**
    * Returns state of the registers in the machine
    * This is one place to add a javadoc in your implementation.
    * Specifying in what order the register values are returned
    *
    * @return String array of register values
    */
    public String[] getProcessorState(){
        //This function depends on which program you are trying to run. Make sure the appropriate values are set. Otherwise, quit with warning.
        if (N == -1) {
            System.out.println("ERROR: 'getProcessorState()' depends on 'N'. Use 'setN()' first.");
            return null;
        } else if (inputDataFirstIndex == -1) {
            System.out.println("ERROR: 'getProcessorState()' depends on 'inputDataFirstIndex'. Use 'setFirstDataIndex()' first.");
            return null;
        } else if (isTimeOptimized == -1) {
            System.out.println("ERROR: 'getProcessorState()' depends on 'timeOptimized'. Use 'setFirstDataIndex()' first.");
            return null;
        }

        String[] registers = (isTimeOptimized == 1) ? getTimeEfficientWorkingRegisters() : getMemoryEfficientWorkRegisters();
        String[] data = getDataRegisters();
        String[] result = new String[registers.length + data.length];

        //COPY THE WORKING REGISTERS INTO THE RESULT
        for (int i = 0; i < registers.length; i++) {
            result[i] = registers[i];
        }

        //COPY THE DATA REGISTERS INTO RESULT
        for (int i = 0; i < data.length; i++) {
            result[i + registers.length] = data[i];
        }

        return result;

    }

    //The working registers are the memory locations used to hold auxillary information that can be discarded at the end of the program
    private String[] getTimeEfficientWorkingRegisters(){
        return null;
    }

    //The working registers are the memory locations used to hold auxillary information that can be discarded at the end of the program
    private String[] getMemoryEfficientWorkRegisters(){
        //REGISTERS
        //0xF8 Stores the running product                   - Address: Decimal 248
        //0xF9 Stores the current running sum               - Address: Decimal 259
        //0xFA Stores the current second operand            - Address: Decimal 250
        //0xFB Stores the current mask                      - Address: Decimal 251
        //0xFC Stores i, the counter for the outer loop     - Address: Decimal 252
        //0xFD Stores j, the counter for the inner loop     - Address: Decimal 253

        int[] registerIndices = {248, 259, 250, 251, 252, 253};
        String[] result = new String[registerIndices.length];

        for (int i = 0; i < registerIndices.length; i++) {
            result[i] = String.format("%12s", Integer.toBinaryString(0xFFF & memory[registerIndices[i]])).replace(" ", "0");
        }
        
        return result;
    }

    //The data registers are the memory locations that hold the N numbers that need to be processed
    private String[] getDataRegisters(){
        //In this benchmark, data starts at 0x2C
        String[] result = new String[N];
        for (int i = 0; i < N; i++) {
            result[i] = String.format("%s12", Integer.toBinaryString(0xFFF & (memory[inputDataFirstIndex + 0]))).replace(" ", "0");
        }
        return result;
    }

    public void setFirstDataIndex(int i) {
        if (i < 0 || i > (2 << PC_BITS)) {
            System.out.println("ERROR: Cant set first data index to " + i + " because it is out of unsigned range of " + PC_BITS + " bits.");
        } else {
            inputDataFirstIndex = i;
        }
    }

    public void setN(int n) {
        if (n < 0 || n > (2 << PC_BITS)) {
            System.out.println("ERROR: N cannot be set to " + n + " because there are not enough memory spaces.");
        } else {
            this.N = N;
        }
    }
    
    /**
    * String form of the current state of the memory (e.g. each line is hex
    * address, space, binary word)
    *
    * @return string representation of memory
    */
    public String getMemState(){
        int memoryLimit = 2 << PC_BITS;
        String result = "";
        for (int i = 0; i < memoryLimit; i++) {
            result += "0x";
            result += String.format("%2s", Integer.toHexString(i)).replace(" ", "0");
            result += " ";
            result += Integer.toBinaryString(0xFFF & memory[i]);
            if (i != (memoryLimit - 1)) {result += System.lineSeparator();}
        }    
        return result;
    }
    
    /**
     * Write out the memFile associated with the current state of the simulation
     *
     * @param filename - name of memFile to create
     * @return true if successful file creation, false otherwise
     */
    public boolean writeMem(String filename) {

        // SCRUB FILE EXTENSION OFF IF IT EXISTS
        String[] words = filename.trim().split("\\.");
        filename = words[0];

        File outputMemFile = new File(filename + "_memOut" + ".memfile");

        try {
            //MAKE A NEWFILE
            if (outputMemFile.createNewFile()) {
                System.out.println("File created: " + outputMemFile.getName());
            } else {
                System.out.println("File already exists. You must delete it first.");
                return false;
            }

            //ADD THE PROGRAM MEMORY TO THE FILE
            int memoryLimit = 1 << PC_BITS; // 2^PC_BITS
            try (FileWriter writer = new FileWriter(outputMemFile)) {
                //ADD THE PC AND ACCUMULATOR TO THE FIRST LINE SEPARATED BY A SPACE
                writer.write(this.getProgramCounter() + " " + this.getAccumulator() + System.lineSeparator());

                //ADD THE DATA AND THE INSTRUCTIONS 
                for (int i = 0; i < memoryLimit; i++) {
                    int memoryValue = memory[i] & 0xFFF;
                    String memoryString = String.format("%12s",
                            Integer.toBinaryString(memoryValue)).replace(' ', '0');
                    writer.write(memoryString + System.lineSeparator());
                }
            }

            return true;

        //END THE PROGRAM
        } catch (IOException e) {
            System.out.println("Could not create output memfile.");
            e.printStackTrace();
            return false;
        }
    }

   /**
     *
     * @param filename - name of trace file to create
     * @return true if successfully written, else false
     */
    public boolean writeTrace(String filename) {
        String[] splitFilename = filename.split("\\.");
        String traceFilename = splitFilename[0] + ".trace";

        try (FileWriter fw = new FileWriter(new File(traceFilename))) {
            for (String record : traceRecord) {
                fw.write(record + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing trace file: " + e.getMessage());
            return false;
        }
    }

    /**
    * Translate s12 assembly instruction into binary
    * @param s12Instr human readable instruction, space, two hex digits
    * @return String form of twelve bits (e.g. {w over {0,1} | |w| is 12})
    */
    public String s12ToBinary(String s12Instr){

        //SPLIT THE HUMAN READABLE INSTRUCTION INTO WORDS
        String[] words = s12Instr.trim().split("\\s+");
        String opcodeMnemonic = words[0].toUpperCase();
        String operandHex = words[1].toUpperCase();
        
        //VERIFY THAT THERE ARE ONLY TWO TEXT FIELDS IN THE INSTRUCTION
        if (words.length != 2) {
            System.out.println("ERROR: The method 's12ToBinary' can only split instructions that have exactly two text fields (Opcode and Operand).");
            return null;
        }

        //VERIFY THAT OPCODE IS REAL
        Opcode code;
        try {
            code = Opcode.valueOf(opcodeMnemonic);
        } catch (Exception e) {
            System.out.println("ERROR: The method 's12ToBinary' can only process real opcodes, not " + words[0]);
            return null;
        }

        //GENERATE BINARY STRINGS
        String opcodeBinary = String.format("%4s", Integer.toBinaryString(code.getBinary())).replace(' ', '0');
        int operandValue = Integer.parseInt(operandHex, 16);
        String operandBinary = String.format("%8s", Integer.toBinaryString(operandValue)).replace(' ', '0');

        return opcodeBinary + operandBinary;

    }

    /**
    * Translate binary word into human readable instruction and two hex digits
    * @param binInstr
    * @return String of human readable instruction and two hex digits
    * e.g. 000010000001 --> JMP A1
    */
    public String binaryToS12(String binInstr){
        
        //MAKE SURE THAT ARGUMENT HAS ENOUGH BITS FOR A FULL INSTRUCTION
        if (binInstr.length() != MEMORY_BITS) {
            System.out.println("ERROR: Method 'binaryToS12' only proccesses 12 bit strings." + binInstr + " is illegal.");
            return null;
        }
        
        //CREATE TEMP
        String opcodeString = binInstr.substring(0, OPCODE_BITS);
        String operandString = binInstr.substring(OPCODE_BITS, OPCODE_BITS + OPERAND_BITS);

        //BUILD STRING
        Opcode opcode = Opcode.fromOperand(Integer.parseInt(opcodeString, 2)); 
        if (opcode == null) {
            System.out.println("Method 'binaryToS12' recieved the unknown opcode: " + opcodeString);
            return null;
        }
        opcodeString = opcode.getName();
        int operandValue = Integer.parseInt(operandString, 2);
        operandString = String.format("%02X", operandValue);


        return opcodeString + " " + operandString;

    }
   
    public String getAccumulator(){
        return binaryToS12(String.format("%12s", Integer.toBinaryString(a & 0xFFF)).replace(" ", "."));
    }

    public String getNumCyclesExectured(){
        return Integer.toString(cyclesExecuted);
    }

    public String getProgramCounter(){
        return binaryToS12(Integer.toBinaryString(pc & 0xFF));
        
    }

    public void setIsTimeOptimized(int i) {
        if (i < 0 || i > 1) {
            System.out.println("ERROR: 'isTimeOptimized' can only be manually set to 0 or 1.");
        } else {
            this.isTimeOptimized = i;
        }
    }





























    //UNCONDITIONAL JUMP
    public void JMP(byte o){
        int memoryIndex = 0xFF & o;
        pc = memoryIndex; 
    } 

    //JUMP IF NEGATIVE
    public void JN(byte o){
        if ((0x800 & a) != 0) {
            int memoryIndex = 0xFF & o;
            pc = memoryIndex;
        } else {
            incrementPC();
        }
    }

    //JUMP IF ZERO
    public void JZ(byte o){
        if ((a & 0xFFF) == 0) {
            int memoryIndex = 0xFF & o;
            pc = memoryIndex;
        } else {
            incrementPC();
        }
    }

    //LOAD
    public void LOAD(byte o) {
        int memoryIndex = 0xFF & o;
        a = memory[memoryIndex] & 0xFFF;
        incrementPC();
    }

    //STORE
    public void STORE(byte o){
        int memoryIndex = 0xFF & o;
        memory[memoryIndex] = a & 0xFFF;
        incrementPC();
    }

    //LOAD IMMEDIATE
    public void LOADI(byte o){
        a = 0xFF & o;
        incrementPC();
    }

    //BITWISE AND
    public void AND(byte o){
        int memoryIndex = 0xFF & o;
        a = 0xFFF & (a & memory[memoryIndex]);
        incrementPC();
    }

    //BITWISE OR
    public void OR(byte o) {
        int memoryIndex = 0xFF & o;
        a = 0xFFF & (a | memory[memoryIndex]);
        incrementPC();
    }

    //ADD
    public void ADD(byte o) {
        int memoryIndex = 0xFF & o;
        a = 0xFFF & (a + memory[memoryIndex]);
        incrementPC();
    }

    //SUB
    public void SUB(byte o) {
        int memoryIndex = 0xFF & o;
        a = 0xFFF & (a - memory[memoryIndex]);
        incrementPC();
    }

    //HALT
    public void HALT(){
        System.out.println("PROGRAM END");
        int signedA = (a & 0x800) != 0 ? (a | ~0xFFF) : (a & 0xFFF);
        System.out.println("ACCUMULATOR: " + signedA);


    }

    //Program counter is only 8 bits, so pc++ runs the risk of not overflowing
    private void incrementPC() {
        pc = (pc + 1) & 0xFF;  // mask to 8 bits, range 0..255
    }

    public void setTargetCycles(int c) {
        this.targetCyclesExecuted = c;
    }

    public void setOutputFileName(String o) {
        this.outputFileName = o;
    }

    public void setInputFileName(String n) {
        this.inputFileName = n;
    }
}
