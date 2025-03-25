package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.Logger;

//Base class for auto and user input mazes
public abstract class Maze {

    //Boolean that stores if peice is wall or path
    protected boolean[][] maze_board;

    //Logger
    protected Logger logger;

    //Creates array of maze size and creates maze
    Maze(String file, Logger logger) {
        this.maze_board = getSize(file);
        this.logger = logger;
        create_maze(file, logger);
    }

    //Creates boolean array of maze size by reading file
    protected boolean[][] getSize(String file) {
        
        int rows = 0;
        int cols = 0;
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line;

            //Goes thru file and counts number of rows, accounts for uneven maze by taking widest possible width
            while ((line = r.readLine()) != null) {
                cols = Math.max(line.length(), cols);
                rows++;
            }

            r.close();
            return new boolean[rows][cols];

            //Error for maze counting
        } catch (Exception e) {
            System.out.println("Error reading maze from file. Ensure file path is correct.");
            System.exit(1);
        }
        return new boolean[0][0];
    }

    //Places peices in maze
    protected void create_maze(String file, Logger logger) {

        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line;

            //Reads file 
            for (int row = 0; row < this.maze_board.length; row++) {
                line = r.readLine();
                for (int col = 0; col < this.maze_board[0].length; col++) {
                    //check if there is a # for wall, otherwise assume path 
                    if ((col < line.length()) && (line.charAt(col) == '#')) {
                        set_piece(col, row, true);
                    } else {
                        set_piece(col, row, false);
                    }
                }
            }
            //Closes reader
            r.close();
        } catch (Exception e) {
            logger.error("Error while reading the maze file", e);
        }
    }

    //Prints out maze, used for debugging
    protected void print_maze() {
        for (int i = 0; i < this.maze_board.length; i++) {
            for (int j = 0; j < this.maze_board[0].length; j++) {
                System.out.print(maze_board[i][j] ? "[#]" : "[ ]");
            }
            System.out.println();
        }
    }

    //Prints out maze with location and direction of bot, used for debugging
    protected void debug_path(int x, int y, char bot) {

        String curr = "";

        for (int i = 0; i < this.maze_board.length; i++) {
            for (int j = 0; j < this.maze_board[0].length; j++) {
                if (this.maze_board[i][j]) {
                    curr = "[#]";
                } else {
                    if (!this.maze_board[i][j]) {
                        curr = "[ ]";
                    }
                    if ((i == y) && (j == x)) {
                        curr = "["+bot+"]";
                    }
                }
                System.out.print(curr);
            }
            System.out.println();
        }
        System.out.println();

    }

    //Checks if given coordinates are a wall
    protected boolean isWall(int x, int y) {
        if (x >= 0 && x < this.maze_board[0].length) {
            if (y >= 0 && y < this.maze_board.length) {
                return (this.maze_board[y][x]);
            }
            return true;
        } else {
            return true;
        }
    }

    //Checks for exit by ensuring chosen spot is on opposite end
    //of where the bot entered
    protected boolean isExit(int x, Direction startDir) {
        if (startDir == Direction.EAST) {
            if (x == this.maze_board[0].length - 1) {
                return true;
            }
        } else {
            if (x == 0) {
                return true;
            }
        }
        return false;
    }

    //Places spot on array
    private void set_piece(int x, int y, boolean wall) {
        this.maze_board[y][x] = wall;
    }

    //Locates enterace given direction bot is facing
    protected int find_enterance(Direction d) {
        if (d == Direction.EAST) {
            for (int i = 0; i < this.maze_board.length; i++) {
                if (!this.maze_board[i][0]) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < this.maze_board.length; i++) {
                if (!this.maze_board[i][this.maze_board[0].length-1]) {
                    return i;
                }
            }
        }
        return 0;
    }

    //All subclasses must have runPath, Liskov
    public abstract void runPath();

}
