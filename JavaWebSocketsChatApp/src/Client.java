import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket socket;

    private String chatUsername;

    private BufferedWriter bufferedWriter;

    private BufferedReader bufferedReader;
    public Client(Socket socket, String chatUsername) {
        this.socket = socket;
        this.chatUsername = chatUsername;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            handleExceptions(this.bufferedWriter, this.bufferedReader, this.socket);
        }
    }


    public static void main(String[] args) throws IOException {
        Socket soc = new Socket("127.0.0.1", 8880);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your Username : ");
        String username = scanner.next();

        Client client = new Client(soc, username);
        client.sendMessages();
        client.receiveMessages();
    }

    private void receiveMessages(){
        while (socket.isConnected()){
            try {
                while (socket.isConnected()){
                    String message = bufferedReader.readLine();
                    System.out.println(message);
                }
            } catch (IOException e) {
                handleExceptions(this.bufferedWriter, this.bufferedReader, this.socket);
            }
        }
    }


    private void sendMessages(){
        Scanner scanner = new Scanner(System.in);
        Thread thread = new Thread(() -> {
            try {
                bufferedWriter.write(chatUsername);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                while (socket.isConnected()){
                    System.out.print(">: ");
                    String message = scanner.nextLine();

                    bufferedWriter.write("Username : "+message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                handleExceptions(this.bufferedWriter, this.bufferedReader, this.socket);
            }

        });
        thread.start();
    }

    private void handleExceptions(BufferedWriter bufferedWriter, BufferedReader bufferedReader, Socket socket) {

        try {
            if(bufferedReader != null) bufferedReader.close();

            if(bufferedWriter != null) bufferedWriter.close();

            if(socket != null) socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
















