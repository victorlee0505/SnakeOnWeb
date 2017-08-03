package SnakeGame;

import SnakeGUI.modelGUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable
{


    private static int WIDTH = 650;
    private static int HEIGHT = 500;

    //Graphics variables
    private Graphics2D g2d;
    private BufferedImage image;

    //Threads
    public Thread thread;
    private boolean running ;
    private long targetTime;

    //useful variables used to do calculations
    private static final int SIZE = 20;
    private static Square head;
    private static Square food;
    private static Square poison;

    private static final ArrayList<Square> snake = new ArrayList<>();
    private static final Map<String, Square> foods = new HashMap<>();
    private static final Map<String, Square> poisons = new HashMap<>();

    //variables used to add more features to the game
    private int score;
    private int level;
    private static final int LEVELUPconstant = 20;
    private static boolean gameOver;
    private static final int STOP = 1000000;
    private static boolean exit;
    private static Control control;
    private static modelGUI modelGUI;
    private static boolean reset;

    //variables used to determine the positions
    private int dx,dy;
    public long wait;


    public GamePanel(modelGUI modelGUI, Control control){
        reset = false;
        this.modelGUI = modelGUI;
        running = true;
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();
        this.control = control;
        addKeyListener(control);
    }

    //reset method
    public void reset()
    {
        snake.clear();
        poisons.clear();
        foods.clear();
        running = true;
        //score = 100;
        //setScore();
        reset = true;
    }
    @Override
    public void addNotify() {
        super.addNotify();
        thread=new Thread(this);
        thread.start();
    }

    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    private void setSpeed(int speed){
        targetTime=1000 / speed;

    }

    /**
     * Randomly generate a position within the size of the screen for food
     * todo : check if the position is within the snake position, food positions, or poison positions
     */
    public void setFood()
    {
        int x = (int)(Math.random() * (WIDTH - SIZE));
        int y = (int)(Math.random() * (HEIGHT - SIZE));

        food.setPosition(x, y);
    }

    /**
     * Randomly generate a position within the size of the screen for poison
     * todo : check if the position is within the snake position, food positions, or poison positions
     */
    public void setPoison()
    {
        int x = (int)(Math.random() * (WIDTH - SIZE));
        int y = (int)(Math.random() * (HEIGHT - SIZE));

        poison.setPosition(x, y);
    }

    /**
     * this method is responsible for running the games using thread
     */
    @Override
    public void run() {
        if(running)
        {
            init();
            long startTime;
            update();
            requestRender();
            long elapsed;
            exit = false;
            while(running && !exit){
                startTime=System.nanoTime();

                update();
                requestRender();

                /*System.out.println("FOOD position is \n" + food);
                System.out.println("SNAKE HEAD POSITION is \n" + snake.get(0));*/


                elapsed=System.nanoTime()-startTime;
                wait=targetTime-elapsed/1000000;
                if(wait>0){

                    if(gameOver)
                    {
                        exit = true;
                    }
                    try{
                        Thread.sleep(wait);
                    }catch(Exception e  ){
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    private void init(){
        image =new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        g2d=image.createGraphics();
        setupLevel();
        gameOver = false;
        setGameOver();
        level = 1;
        setSpeed(level * 5);
    }
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    private void populateSnake()
    {
        //initalized the snake
        head=new Square(SIZE);
        head.setPosition(WIDTH/2, HEIGHT/2);
        snake.add(head);

        for (int i = 1; i < 3; i++){
            Square e=new Square(SIZE);
            e.setPosition(head.getX()+(i*SIZE),head.getY());
            snake.add(e);
        }
    }

    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    private void populateFoods()
    {
        //initalized the foods
        Square e;

        for(int i =  0; i < 5 ; i++)
        {
            food = new Square(SIZE);
            setFood();
            e = food;
            String key = e.toString();
            foods.put(key, e);
        }
    }

    private void populatePoisons()
    {
        //initalized the foods
        Square e;

        for(int i = 0; i < 5; i++)
        {
            poison = new Square(SIZE);
            setPoison();
            e = poison;
            String key = e.toString();
            poisons.put(key, e);
        }
    }
    //=================================================================================
    //=================================================================================
    //=================================================================================

    /**
     * initialize the nessary global variables
     */
    public void setupLevel()
    {
        //initalized the score = 100
        score = 100;
        setScore();

        //initalized the snake
        populateSnake();

        //initalized the foods
        populateFoods();

        //initalized the poisons
        populatePoisons();

    }

    //=================================================================================
    //=================================================================================
    //=================================================================================



    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    private void update()
    {
        //reset the game back to the intialization
        //if the gameOver is true
        if(gameOver)
        {
            if(control.getStart())
            {
                setupLevel();
            }
        }

        //check if the score is below 0
        if(score == 0)
        {
            gameOver = true;
            setGameOver();
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //determination of the position of the snake
        if(control.getUp() && dy==0){
            dy=-SIZE;
            dx=0;
        }
        if(control.getDown()  && dy==0){
            dy=SIZE;
            dx=0;
        }
        if(control.getLeft() && dx==0){
            dy=0;
            dx=-SIZE;
        }
        if(control.getRight() && dx==0 && dy != 0)
        {
            dy=0;
            dx=SIZE;
        }
        if(dx!=0 || dy!=0){
            for (int i=snake.size() - 1;i>0;i--){
                snake.get(i).setPosition(
                        snake.get(i-1).getX(),
                        snake.get(i-1).getY()
                );

            }
            head.move(dx, dy);
        }

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //check if the snake collides with itself
        //--------------------------------------------------------------------------------------------------------------
        for(Square e : snake)
        {
            if(e.isCollsion(head))
            {
                gameOver = true;
                setGameOver();
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        for(Square e : foods.values())
        {
            if(e.equals(head))
                food = e;
        }

        if(food.isCollsion(head))
        {
            score++;
            setScore();
            /*System.out.println("Before Remove");
            System.out.println(food);*/

            //remove the food from the map foods
            foods.remove(food.toString());

            /*System.out.println("After Remove");
            System.out.println(food);*/

            //add a new random food in again
            setFood();
            foods.put(food.toString(), food);
            /*System.out.println("NEW FOOD ADD ");
            System.out.println(food);*/

            Square e=new Square(SIZE);
            /*System.out.println(e);*/
            e.setPosition(-50, -100);
            snake.add(e);

            System.out.println("BEFORE");
            System.out.println("Score is " + score);
            System.out.println("LEVEL is " + level);

            if((score > 100) && (score % LEVELUPconstant == 0))
            {
                System.out.println("ENTER!!!!!!!");
                level ++;

                if(level > LEVELUPconstant)
                    level = 1;
                setSpeed(level * 5);
            }

            System.out.println("AFTER");

            System.out.println("Score is " + score);
            System.out.println("LEVEL is " + level);
        }
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(Square e : poisons.values())
        {
            if(e.equals(head))
                poison = e;
        }

        if(poison.isCollsion(head))
        {
            score--;
            setScore();
            /*System.out.println("Before Remove");
            System.out.println(food);*/

            //cut the tail off
            snake.remove(snake.size() - 2);
            //remove the poison from the map foods
            poisons.remove(poison.toString());

            /*System.out.println("After Remove");
            System.out.println(food);*/

            //add a new random poison in again
            setPoison();
            poisons.put(poison.toString(), poison);
            /*System.out.println("NEW FOOD ADD ");
            System.out.println(food);*/
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //this section handles the collision to the wall
        //todo: add more features if necessary
        if(head.getX()<0)
            head.setX(WIDTH - SIZE);
        if(head.getY()<0)
            head.setY(HEIGHT - SIZE);
        if(head.getX()>WIDTH - SIZE)
            head.setX(0);
        if(head.getY()>HEIGHT - SIZE)
            head.setY(0);

        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????
    }

    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------

    /**
     * the requestRender method removes the previous graphic from the sc
     */
    private void requestRender() {
        render(g2d);
        Graphics g=getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }


    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------

    /**
     * the role is to draw out the graphics
     * @param g2d a 2-Dimensional Graphics
     */
    public void render(Graphics2D g2d) {

        if(snake.size() == 1)
        {
            gameOver = true;
            setGameOver();
        }


        g2d.clearRect(0, 0, WIDTH, HEIGHT);

        //draw Snake
        for (Square e : snake)
        {
            if(e.equals(snake.get(0)))
                g2d.setColor(Color.GRAY);
            e.render(g2d);
            g2d.setColor(Color.GREEN);
        }

        //draw foods
        g2d.setColor(Color.RED);

        for(Square e : foods.values())
        {
            e.render(g2d);
        }

        //draw poisons
        g2d.setColor(Color.MAGENTA);
        for(Square e : poisons.values())
        {
            e.render(g2d);
        }

        g2d.setColor(Color.BLUE);

        if(gameOver && !reset)
        {
            running = false;
            JOptionPane.showMessageDialog(null, "GAME OVER!!!!!!", "Lost Message", JOptionPane.INFORMATION_MESSAGE);

            g2d.setFont(new Font("BOLD", Font.BOLD, 50));
            g2d.drawString("GameOver!", WIDTH / 2 - WIDTH / 4, HEIGHT / 2);

            reset();

        }

        g2d.setFont(new Font("default",Font.ITALIC, 12));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score : " + score + "   Level : " + level, 10, 10 );

        if(dx == 0 && dy == 0)
        {
            g2d.drawString("Ready!", 150, 200);
        }
    }

    public void setScore()
    {
        modelGUI.setScore(this.score);
    }

    public void setGameOver()
    {
        modelGUI.setGameOver(this.gameOver);
    }
}