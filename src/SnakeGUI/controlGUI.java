package SnakeGUI;

import SnakeGame.Control;
import SnakeGame.GamePanel;
import javax.swing.*;


/**
 * This is a control mechanism that handle user interaction from the View's button.
 * Created by victo on 7/24/2017.
 */
public class controlGUI {


    private modelGUI myModel;
    //private Control snakeControl;

    public controlGUI(){

        //Connection to model
        this.myModel = new modelGUI();
    }

    /**
     * openingDial:
     * prompt a confirmation dialog with welcome message.
     * Yes -- proceed to login dialog.
     * No -- straight to main menu without login.
     */
    public void openingDial(){

        final JFrame welcome = new JFrame();

        int result = JOptionPane.showConfirmDialog(welcome," Welcome to Snake on Web!!!  Would you like to login ? \n"+" (Therefore you can submit your highest scores as well as enjoy Online Multi-player mode)","Snake on Web",JOptionPane.YES_NO_OPTION);

        if(result == 0) {
            System.out.print("Yes, Login");
            loginDial();
        }
        else if(result == 1) {
            System.out.println("No, skip");
        }
    }

    /**
     * loginDial:
     * prompt a input dialog for user input of UserID.
     *
     */
    public void loginDial(){

        final JFrame signIn = new JFrame();

        String userID = JOptionPane.showInputDialog(signIn, "Please type in your user name!");

        modelGUI.setUserID(userID);

        System.out.printf("The user's name is '%s'.\n", modelGUI.getUserID());
    }

    /**
     * singleDial:
     * prompt a confirmation dialog proceed to single player mode (change JPanel).
     * Pass Integer (0,1) back to button.
     * 0 -- proceed to singleMode(View).
     * 1 -- no action.
     * @return int
     */
    public int singleDial(){

        final JFrame startGame = new JFrame();

        int result = JOptionPane.showConfirmDialog(startGame," Ready to Start ?","Snake on Web",JOptionPane.YES_NO_OPTION);

        // Resetting game status
        myModel.GameOver=false;
        myModel.score = 100;
        return result;
    }

    /**
     * multiDial:
     * prompt a confirmation dialog proceed to multi player mode (change JPanel).
     * Pass Integer (0,1) back to button.
     * 0 -- proceed to multiMode(View).
     * 1 -- no action.
     * @return int
     */
    public int multiDial(){

        final JFrame startGame = new JFrame();

        int result = JOptionPane.showConfirmDialog(startGame," Look for online players ?","Snake on Web",JOptionPane.YES_NO_OPTION);

        myModel.GameOver=false;
        return result;

    }

    /**
     * scoreDial:
     * prompt a message dialog to show a list of score with userID and score that retrieve from model.
     */
    public void scoreDial(){

        final JFrame scoreShow = new JFrame();

        //myModel.serverData.forEach( k -> System.out.println("Name: "+ k + "----- Score: "));

        String s = myModel.getScoreList();

        JOptionPane.showMessageDialog(scoreShow, s);

    }

    /**
     * startGame:
     * 1. Call by gameArea from View.
     * 2. ask Model to execute game.
     *
     * @return game
     */
    public GamePanel startGame(Control snakeControl, int option){
        myModel.game = null;

        if(option == 1)
        {
            myModel.game = new GamePanel(this.myModel , snakeControl);
        }

        if(option == 2)
        {
            myModel.Multigame = new GamePanel(this.myModel, snakeControl);
        }

        return myModel.game;
    }

    /**
     * Check status of the Snake game.
     * This is used by quitButton to prevent unexpected kill process before the game is finished.
     * YES: quit button can be used to go back main menu JPanel.
     * NO: no action performed.
     * @return boolean
     */
    public boolean isGameEnd(){

        return myModel.GameOver;
    }

    /**
     * resetGame:
     * reset Game status (Start over)
     */
    public void resetGame(){

        //Put the method that reset the game here
        if(myModel.GameOver){
            myModel.GameOver=false;
            myModel.game.reset();
            //myModel.resetGameOver();
            System.out.println("restart pressed");
        }
    }

    /**
     * submitScore:
     * pre con: Player name can not be null. a valid username is needed for score submission.
     *
     * a message will pop up to confirm the submission.
     * Only highest score will be updated to the server
     *
     * the method will call Model to handle the transaction.
     */
    public void submitScore(){


        final JFrame submit = new JFrame();

        if(myModel.getUserID()!=null) {

            int result = JOptionPane.showConfirmDialog(submit, " Your User Name : " + myModel.getUserID() + "  and Your Score is " + myModel.getScore() + "\n" + "Do you want to submit to server ?", "Snake on Web", JOptionPane.YES_NO_OPTION);
            if( result == 0 ) {
                myModel.submitScore();
            }
        }
        else{
            JOptionPane.showMessageDialog(submit, "Login is required for score submission.");
        }
    }

    public static void main(String[] args) {

    }

}
