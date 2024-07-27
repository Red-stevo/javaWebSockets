import java.net.Socket;

public class HandleNewConnection implements Runnable{

    private Socket socket;


    public HandleNewConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
