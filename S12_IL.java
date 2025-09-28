import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class S12_IL implements S12_IL_Interface {

    private int a = 0;
    private int pc = 0;
    private int cyclesExectued = 0;
    private int targetCyclesExecuted;
    private int[] memory = new int[256];
    private File memFile;
    private Scanner scan;
    private final int PC_BITS = 8;
    private final int A_BITS = 12;
    private final int MEMORY_BITS =  A_BITS;
    private String outputFileName;

    
    public static void main(String[] args) {
        //<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>
        try {
            if (args.length == 0 || args.length > 3) {throw new Exception("ERROR: Incorrect number of arguments.");}
            S12_IL sim = new S12_IL();
            sim.initializeMem(args[0]);
            sim.setTargetCycles(-1);

            //Proccess the optional args
            for (int i = 1; i < args.length; i += 2) {
                switch (args[i]) {
                    case "-o":
                        sim.setOutputFileName(args[i + 1]);
                        break;
                    case "-c":
                        sim.setTargetCycles(Integer.parseInt(args[i + 1]));
                        break;
                    default:
                        throw new Exception("ERROR: Unrecognized optional argument.");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("CORRECT USAGE: ");
            System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
        }

        sim.run();

    }

    public boolean initializeMem(String filename) {
        try {
            memFile = new File(filename);
            scan = new Scanner(memFile);
            String temp;
            //READ THE 8 PC BITS
            if (scan.hasNext()) {
                temp = scan.next();
                if (temp.length() != PC_BITS) {
                    throw new Exception("ERROR: Program counter incorrect number of bits in memfile");
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

            //Parse the instructions and data into memory
            int i = 0;
            while (scan.hasNext()) {
                if (i > 255) {throw new Exception("ERROR: Memfile has more instructions than memory can fit.");} 
                temp = scan.next();
                if (temp.length() != MEMORY_BITS) {throw new Exception("ERROR: Memfile has incorrect number of bits on line " + (i + 1));}
                memory[i] = (0xFFF) & Integer.parseInt(temp, 2);
                i++;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("ERROR: File not found. Unable to initialize memeory.");
            return false;
        } catch (Exception e) {
            //THIS MIGHT HIDE OTHER ERRORS
            System.out.println(e.getMessage());
            return false;
        }    
        finally {
            if (scan != null) {scan.close();}
        }
        return true;
    }


    public void run(){
        
         while (true) {
            // Instruction instruction = memory[pc & 0xFF];
            Opcode code = Opcode.fromOperand((memory[pc] >> 8) & 0xF);
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
                    return;

                
                default:
                    throw new AssertionError();
            }
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
}