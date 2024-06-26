import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket soc = serverSocket.accept();

        BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        String str = reader.readLine();

        System.out.println(str);
    }
}