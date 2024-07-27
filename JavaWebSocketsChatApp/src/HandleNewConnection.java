import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HandleNewConnection implements Runnable{

    private final Socket socket;
    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    private static final ArrayList<HandleNewConnection> newConnections = new ArrayList<>();
    private String username;

    public HandleNewConnection(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            username = bufferedReader.readLine();
            broadCastMessage("SERVER: "+ username.toUpperCase()+" joined the chat.");
        } catch (IOException e) {
            handleExceptions(bufferedReader, bufferedWriter, socket);
        }
        newConnections.add(this);
    }

    private void broadCastMessage(String message){
        for(HandleNewConnection handleNewConnection : newConnections)
            if(handleNewConnection.username.compareTo(this.username) != 0){
                try {
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    handleExceptions(this.bufferedReader, this.bufferedWriter, this.socket);
                }
        }
    }


    @Override
    public void run() {
        String clientMessage;
        while (socket.isConnected())
            try {
                clientMessage = bufferedReader.readLine();
                broadCastMessage(clientMessage);
            } catch (IOException e) {
                handleExceptions(this.bufferedReader, this.bufferedWriter, this.socket);
                break;
            }
    }

    private void handleChatLeave(){
        broadCastMessage(this.username+"left the chat room!");
    }

    private void handleExceptions(BufferedReader reader,BufferedWriter writer, Socket soc) {
        handleChatLeave();
        try {
            if(soc != null) soc.close();

            if(writer != null) writer.close();

            if(reader != null) reader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
