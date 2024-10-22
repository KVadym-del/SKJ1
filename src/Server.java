import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server(int port) {
        this.port = port;
        System.out.println("Server is created on port " + port);
    }

    public void createServer()
    {
        try {
            createSocket();
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                new EchoThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSocket() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is listening");
    }

    public static boolean ping(Socket clientSocket) {
        try {
            clientSocket.sendUrgentData(0xFF);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private final int port;
    private ServerSocket serverSocket;
    private Socket socket;
}
