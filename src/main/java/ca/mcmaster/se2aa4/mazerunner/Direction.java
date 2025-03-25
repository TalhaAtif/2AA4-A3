package ca.mcmaster.se2aa4.mazerunner;

//Stores direction and icon
public enum Direction {

    //Stores change in matrix location for each direction
    //And the icon of the direction for debuging maze mode
    NORTH(0,-1, 'U'),
    EAST(1,0, 'R'),
    SOUTH(0,1, 'D'),
    WEST(-1,0,'L');

    //cannot be changed for saftey
    final int changeX;
    final int changeY;
    final char icon;

    //Sets variables for ENUMS
    Direction(int changeX, int changeY, char icon) {
        this.changeX = changeX;
        this.changeY = changeY;
        this.icon = icon;
    }

    //Methods to turn right or left
    public Direction turnRight() {
        return values()[(ordinal() + 1) % 4];
    }

    public Direction turnLeft() {
        return values()[(ordinal() + 3) % 4];
    }

}
