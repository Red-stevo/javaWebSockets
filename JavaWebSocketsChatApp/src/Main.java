import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        ChatServer chatServer;
        try {
            ServerSocket socket = new ServerSocket(8880);
            chatServer = new ChatServer(socket);
            chatServer.handleServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}