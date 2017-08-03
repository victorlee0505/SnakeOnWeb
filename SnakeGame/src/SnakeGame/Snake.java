package SnakeGame;

import java.util.LinkedList;

public class Snake {
    private final int DELTA = 50;
    private final int[] DIRECTION_UP =    { 0, -1 * DELTA};
    private final int[] DIRECTION_DOWN =  { 0,  DELTA};
    private final int[] DIRECTION_LEFT =  {-1 * DELTA,  0};
    private final int[] DIRECTION_RIGHT = { DELTA,  0};

    private LinkedList<Square> snakeSquares;  //List of snake squares or segments. Square class is simply an int x and y value.

    private int[] currentHeading;  //Direction snake is going in, not direction user is telling snake to go
    private int[] lastHeading;    //Last confirmed movement of snake. See moveSnake method

    private int growThisManySquares = 0;  //Snake grows 1 square at a time, one per clock tick. This tracks how many squares are left to add over a number of clock ticks.

    private int xPosition, yPosition;

    public Snake(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
        createStartSnake();
    }

    protected void createStartSnake(){

        int X = xPosition;
        int Y = yPosition;

        //Create empty list
        snakeSquares = new LinkedList<>();
        snakeSquares.add(new Square(X, Y));
        snakeSquares.add(new Square(X+DELTA, Y));
        snakeSquares.add(new Square(X+(DELTA * 2), Y));

        currentHeading = DIRECTION_LEFT;
        lastHeading = DIRECTION_LEFT;

        growThisManySquares = 0;
    }


    /* Make a copy of the list of squares and return it. Can't return the actual list in
    case something else modifies it. When an object is returned, it's actually a reference
    to that object, not a copy. And then anything that modifies that returned reference
    also modifies the original. */
    public LinkedList<Square> getSnakeSquares(){

        LinkedList<Square> segmentSquares = new LinkedList<Square>();

        for (Square s : snakeSquares) {
            Square sq = new Square(s.getX(), s.getY());
            segmentSquares.add(sq);
        }
        return segmentSquares;

    }

    public void snakeUp()
    {
        currentHeading = DIRECTION_UP;
    }

    public void snakeDown(){
        currentHeading = DIRECTION_DOWN;
    }
    public void snakeLeft(){
        currentHeading = DIRECTION_LEFT;
    }
    public void snakeRight(){
        currentHeading = DIRECTION_RIGHT;
    }


    protected void moveSnake(){

        if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
            currentHeading = DIRECTION_UP;
        }
        if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
            currentHeading = DIRECTION_DOWN;
        }
        if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
            currentHeading = DIRECTION_RIGHT;
        }
        if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
            currentHeading = DIRECTION_LEFT;
        }


        //Make new head, and current heading, to move snake
        Square currentHead = snakeSquares.getFirst();
        int headX = currentHead.getX();
        int headY = currentHead.getY();

        Square newHead = new Square(headX + currentHeading[0], headY + currentHeading[1]);

        //Otherwise, game is still on. Add new head
        snakeSquares.add(0, newHead);

        // Still playing.
        // If snake did not just eat, then remove tail segment
        // to keep snake the same length.
        if (growThisManySquares == 0) {
            snakeSquares.removeLast();
        }
        else {
            growThisManySquares-- ;
        }

        lastHeading = currentHeading; //Update last confirmed heading

    }

    @Override
    public String toString(){
        String textSnake = "";
        int segment = 0;
        for (Square s : snakeSquares) {
            textSnake += String.format("Segment %d [%d, %d]", segment, s.getX(), s.getY());
            segment++;
        }

        return textSnake;
    }


    /* Convenience method for testing if a square is one of the snake squares.
    * This is helpful to decide if a kibble and snake are in the same place i.e. snake
    * has eaten the kibble. Could also be useful to test if the snake has hit a wall or maze. */
    public boolean isThisInSnake(Square testSquare) {

        for (Square s : snakeSquares) {
            if (s.getX() == testSquare.getX() && s.getY() == testSquare.getY()) {
                return true;
            }
        }
        return false;

    }

}
