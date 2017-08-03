package SnakeGame;

import org.w3c.dom.events.Event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jay C on 8/1/2017.
 */
public class Control implements KeyListener
{

    //key input
    private boolean up,down,right,left,start;

    public static int UP_key = 38;
    public static int DOWN_key = 40;
    public static int LEFT_key = 37;
    public static int RIGHT_key = 39;
    public static int ENTER_key = 10;

    public Control()
    {
        up = false;
        down = false;
        right = false;
        left = false;
        start = false;
    }

    public boolean getUp()
    {
        return up;
    }

    public boolean getDown()
    {
        return down;
    }

    public boolean getRight()
    {
        return right;
    }

    public boolean getLeft()
    {
        return left;
    }

    public boolean getStart()
    {
        return start;
    }

    /**
     * If the keyPressed is UP arrow , the snake will move upward <br>
     * If the keyPressed is Down arrow, the snake will move downward <br>
     * If the keyPressed is Left arrow, the snake will move LeftWard <br>
     * If the keyPressed is Right arrow, the snake will move RightWard <br>
     *
     * By default, the snake starts moving Rightward if the keyPressed is Enter
     * @param e keyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int k=e.getKeyCode();
        System.out.println(k);
        if(k == UP_key) up=true;
        if(k == DOWN_key) down=true;
        if(k == LEFT_key) left=true;
        if(k == RIGHT_key) right=true;
        if(k == ENTER_key) right=true;

    }

    /**
     * Determine if the following keys are released: <br>
     *       keyReleased = UP arrow <br>
     *       keyReleased = Down arrow <br>
     *       keyReleased = Left arrow <br>
     *       keyReleased = Right arrow <br>
     *       keyReleased = Enter arrow <br>
     * @param e keyListener
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int k=e.getKeyCode();

        if( k== UP_key) up=false;
        if( k== DOWN_key) down=false;
        if( k== LEFT_key) left = false;
        if( k== RIGHT_key) right=false;
        if( k== ENTER_key) right=false;
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
