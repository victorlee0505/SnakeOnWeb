package snake.service.listeners;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import snake.service.threads.SocketThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by taowang on 7/14/2017.
 */
@Component
public class StartUpListener {

    @EventListener(ContextRefreshedEvent.class)
    public void gameStart(){
        System.out.println("listener triggered!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(6666);
                    ExecutorService executorService = Executors.newFixedThreadPool(10);
                    while (true){
                        Socket socket = serverSocket.accept();
                        //get the IP address!!
                        System.out.println((socket.getInetAddress()).getHostName());
                        executorService.execute(new SocketThread(socket));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
