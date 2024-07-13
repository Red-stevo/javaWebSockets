import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("192.168.0.125", 8080);

            PrintWriter printWriter = null;

            printWriter = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Data : ");
            String data = bufferedReader.readLine();

            printWriter.println(data);
        } catch (IOException e) {
            System.out.println("Connection Refused.");
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }
}