package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

public class MazeRunner {

    //Where use inputted a path as an arg or not
    private Boolean userPath = false;
    private Logger logger;
    //Stores user filepath if needed
    private String filePath = " ";
    //List of args
    private String[] args;

    //If there are 4 arguments, assume user gave path
    MazeRunner(String[] args, Logger logger) {
        if (args.length == 4) {
            this.userPath = true;
        }
        this.filePath = args[1];
        this.logger = logger;
        this.args = args;
    }

    //Runs maze path using, automatic or user verification
    public void run() {
        Maze maze;

        //If user gave path, check it
        if (this.userPath) {
            maze = new UserMaze(this.filePath,this.logger,this.args[3]);
        }
        //Otherwise, solve maze
        else {
            maze = new AutoMaze(this.filePath,this.logger);
        }

        //Liskov, runs path
        maze.runPath();
    }

}
