package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    
    //Creates logger
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Starting Maze Runner...");

        //If there are not stricly 2 or 4 arguments, gives user a warning
        if ((args.length != 2) && (args.length != 4)) {
            System.out.println("Input must be [java -jar <program location> -i <\"file path\">] with optional [ -p <\"path\">]");
        }
        //Ensures that the -i and -p are used when needed
        else if ((args.length == 2) && (!args[0].equals("-i"))) {
            System.out.println("Second argument must be [-i]");
        }
        else if ((args.length == 4) && (!args[2].equals("-p"))) {
            System.out.println("Fourth argument must be [-p]");
        }
        //Otherwise, runs program
        else {
        //Creates maze runner and runs it
        MazeRunner tryMaze = new MazeRunner(args, logger);
        tryMaze.run();
        }
    }
}
