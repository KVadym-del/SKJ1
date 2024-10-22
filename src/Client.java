import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverName, serverPort);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server " + serverName + " on port " + serverPort);

            String userInput;
            while (true) {
                System.out.print("Enter message (type 'QUIT' to exit): ");
                userInput = console.readLine();
                if (userInput.equalsIgnoreCase("QUIT")) {
                    break;
                }
                out.writeBytes(userInput + "\n");
                out.flush();
                System.out.println("Server response: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final private String serverName;
    final private int serverPort;

}