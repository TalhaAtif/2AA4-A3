package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

public class AutoMaze extends Maze {

    //Creates maze 
    AutoMaze(String file, Logger logger) {
        super(file, logger);
    }

    //Uses right hand rule to solve and print maze path
    @Override
    public void runPath() {
        //Creates only one bot facing east
        Explorer bot = new Explorer(0, find_enterance(Direction.EAST), Direction.EAST);

        //Until the bot reaches an exit
        while (!isExit(bot.getX(), Direction.EAST)) {
            //Sets direction to the right of bot, and checks for a wall ahead or on the right
            Direction right = bot.getDir().turnRight();
            boolean wallAhead = isWall(bot.getX() + bot.getDir().changeX, bot.getY() + bot.getDir().changeY);
            boolean wallRight = isWall(bot.getX() + right.changeX, bot.getY() + right.changeY);

            //If there is no wall on the right side,
            //turn right and go forward
            if (!wallRight) {
                bot.turn('R');
                bot.addMove('R');
                bot.move();
                bot.addMove('F');
            //If there is a wall on the right and no wall ahead, go forward
            } else if (!wallAhead) {
                bot.move();
                bot.addMove('F');
            } else {
                //Otherwise keep turning left until there is no wall ahead
                while (wallAhead) {
                    bot.turn('L');
                    bot.addMove('L');
                    wallAhead = isWall(bot.getX() + bot.getDir().changeX, bot.getY() + bot.getDir().changeY);
                }
            }
        }
        //Prints final factorized path
        bot.printPath();
    }

}
