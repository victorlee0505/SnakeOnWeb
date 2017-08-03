package SnakeGame;

import java.awt.event.KeyEvent;

/**
 * Created by Jay C on 8/2/2017.
 */
public class AlternateControl extends Control
{
    //key input
    private boolean up,down,right,left,start;
    private static final int UP_key = 87;
    private static final int DOWN_key = 83;
    private static final int LEFT_key = 65;
    private static final int RIGHT_key = 68;
    private static final int ENTER_key = 10;

    public AlternateControl()
    {
        super();
        this.up = false;
        this.down = false;
        this.right = false;
        this.left = false;
    }

    @Override
    public boolean getUp()
    {
        return up;
    }

    @Override
    public boolean getDown()
    {
        return down;
    }

    @Override
    public boolean getLeft()
    {
        return left;
    }

    @Override
    public boolean getRight()
    {
        return right;
    }

    @Override
    public boolean getStart()
    {
        return start;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int k=e.getKeyCode();
        System.out.println(k);
        if(k== UP_key) up=true;
        if(k== DOWN_key) down=true;
        if(k== LEFT_key) left=true;
        if(k== RIGHT_key) right=true;
        if(k== ENTER_key) right=true;

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

        if(k== UP_key) up=false;
        if(k== DOWN_key) down=false;
        if(k== LEFT_key) left = false;
        if(k== RIGHT_key) right=false;
        if(k== ENTER_key) right=false;
    }

}
