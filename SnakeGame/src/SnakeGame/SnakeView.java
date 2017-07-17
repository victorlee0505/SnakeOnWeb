package SnakeGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * Created by Jay C on 7/7/2017.
 */
public class SnakeView implements KeyListener
{
    //declare instance variables
    public static int x, y;
    private static int vx, vy;
    private static Graphics2D g;
    private static int WIDTH ;
    private static int HEIGHT;
    private final static int DELTA = 5;
    private final static int DELTA_SQUARE= 50;
    private static boolean Playing;

    private static Snake snake;
    private static Square square;
    private JFrame f;
    private static Circle circle;
    private static int randomX, randomY;
    private static int msElapsed;
    private final static int CONSTANT =  200;
    private static int UP, DOWN, RIGHT, LEFT;
    private static int CENTER_X, CENTER_Y;

    //==============================================================================
    //===============================================================================
    //================================================================================
    //parameterized constructor with given width and height
    public SnakeView(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;
        display();
    }

    public void display()
    {
        f = new JFrame();  //This is making the GUI box
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //This is telling it how to close
        f.setTitle("Snake Game");  //This is the title of the game.  You can change if this you want.
        f.setResizable(false);      //Makes it so the GUI can’t be resized.
        Canvas c = new Canvas();   //This is the background
        c.setSize(WIDTH, HEIGHT);  //this is setting the dimensions of the background
        f.add(c);      //This is connecting the background with the JFrame f
        f.pack();    // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
        f.setVisible(true);   //Makes it visible
        g = (Graphics2D)c.getGraphics();    //Gives Graphics g a value
        f.addKeyListener(this);

        CENTER_X = WIDTH / 2;
        CENTER_Y = HEIGHT / 2;

        snake = new Snake(CENTER_X, CENTER_Y);
        msElapsed = 0;
        x = CENTER_X;
        y = CENTER_Y;

        randomX= (int)(Math.random() * WIDTH);
        randomY = (int)(Math.random() * HEIGHT);

        Point2 position = new Point2(randomX, randomY);

        //System.out.println("1 "+UP);

        circle = Circle.generate(position);

        msElapsed = 0;
        draw();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Arrow key Up controls the direction going up
     * Arrow key Down controls the direction going down
     * Arrow key Left controls the direction going left
     * Arrow key Right controls the direction gong right
     *
     * @param e Invoked when a key has been pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        checkCollision();
        int pressed = e.getKeyCode();

        //System.out.println(up);

        if(pressed == 38 && y > 0)
        {
            snake.snakeUp();
            snake.moveSnake();
        }

        if(pressed== 40 && y < HEIGHT - DELTA_SQUARE)
        {
            snake.snakeDown();
            snake.moveSnake();
        }
        if(pressed == 39 && x < WIDTH - DELTA_SQUARE)
        {
            snake.snakeRight();
            snake.moveSnake();
        }
        if(pressed == 37 && x > 0)
        {
            snake.snakeLeft();
            snake.moveSnake();
        }
    }
    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************



    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************
    private static void checkCollision()
    {
        UP = y - DELTA_SQUARE;
        DOWN = y + DELTA_SQUARE;
        LEFT = x - DELTA_SQUARE;
        RIGHT = x + DELTA_SQUARE;

    }
    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Draw images using Graphic g
     * such as Rectangle, and Circle
     */
    public void draw() {
        //Make the background Blue
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Make the Snake
        drawSnake();
    }

    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************
    private void randomGenerate()
    {
        shiftLeft();
        g.setColor(Color.ORANGE);
        g.fillOval(randomX, randomY, circle.getDelta(), circle.getDelta());

    }

    /**
     * shift the randomGenerate x-axies and y-axis to the right position
     */
    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************
    private void shiftLeft()
    {
        randomX = ((randomX / circle.getDelta() ) * circle.getDelta() );
        randomY = ((randomY / circle.getDelta()) * circle.getDelta());
        x = ((x / DELTA_SQUARE) * DELTA_SQUARE);
        y = ((y / DELTA_SQUARE) * DELTA_SQUARE);
    }


    /**
     * Detect Collision
     */

    //*****************************************************************************************
    //*****************************************************************************************
    //*****************************************************************************************
    private static boolean DetectCollision()
    {
        checkCollision();
        if((UP == randomY&& x == randomX) || (DOWN == randomY && x == randomX) || (RIGHT == randomX  && y == randomY) || (LEFT == randomX && y == randomY))
        {
            return true;
        }
        return false;


    }

    //*******************************************************************************************************************************************
    //*******************************************************************************************************************************************
    //*******************************************************************************************************************************************
    private static void cornersMove()
    {
        if(y > 0)
        {
            y = y - DELTA;
        }

        if(y < HEIGHT - DELTA_SQUARE)
        {
            y = y + DELTA;
        }
        if(x < WIDTH - DELTA_SQUARE)
        {
            x = x - DELTA;
        }
        if(x > 0)
        {
            x = x + DELTA;
        }
    }

    private static void drawSnake()
    {
        for(Square sq : snake.getSnakeSquares())
        {
            if(sq.equals(snake.getSnakeSquares().getFirst()))
            {
                g.setColor(Color.YELLOW);
                g.fillRect(sq.getX(),sq.getY(),DELTA_SQUARE, DELTA_SQUARE);
            }
            else
            {
                g.setColor(Color.PINK);
                g.fillRect(sq.getX(),sq.getY(),DELTA_SQUARE, DELTA_SQUARE);
            }

        }

    }


    /**
     * Invoked the class to display the game
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------
    public void play()
    {
        boolean hit = false;
        Color tempColor = Color.BLUE;

        int xTemp = -10000, yTemp = -200000;
        Playing  = true;

        while(Playing == true)
        {
            //System.out.println(x);
            draw();
            msElapsed += 500;
            randomGenerate();
            checkCollision();
            snake.moveSnake();

            //cornersMove();
            int nextX = x;
            int nextY = y;

            //set the colour of the "snake" rectangular at the random generated rectangle temporatorily
            if(nextX == randomX  && nextY == randomY)
            {
                tempColor = Color.PINK;
                g.setColor(tempColor);
                g.fillRect(randomX, randomY, DELTA_SQUARE, DELTA_SQUARE);
                hit = true;
            }
            else
                tempColor = Color.BLUE;

            if(hit && DetectCollision())
            {
                xTemp = randomX;
                yTemp =  randomY;
            }


            //set the colour of the random generated circle permanently Blue at all time
            g.setColor(tempColor);
            g.fillRect(xTemp, yTemp, circle.getDelta(), circle.getDelta());

            try {
                Thread.sleep(700);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        SnakeView game = new SnakeView(650, 500);
        game.play();
    }


}
