import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@Data
@Setter
@Getter
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
            newConnections.add(this);
            broadCastMessage("SERVER: "+ username+" joined the chat.");
        } catch (Exception e) {
            handleExceptions(bufferedReader, bufferedWriter, socket);
        }

    }

    private void broadCastMessage(String message) {
        for (HandleNewConnection handleNewConnection : newConnections) {

            if (!username.equals(handleNewConnection.getUsername())) {
                try {
                    handleNewConnection.bufferedWriter.write(message);
                    handleNewConnection.bufferedWriter.newLine();
                    handleNewConnection.bufferedWriter.flush();
                } catch (Exception e) {
                    handleExceptions(bufferedReader, bufferedWriter, socket);
                    break;
                }
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
            } catch (Exception e) {
                handleExceptions(bufferedReader, bufferedWriter, socket);
                break;
            }
    }


    private void handleChatLeave(){
        newConnections.remove(this);
        broadCastMessage(this.username+" left the chat room!");
    }

    private void handleExceptions(BufferedReader reader,BufferedWriter writer, Socket soc) {
        handleChatLeave();
        try {
            if(soc != null) soc.close();

            if(writer != null) writer.close();

            if(reader != null) reader.close();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}










