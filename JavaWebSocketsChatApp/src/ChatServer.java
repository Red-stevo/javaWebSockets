import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void handleServer(){
        while (!serverSocket.isClosed()){
            //start the server listener.
            try {
                Socket socket = serverSocket.accept();
                HandleNewConnection handleNewConnection = new HandleNewConnection(socket);
                Thread thread = new Thread(handleNewConnection);
                thread.start();
            } catch (IOException e) {
                try {
                   if(serverSocket != null) serverSocket.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            }
        }
    }
}
