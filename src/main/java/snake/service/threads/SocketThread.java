package snake.service.threads;

import snake.dto.GameStatus;

import java.io.*;
import java.net.Socket;

/**
 * Created by taowang on 7/14/2017.
 */
public class SocketThread extends Thread {
    private Socket socket = null;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Socket outSocket = null;
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            GameStatus gameStatus = (GameStatus)o;
            //create a client for sending data to the opponent
            outSocket = new Socket(gameStatus.getIpOfOpponent(), 55555);
            OutputStream outputStream = outSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //Transfer data until the game is over
            while (!gameStatus.isOver()) {
                System.out.println(gameStatus); //TODO
                objectOutputStream.writeObject(gameStatus);
                objectOutputStream.flush();

                gameStatus = (GameStatus) (objectInputStream.readObject());
            }
            objectOutputStream.writeObject(gameStatus); //DON'T FORGET!!!
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (outSocket != null)
                    outSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
