public class S12_Sim {

    public static void main(String[] args) {
        //<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>
        S12_IL sim = new S12_IL();
        
        try {
            //Check the number of arguments
            if (args.length == 0 || args.length > 5) {
                System.out.println("ERROR: Incorrect number of arguments.");
                System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
                System.exit(1);
            }
            if ((args.length % 2) != 1) {
                System.out.println("ERROR: Incorrect number of arguments.");
                System.out.println("<memFile> <optional: -o outputFileBaseName> <optional: -c cyclesToExecute>");
                System.exit(1);
            }
            
            //Initialize memory
            sim.intializeMem(args[0]);

            //Initialize sim
            sim.setInputFileName(args[0]);
            
            //Proccess the optional args
            for (int i = 1; i < args.length; i += 2) {
                switch (args[i]) {
                    case "-o":
                    //Set the output file name
                    sim.setOutputFileName(args[i + 1]);
                    break;
                    case "-c":
                    //Verify that flag passes an integer. Parse Int throws exception if cast is not possible.
                    int numClocks = Integer.parseInt(args[i+1]);
                    sim.setTargetCycles(numClocks);
                    break;
                    default:
                    System.out.println("ERROR: Unrecognized optional argument. " + args[i]);
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
        System.out.println(sim.getNumCyclesExectured());
        System.out.println(sim.getProgramCounter());
        System.out.println(sim.getAccumulator());
        
        
    } 
}
