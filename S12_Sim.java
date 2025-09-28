public class S12_Sim {

    public static void main(String[] args) {
        //<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>
        String[] tempArgs = {"N-Multiplication_Time_Optimized.memfile"};
        S12_IL sim = new S12_IL();
        
        try {
            //Check the number of arguments
            if (tempArgs.length == 0 || tempArgs.length > 5) {
                System.out.println("ERROR: Incorrect number of arguments.");
                System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
                System.exit(1);
            }
            if ((tempArgs.length % 2) != 1) {
                System.out.println("ERROR: Incorrect number of arguments.");
                System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
                System.exit(1);
            }
            
            //Initialize memory
            sim.intializeMem(tempArgs[0]);

            //Initialize sim
            sim.setInputFileName(tempArgs[0].split("\\.")[0]);
            
            //Proccess the optional tempArgs
            for (int i = 1; i < tempArgs.length; i += 2) {
                switch (tempArgs[i]) {
                    case "-o":
                        //Set the output file name
                        sim.setOutputFileName(tempArgs[i + 1]);
                        break;

                    case "-c":
                        //Verify that flag passes an integer. Parse Int throws exception if cast is not possible.
                        int numClocks = Integer.parseInt(tempArgs[i+1]);
                        sim.setTargetCycles(numClocks);
                        break;


                    default:
                    System.out.println("ERROR: Unrecognized optional argument. " + tempArgs[i]);
                    System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
                    System.exit(1);
                }
            }
            
        }
        catch (NumberFormatException nfe) {
            System.out.println("The optional argument flag '-c' must be followed by an integer.");
            System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
            System.exit(1);
        } 
        
        //Start the program
        sim.run();
        //Print Results
        System.out.println("Cycles Executed: " + sim.getNumCyclesExectured());
        System.out.println("PC: 0x" + String.format("%2s", Integer.toHexString(sim.getProgramCounterValue())).replace(" ", "0").toUpperCase());
        System.out.println("ACC: 0x" + String.format("%3s", Integer.toHexString(sim.getAccumulatorValue())).replace(" ", "0").toUpperCase());
        
        
    } 
}
