package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

public class UserMaze extends Maze {

    private String path;

    //Creates maze, deleted all spaces from user input and sets to uppercase
    //Then passes through function to expand path
    UserMaze(String file, Logger logger, String userPath) {
        super(file, logger);
        this.path = pathString(userPath.replace(" ", "").toUpperCase());
    }

    //Expands path like "3F" to "FFF", for easier debugging
    private String pathString(String path) {
        StringBuilder updatePath = new StringBuilder();
        int i = 0;

        //Interates through user input
        while (i < path.length()) {
            char currentMove = path.charAt(i);

            //If there is a number
            if (Character.isDigit(currentMove)) {
                StringBuilder number = new StringBuilder();

                //Works for muti digit numbers
                while (i < path.length() && Character.isDigit(path.charAt(i))) {
                    number.append(path.charAt(i));
                    i++;
                }

                //Once number is read convert to int
                int amt = Integer.parseInt(number.toString());
                char nextMove = path.charAt(i);

                //Add number to list
                for (int j = 0; j < amt; j++) {
                    updatePath.append(nextMove);
                }
                i++;
            //If not a number, add to list normally
            } else {
                updatePath.append(currentMove);
                i++;
            }
        }
        //Return formatted string
        return updatePath.toString();
    }

    @Override
    //Runs user path
    public void runPath() {

        //Creates 2 maze explorers, to test path from east and west enterances
        Explorer botEast = new Explorer(0, find_enterance(Direction.EAST), Direction.EAST);
        Explorer botWest = new Explorer(this.maze_board[0].length - 1, find_enterance(Direction.WEST), Direction.WEST);

        //Checks if user input was correct from either direction
        boolean eastResult = pathFrom(Direction.EAST, botEast);

        boolean westResult = pathFrom(Direction.WEST, botWest);


        //Prints result
        if (eastResult || westResult) {
            System.out.println("correct path");
        } else {
            System.out.println("incorrect path");
        }
    }

    //checks user path from certain direction
    private boolean pathFrom(Direction dir, Explorer bot) {
        Direction startDir = dir;
        Direction currDir = startDir;

        //for each move in the user path 
        for (char move : this.path.toCharArray()) {

            //If move is forward, try to move forward or stay is current location
            if (move == 'F') {
                if (!isWall(bot.getX() + currDir.changeX, bot.getY() + currDir.changeY)) {
                    bot.move();
                }
            //Otherwise, rotate bot occording to L or R
            } else {
                bot.turn(move);
                currDir = bot.getDir();
            }
        }
        //If bot reached an exit, return true
        if (isExit(bot.getX(), startDir)) {
            return true;
        }
        return false;
    }
}
