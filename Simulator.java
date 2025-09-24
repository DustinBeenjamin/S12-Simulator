import java.util.Scanner;

public class Simulator {

    byte a = 12;
    byte pc = 0;
    Instruction[] memory = new Instruction[256];

    
    public static void main(String[] args) {
        
        Simulator sim = new Simulator();
        sim.run();

        


    }


    public void run(){
        ////Testing ADD/SUB
        //LOAD MEMORY
        // memory[0] = new Instruction(Opcode.ADD, (byte) 100);
        // memory[1] = new Instruction(Opcode.ADD, (byte) 101);
        // memory[2] = new Instruction(Opcode.ADD, (byte) 102);
        // memory[3] = new Instruction(Opcode.ADD, (byte) 103);
        // memory[4] = new Instruction(Opcode.ADD, (byte) 104);
        // memory[5] = new Instruction(Opcode.ADD, (byte) 105);
        // memory[6] = new Instruction(Opcode.ADD, (byte) 106);
        // memory[7] = new Instruction(Opcode.ADD, (byte) 107);
        // memory[8] = new Instruction(Opcode.ADD, (byte) 108);
        // memory[9] = new Instruction(Opcode.ADD, (byte) 109);
        // memory[10] = new Instruction(Opcode.HALT, (byte) 0x00);

        // //Testing Other Functions
        // memory[0] = new Instruction(Opcode.LOADI, (byte) 0xFB);
        // memory[1] = new Instruction(Opcode.ADD, (byte) 101);
        // memory[2] = new Instruction(Opcode.JN, (byte) 0x01);
        // memory[3] = new Instruction(Opcode.ADD, (byte) 104);
        // memory[4] = new Instruction(Opcode.SUB, (byte) 101);
        // memory[5] = new Instruction(Opcode.JN, (byte) 101);
        // memory[6] = new Instruction(Opcode.ADD, (byte) 101);
        // memory[7] = new Instruction(Opcode.STORE, (byte) 99);
        // memory[8] = new Instruction(Opcode.ADD, (byte) 104);
        // memory[9] = new Instruction(Opcode.SUB, (byte) 99);
        // memory[10] = new Instruction(Opcode.ADD, (byte) 104);
        // memory[11] = new Instruction(Opcode.ADD, (byte) 104);
        // memory[12] = new Instruction(Opcode.AND, (byte) 104);
        // memory[13] = new Instruction(Opcode.OR, (byte) 103);
        // memory[14] = new Instruction(Opcode.HALT, (byte) 0x00);

        
        // //USED FOR TESTS ABOVE
        // memory[99] = new Instruction(Opcode.J, (byte) 0x00);  // 0
        // memory[100] = new Instruction(Opcode.J, (byte) 0x00);  // 0
        // memory[101] = new Instruction(Opcode.J, (byte) 0x01);  // 1
        // memory[102] = new Instruction(Opcode.J, (byte) 0x02);  // 2
        // memory[103] = new Instruction(Opcode.J, (byte) 0x03);  // 3
        // memory[104] = new Instruction(Opcode.J, (byte) 0x04);  // 4
        // memory[105] = new Instruction(Opcode.J, (byte) 0xFB);  //-5
        // memory[106] = new Instruction(Opcode.J, (byte) 0xFC);  //-4
        // memory[107] = new Instruction(Opcode.J, (byte) 0xFD);  //-3
        // memory[108] = new Instruction(Opcode.J, (byte) 0xFE);  //-2
        // memory[109] = new Instruction(Opcode.J, (byte) 0xFF);  //-1
       
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

        // //Testing Max Finder
        
    
        // memory[0] = new Instruction(Opcode.JN, (byte) 19);  
        // memory[1] = new Instruction(Opcode.JZ, (byte) 19);  
        // memory[2] = new Instruction(Opcode.STORE, (byte) 98);
        
        // //SAVE A CONSTANT OF 1
        // memory[3] = new Instruction(Opcode.LOADI, (byte) 1);
        // memory[4] = new Instruction(Opcode.STORE, (byte) 97);
        
        // //Do the subtraction 
        // memory[5] = new Instruction(Opcode.LOAD, (byte) 100);  //THIS GETS INCREMENTED EACH LOOP
        // memory[6] = new Instruction(Opcode.SUB, (byte) 99);
        
        // //CONDITIONS TO SKIP CHANGING THE LARGEST VALUE
        // memory[7] = new Instruction(Opcode.JN, (byte) 11);
        // memory[8] = new Instruction(Opcode.JZ, (byte) 11);

        // //CHANGE THE LARGEST VALUE
        // memory[9] = new Instruction(Opcode.LOAD, (byte) 100);  //THIS GETS INCREMENTED EACH LOOP
        // memory[10] = new Instruction(Opcode.STORE, (byte) 99);  

        // //MODIFY THE INSTRUCTIONS
        // memory[11] = new Instruction(Opcode.LOAD, (byte) 5);
        // memory[12] = new Instruction(Opcode.ADD, (byte) 97);
        // memory[13] = new Instruction(Opcode.STORE, (byte) 5);
        // memory[14] = new Instruction(Opcode.STORE, (byte) 9);

        // //SUBTRACT 1 FROM LOOP COUNTER
        // memory[15] = new Instruction(Opcode.LOAD, (byte) 98);  
        // memory[16] = new Instruction(Opcode.SUB, (byte) 97);
        // memory[17] = new Instruction(Opcode.STORE, (byte) 98);  

        // //REPEAT LOOP
        // memory[18] = new Instruction(Opcode.J, (byte) 0);  


        // //HALT
        // memory[19] = new Instruction(Opcode.HALT, (byte) 00);  







        // // memory[97] = new Instruction(Opcode.J, (byte) 0x00);  // SINCE THERE IS NOT SUBI, USE THIS TO STORE A VALUE YOU WANT TO SUBTRACT
        // // memory[98] = new Instruction(Opcode.J, (byte) 0x00);  // LOOPS COUNTER, STARTS AT N, WHICH IS ON THE ACCUMULATOR AT START
        // // memory[99] = new Instruction(Opcode.J, (byte) 0x00);  // PUT THE LARGEST NUMBER HERE

        // // memory[100] = new Instruction(Opcode.J, (byte) 42);  
        // // memory[101] = new Instruction(Opcode.J, (byte) -10);  
        // // memory[102] = new Instruction(Opcode.J, (byte) 100);  
        // // memory[103] = new Instruction(Opcode.J, (byte) 78);  
        // // memory[104] = new Instruction(Opcode.J, (byte) 127);  
        // // memory[105] = new Instruction(Opcode.J, (byte) 42);  
        // // memory[106] = new Instruction(Opcode.J, (byte) -2);   <---------------
        // // memory[107] = new Instruction(Opcode.J, (byte) 1);  
        // // memory[108] = new Instruction(Opcode.J, (byte) 10);  
        // // memory[109] = new Instruction(Opcode.J, (byte) 20);  


        // memory[97] = new Instruction(Opcode.J, (byte) 0x00);  // SINCE THERE IS NOT SUBI, USE THIS TO STORE A VALUE YOU WANT TO SUBTRACT
        // memory[98] = new Instruction(Opcode.J, (byte) 0x00);  // LOOPS COUNTER, STARTS AT N, WHICH IS ON THE ACCUMULATOR AT START
        // memory[99] = new Instruction(Opcode.J, (byte) 0x00);  // PUT THE LARGEST NUMBER HERE

        // memory[100] = new Instruction(Opcode.J, (byte) 42);  
        // memory[101] = new Instruction(Opcode.J, (byte) -10);  
        // memory[102] = new Instruction(Opcode.J, (byte) 100);  
        // memory[103] = new Instruction(Opcode.J, (byte) 78);  
        // memory[104] = new Instruction(Opcode.J, (byte) 127);  
        // memory[105] = new Instruction(Opcode.J, (byte) 42);  
        // memory[106] = new Instruction(Opcode.J, (byte) -2);  
        // memory[107] = new Instruction(Opcode.J, (byte) 1);  
        // memory[108] = new Instruction(Opcode.J, (byte) 10);  
        // memory[109] = new Instruction(Opcode.J, (byte) 20);  


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 


        
        //Testing Selection Sort
        
        
        memory[0]  = new Instruction(Opcode.SUB, (byte) 99);     //CALCULATE i = N - 1 
        memory[1]  = new Instruction(Opcode.JN, (byte) 46);      //ZERO SIZE ARRAYS ARE ALREADY SORTED
        memory[2]  = new Instruction(Opcode.JZ, (byte) 46);      //ONE ELEMENT ARRAYS ARE ALREADY SORTED (IF N -1 = 0, THEN N = 1)
        memory[3]  = new Instruction(Opcode.STORE, (byte) 101);  //STORE THE COUNTER i
        memory[4]  = new Instruction(Opcode.STORE, (byte) 102);  //STORE THE COUNTER j, which starts at i for each for loop
        
        
        memory[5]  = new Instruction(Opcode.LOAD, (byte) 106);   //GRAB THE CURRENT NUMBER      <---------------------------------------DYNAMIC INSTRUCTION (OUTER)
        memory[6]  = new Instruction(Opcode.STORE, (byte) 100);  //STORE THE CURRENT SMALLEST NUMBER
        memory[7]  = new Instruction(Opcode.STORE, (byte) 103);  //STORE THE CURRENT NUMBER


        //Default the smallest address to the location of the outer loop number
        memory[8]  = new Instruction(Opcode.LOAD, (byte) 5);   
        memory[9]  = new Instruction(Opcode.STORE, (byte) 105); 



        //Inner Loop Counter Check
        memory[10]  = new Instruction(Opcode.LOAD, (byte) 102);   //LOAD j
        memory[11]  = new Instruction(Opcode.JZ, (byte) 28);      //JUMP IF j = 0 (END INNER FOR LOOP)

        //Inner Loop
        memory[12] = new Instruction(Opcode.LOAD, (byte) 100);   //GRAB THE CURRENT SMALLEST NUM
        memory[13] = new Instruction(Opcode.SUB, (byte) 107);    //SUBTRACT THE VALUE THAT NEEDS TO BE COMPARED    <--------------------DYNAMIC INSTRUCTION (INNER)
        memory[14] = new Instruction(Opcode.JN, (byte) 20);      //SMALLEST DOESNT NEED TO CHANGE, SKIP LINES
        memory[15] = new Instruction(Opcode.JZ, (byte) 20);      //SMALLEST DOESNT NEED TO CHANGE, SKIP LINES
        memory[16] = new Instruction(Opcode.LOAD, (byte) 107);   //REGRAB THE VALUE    <------------------------------------------------DYNAMIC INSTRUCTION (INNER)
        memory[17] = new Instruction(Opcode.STORE, (byte) 100);  //SAVE IT AS THE NEW SMALLEST
        memory[18] = new Instruction(Opcode.LOAD, (byte) 16);    //LOAD THE INSTRUCTION THAT FOUND THE SMALLEST NUMBER
        memory[19] = new Instruction(Opcode.STORE, (byte) 105);  //SAVE WHERE YOU FOUND THE SMALLEST ADDRESS
        
        //Modify inner loop instructions for next time
        memory[20] = new Instruction(Opcode.LOAD, (byte) 13);  
        memory[21] = new Instruction(Opcode.ADD, (byte) 99);     //INCREMENT THE ADDRESS THE REFERENCED INSTRUCTION ACTS ON
        memory[22] = new Instruction(Opcode.STORE, (byte) 13);   //UPDATE INNER LOOP DYNAMIC INSTRUCTIONS
        memory[23] = new Instruction(Opcode.STORE, (byte) 16);   //UPDATE INEER LOOP DYNAMIC INSTRUCTOINS

        //Update inner loop counter and reattempt inner loop
        memory[24] = new Instruction(Opcode.LOAD, (byte) 102);  
        memory[25] = new Instruction(Opcode.SUB, (byte) 99);  
        memory[26] = new Instruction(Opcode.STORE, (byte) 102);
        memory[27] = new Instruction(Opcode.J, (byte) 10);  

        //Inner loop end - Make Swaps
        memory[28] = new Instruction(Opcode.LOAD, (byte) 105);   //LOAD WHERE THE SMALLEST NUMBER CAME FROM
        memory[29] = new Instruction(Opcode.STORE, (byte) 31);   //STORE THE ADDRESS TO THE INSTUCTION THAT WILL SAVE THE OUTER LOOP NUM TO WHRE THE SMALLEST NUM CAME FROM
        memory[30] = new Instruction(Opcode.LOAD, (byte) 103);   //LOAD THE OUTER LOOP NUM  
        memory[31] = new Instruction(Opcode.STORE, (byte) 0);    //STORE IT TO WHERE THE SMALLEST NUM WAS FOUND (0 is arbitrary as it is dynamicly rewritten)
        memory[32] = new Instruction(Opcode.LOAD, (byte) 100);   //LOAD SMALLEST NUMBER
        memory[33] = new Instruction(Opcode.STORE, (byte) 106);  //SAVE IT TO WHERE THE OUTER LOOP NUM CAME FROM       <----------------DYNAMIC INSTRUCTION (OUTER)
        
        //Update outer loop dynamic instructions
        memory[34] = new Instruction(Opcode.LOAD, (byte) 33);    //LOAD A OUTER LOOP DYNAMIC INSTRUCTION
        memory[35] = new Instruction(Opcode.ADD, (byte) 99);     //INCREMENT THE ADDRESS IT CHANGES
        memory[36] = new Instruction(Opcode.STORE, (byte) 5);    //Save the update outer loop dynamic instructions
        // memory[37] = new Instruction(Opcode.STORE, (byte) 30);   //...
        memory[37] = new Instruction(Opcode.STORE, (byte) 33);   //...
        memory[38] = new Instruction(Opcode.STORE, (byte) 33);   //...

        //Reset inner loop instructions
        memory[39] = new Instruction(Opcode.ADD, (byte) 99);     //The inner loop should start by loading 1 address after the outer loop num address  
        memory[40] = new Instruction(Opcode.STORE, (byte) 13);   //Reset where the inner loop instructions pull their first num from  
        memory[41] = new Instruction(Opcode.STORE, (byte) 16);   //...
        
        //Outer loop ends - attempt to loop again
        memory[42] = new Instruction(Opcode.LOAD, (byte) 101);   //Load i
        memory[43] = new Instruction(Opcode.SUB, (byte) 99);     //Subtract 1
        memory[44] = new Instruction(Opcode.STORE, (byte) 101);  //Store the new i
        memory[45] = new Instruction(Opcode.J, (byte) 1);        //Attempt the outer loop again

        //Halt program
        memory[46] = new Instruction(Opcode.HALT, (byte) 0);     //Halt Program




        

        




        memory[98] = new Instruction(Opcode.J, (byte) 1);  
        memory[99] = new Instruction(Opcode.J, (byte) 1);  
        memory[100] = new Instruction(Opcode.J, (byte) 0);  
        memory[101] = new Instruction(Opcode.J, (byte) 0);  
        memory[102] = new Instruction(Opcode.J, (byte) 0);  
        memory[103] = new Instruction(Opcode.J, (byte) 0);  
        memory[104] = new Instruction(Opcode.J, (byte) 0);  
        memory[105] = new Instruction(Opcode.J, (byte) 0);

        // memory[106] = new Instruction(Opcode.J, (byte) 52); 
        // memory[107] = new Instruction(Opcode.J, (byte) 51);  
        // memory[108] = new Instruction(Opcode.J, (byte) 52);  
        // memory[109] = new Instruction(Opcode.J, (byte) 51);
        // memory[110] = new Instruction(Opcode.J, (byte) 50);


        memory[106] = new Instruction(Opcode.J, (byte)  -127);                                                   
        memory[107] = new Instruction(Opcode.J, (byte)   5);                                                                
        memory[108] = new Instruction(Opcode.J, (byte)  -1);                                                      
        memory[109] = new Instruction(Opcode.J, (byte)  17);                                                         
        memory[110] = new Instruction(Opcode.J, (byte)   2);                                                                  
        memory[111] = new Instruction(Opcode.J, (byte)  19);                                                           
        memory[112] = new Instruction(Opcode.J, (byte) 120);                                                              
        memory[113] = new Instruction(Opcode.J, (byte)  21);                                                    
        memory[114] = new Instruction(Opcode.J, (byte)  16);                                                    
        memory[115] = new Instruction(Opcode.J, (byte)  17);                                                         
        memory[116] = new Instruction(Opcode.J, (byte)  73);                                                                     
        memory[117] = new Instruction(Opcode.J, (byte)  25);   











        
        



         while (true) {
            
            Instruction instruction = memory[pc & 0xFF];
            Opcode code = instruction.getOpcode();
            byte operand = instruction.getOperand();

            System.out.println("______________________________________________________");
            System.out.println("PC: " + pc + "  Code: " + code + "  Operand: " + operand);
            System.out.println("\tA: " + a);
            System.out.println("\t100(small): \t" + memory[100].getOperand());
            System.out.println("\t101(i):\t\t " + memory[101].getOperand());
            System.out.println("\t102(j):\t\t " + memory[102].getOperand());
            System.out.println("\t103(current):\t " + memory[103].getOperand());
            System.out.println("\t105(addr):\t " + memory[105].getOperand());
            System.out.println("\t106: " + memory[106].getOperand());
            System.out.println("\t107: " + memory[107].getOperand());
            System.out.println("\t108: " + memory[108].getOperand());
            System.out.println("\t110: " + memory[109].getOperand());
            System.out.println("\t111: " + memory[109].getOperand());
            System.out.println("\t112: " + memory[109].getOperand());
            System.out.println("\t113: " + memory[109].getOperand());
            System.out.println("\t114: " + memory[109].getOperand());
            System.out.println("\t115: " + memory[109].getOperand());
            System.out.println("\t116: " + memory[109].getOperand());
            System.out.println("\t117: " + memory[109].getOperand());

            System.out.println("\t110: " + memory[110].getOperand());

            System.out.println("______________________________________________________");

            switch (code) {
                case J:
                    J(operand);
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
    public void J(byte o){
        int memoryIndex = 0xFF & o;
        pc = (byte) memoryIndex; 
    } 

    //JUMP IF NEGATIVE
    public void JN(byte o){
        if (a < 0) {
            pc = o;
        } else {
            incrementPC();
        }
    }

    //JUMP IF ZERO
    public void JZ(byte o){
        if (a == 0) {
            pc = o;
        } else {
            incrementPC();
        }
    }

    //LOAD
    public void LOAD(byte o) {
        int memoryIndex = 0xFF & o;
        a = memory[memoryIndex].getOperand();
        incrementPC();
    }

    //STORE
    public void STORE(byte o){
        int memoryIndex = 0xFF & o;
        memory[memoryIndex].setOperand(a);
        incrementPC();
    }

    //LOAD IMMEDIATE
    public void LOADI(byte o){
        a = o;
        incrementPC();
    }

    //BITWISE AND
    public void AND(byte o){
        int memoryIndex = 0xFF & o;
        a &= memory[memoryIndex].getOperand();
        incrementPC();
    }

    //BITWISE OR
    public void OR(byte o) {
        int memoryIndex = 0xFF & o;
        a |= memory[memoryIndex].getOperand();
        incrementPC();
    }

    //ADD
    public void ADD(byte o) {
        int memoryIndex = 0xFF & o;
        a += memory[memoryIndex].getOperand();
        incrementPC();
    }

    //SUB
    public void SUB(byte o) {
        int memoryIndex = 0xFF & o;
        a -= memory[memoryIndex].getOperand();
        incrementPC();
    }

    //HALT
    public void HALT(){
        System.out.println("PROGRAM END");
        System.out.println("\t106: " + memory[106].getOperand());
        System.out.println("\t107: " + memory[107].getOperand());
        System.out.println("\t108: " + memory[108].getOperand());
        System.out.println("\t110: " + memory[109].getOperand());
        System.out.println("\t111: " + memory[110].getOperand());
        System.out.println("\t112: " + memory[111].getOperand());
        System.out.println("\t113: " + memory[112].getOperand());
        System.out.println("\t114: " + memory[113].getOperand());
        System.out.println("\t115: " + memory[114].getOperand());
        System.out.println("\t116: " + memory[115].getOperand());
        System.out.println("\t117: " + memory[116].getOperand());


    }

    //Program counter is only 8 bits, so pc++ runs the risk of not overflowing
    private void incrementPC() {
        pc = (byte) (pc + 1);  // mask to 8 bits, range 0..255
    }

}